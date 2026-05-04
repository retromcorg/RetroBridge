package org.retromc.retrobridge.bridge;

import org.retromc.retrobridge.RetroBridge;
import org.retromc.retrobridge.bridge.afk.AFKBridge;
import org.retromc.retrobridge.bridge.afk.AFKProvider;
import org.retromc.retrobridge.bridge.afk.DummyAFKProvider;
import org.retromc.retrobridge.bridge.afk.fundamentals.FundamentalsAFKProvider;
import org.retromc.retrobridge.bridge.auth.AuthBridge;
import org.retromc.retrobridge.bridge.auth.AuthProvider;
import org.retromc.retrobridge.bridge.auth.DummyAuthProvider;
import org.retromc.retrobridge.bridge.auth.authme.AuthMeAuthProvider;
import org.retromc.retrobridge.bridge.economy.DummyEconomyProvider;
import org.retromc.retrobridge.bridge.economy.EconomyBridge;
import org.retromc.retrobridge.bridge.economy.EconomyProvider;
import org.retromc.retrobridge.bridge.economy.essentials.EssentialsEconomyProvider;
import org.retromc.retrobridge.bridge.economy.fundamentals.FundamentalsEconomyProvider;
import org.retromc.retrobridge.bridge.economy.zcore.ZCoreEconomyProvider;
import org.retromc.retrobridge.bridge.fakequit.DummyFakeQuitProvider;
import org.retromc.retrobridge.bridge.fakequit.FakeQuitBridge;
import org.retromc.retrobridge.bridge.fakequit.FakeQuitProvider;
import org.retromc.retrobridge.bridge.fakequit.fundamentals.FundamentalsFakeQuitProvider;
import org.retromc.retrobridge.bridge.permission.DummyPermissionProvider;
import org.retromc.retrobridge.bridge.permission.PermissionBridge;
import org.retromc.retrobridge.bridge.permission.PermissionProvider;
import org.retromc.retrobridge.bridge.permission.jperms.JPermsPermissionProvider;
import org.retromc.retrobridge.bridge.vanish.DummyVanishProvider;
import org.retromc.retrobridge.bridge.vanish.VanishBridge;
import org.retromc.retrobridge.bridge.vanish.VanishProvider;
import org.retromc.retrobridge.bridge.vanish.fundamentals.FundamentalsVanishProvider;
import org.retromc.retrobridge.bridge.vanish.zcore.ZCoreVanishProvider;
import org.retromc.retrobridge.bridge.whois.DummyWhoisProvider;
import org.retromc.retrobridge.bridge.whois.WhoisBridge;
import org.retromc.retrobridge.bridge.whois.WhoisProvider;
import org.retromc.retrobridge.bridge.whois.geoiptools.GeoIPToolsWhoisProvider;

import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class BridgeManager {
    private final RetroBridge plugin;
    private final Map<BridgeModuleType, List<ProviderRegistration>> providersByModule = new EnumMap<BridgeModuleType, List<ProviderRegistration>>(BridgeModuleType.class);
    private final Map<BridgeModuleType, ProviderRegistration> activeProviders = new EnumMap<BridgeModuleType, ProviderRegistration>(BridgeModuleType.class);

    public BridgeManager(RetroBridge plugin) {
        this.plugin = plugin;
    }

    public void initialize() {
        String bridgePluginName = plugin.getDescription().getName();
        plugin.debug("Initializing built-in providers.");

        // AFK providers
        registerBuiltinProvider(new FundamentalsAFKProvider(bridgePluginName));
        registerBuiltinProvider(new DummyAFKProvider(bridgePluginName));

        // Economy providers
        registerBuiltinProvider(new EssentialsEconomyProvider(bridgePluginName));
        registerBuiltinProvider(new FundamentalsEconomyProvider(bridgePluginName));
        registerBuiltinProvider(new ZCoreEconomyProvider(bridgePluginName));
        registerBuiltinProvider(new DummyEconomyProvider(bridgePluginName));

        // Permission providers
        registerBuiltinProvider(new JPermsPermissionProvider(bridgePluginName));
        registerBuiltinProvider(new DummyPermissionProvider(bridgePluginName));


        // Auth providers
        registerBuiltinProvider(new AuthMeAuthProvider(bridgePluginName));
        registerBuiltinProvider(new DummyAuthProvider(bridgePluginName));


        // Whois providers
        registerBuiltinProvider(new GeoIPToolsWhoisProvider(bridgePluginName));
        registerBuiltinProvider(new DummyWhoisProvider(bridgePluginName));


        // Vanish providers
        registerBuiltinProvider(new FundamentalsVanishProvider(bridgePluginName));
        registerBuiltinProvider(new ZCoreVanishProvider(bridgePluginName));
        registerBuiltinProvider(new DummyVanishProvider(bridgePluginName));

        // FakeQuit providers
        registerBuiltinProvider(new FundamentalsFakeQuitProvider(bridgePluginName));
        registerBuiltinProvider(new DummyFakeQuitProvider(bridgePluginName));

        refreshAll("startup");
    }

    public synchronized void refreshAll(String reason) {
        if (reason.startsWith("plugin-") && !plugin.getConfig().getConfigBoolean("settings.hooks.refresh-on-plugin-events.value", true)) {
            plugin.debug("Skipping refresh for reason '" + reason + "' because refresh-on-plugin-events is disabled.");
            return;
        }

        plugin.debug("Refreshing all modules. reason=" + reason);
        for (BridgeModuleType moduleType : providersByModule.keySet()) {
            refreshModule(moduleType, reason);
        }
    }

    public synchronized BridgeProvider getActiveProvider(BridgeModuleType moduleType) {
        ProviderRegistration active = activeProviders.get(moduleType);
        if (active == null) {
            return null;
        }
        return active.provider;
    }

    public synchronized ProviderSourceType getActiveProviderSource(BridgeModuleType moduleType) {
        ProviderRegistration active = activeProviders.get(moduleType);
        if (active == null) {
            return null;
        }
        return active.sourceType;
    }

    public synchronized boolean registerProvider(BridgeProvider provider) {
        if (provider == null) {
            return false;
        }
        List<ProviderRegistration> providers = getOrCreateProviders(provider.getModuleType());
        upsertProvider(providers, provider, ProviderSourceType.RUNTIME);
        plugin.debug("Registered runtime provider " + provider.getProviderName() + " for module " + provider.getModuleType().name() + ".");
        refreshModule(provider.getModuleType(), "runtime-register:" + provider.getProviderName());
        return true;
    }

    public synchronized boolean unregisterProvider(BridgeModuleType moduleType, String providerName) {
        List<ProviderRegistration> providers = providersByModule.get(moduleType);
        if (providers == null) {
            return false;
        }
        boolean removed = removeProviderByName(moduleType, providers, providerName);
        if (removed) {
            plugin.debug("Unregistered provider " + providerName + " from module " + moduleType.name() + ".");
            refreshModule(moduleType, "runtime-unregister:" + providerName);
        }
        return removed;
    }

    public synchronized int unregisterProvidersByOwner(String ownerPluginName) {
        int removed = 0;
        for (BridgeModuleType moduleType : providersByModule.keySet()) {
            List<ProviderRegistration> providers = providersByModule.get(moduleType);
            int moduleRemoved = removeProvidersByOwner(moduleType, providers, ownerPluginName);
            removed += moduleRemoved;
            if (moduleRemoved > 0) {
                plugin.debug("Unregistered " + moduleRemoved + " provider(s) owned by " + ownerPluginName + " from module " + moduleType.name() + ".");
                refreshModule(moduleType, "runtime-owner-unregister:" + ownerPluginName);
            }
        }
        return removed;
    }

    public synchronized AFKBridge getAFKBridge() {
        BridgeProvider provider = getActiveProvider(BridgeModuleType.AFK);
        if (provider instanceof AFKProvider) {
            return ((AFKProvider) provider).getAFKBridge();
        }
        return null;
    }

    public synchronized EconomyBridge getEconomyBridge() {
        BridgeProvider provider = getActiveProvider(BridgeModuleType.ECONOMY);
        if (provider instanceof EconomyProvider) {
            return ((EconomyProvider) provider).getEconomyBridge();
        }
        return null;
    }

    public synchronized PermissionBridge getPermissionBridge() {
        BridgeProvider provider = getActiveProvider(BridgeModuleType.PERMISSIONS);
        if (provider instanceof PermissionProvider) {
            return ((PermissionProvider) provider).getPermissionBridge();
        }
        return null;
    }

    public synchronized AuthBridge getAuthBridge() {
        BridgeProvider provider = getActiveProvider(BridgeModuleType.AUTH);
        if (provider instanceof AuthProvider) {
            return ((AuthProvider) provider).getAuthBridge();
        }
        return null;
    }

    public synchronized WhoisBridge getWhoisBridge() {
        BridgeProvider provider = getActiveProvider(BridgeModuleType.WHOIS);
        if (provider instanceof WhoisProvider) {
            return ((WhoisProvider) provider).getWhoisBridge();
        }
        return null;
    }

    public synchronized VanishBridge getVanishBridge() {
        BridgeProvider provider = getActiveProvider(BridgeModuleType.VANISH);
        if (provider instanceof VanishProvider) {
            return ((VanishProvider) provider).getVanishBridge();
        }
        return null;
    }

    public synchronized FakeQuitBridge getFakeQuitBridge() {
        BridgeProvider provider = getActiveProvider(BridgeModuleType.FAKEQUIT);
        if (provider instanceof FakeQuitProvider) {
            return ((FakeQuitProvider) provider).getFakeQuitBridge();
        }
        return null;
    }

    private boolean registerBuiltinProvider(BridgeProvider provider) {
        if (provider == null) {
            return false;
        }
        List<ProviderRegistration> providers = getOrCreateProviders(provider.getModuleType());
        upsertProvider(providers, provider, ProviderSourceType.BUILTIN);
        plugin.debug("Registered built-in provider " + provider.getProviderName() + " for module " + provider.getModuleType().name() + ".");
        return true;
    }

    private List<ProviderRegistration> getOrCreateProviders(BridgeModuleType moduleType) {
        List<ProviderRegistration> providers = providersByModule.get(moduleType);
        if (providers != null) {
            return providers;
        }
        providers = new ArrayList<>();
        providersByModule.put(moduleType, providers);
        return providers;
    }

    private void upsertProvider(List<ProviderRegistration> providers, BridgeProvider provider, ProviderSourceType sourceType) {
        for (int i = 0; i < providers.size(); i++) {
            ProviderRegistration current = providers.get(i);
            if (current.provider.getProviderName().equalsIgnoreCase(provider.getProviderName())) {
                providers.set(i, new ProviderRegistration(provider, sourceType));
                return;
            }
        }
        providers.add(new ProviderRegistration(provider, sourceType));
    }

    private boolean removeProviderByName(BridgeModuleType moduleType, List<ProviderRegistration> providers, String providerName) {
        Iterator<ProviderRegistration> iterator = providers.iterator();
        while (iterator.hasNext()) {
            ProviderRegistration registration = iterator.next();
            if (registration.provider.getProviderName().equalsIgnoreCase(providerName)) {
                iterator.remove();
                ProviderRegistration active = activeProviders.get(moduleType);
                if (active != null && active.provider.getProviderName().equalsIgnoreCase(providerName)) {
                    activeProviders.remove(moduleType);
                }
                return true;
            }
        }
        return false;
    }

    private int removeProvidersByOwner(BridgeModuleType moduleType, List<ProviderRegistration> providers, String ownerPluginName) {
        if (providers == null || ownerPluginName == null) {
            return 0;
        }
        int removed = 0;
        Iterator<ProviderRegistration> iterator = providers.iterator();
        while (iterator.hasNext()) {
            ProviderRegistration registration = iterator.next();
            String owner = registration.provider.getOwningPluginName();
            if (owner != null && owner.equalsIgnoreCase(ownerPluginName)) {
                iterator.remove();
                removed++;
            }
        }
        ProviderRegistration active = activeProviders.get(moduleType);
        if (active != null) {
            String owner = active.provider.getOwningPluginName();
            if (owner != null && owner.equalsIgnoreCase(ownerPluginName)) {
                activeProviders.remove(moduleType);
            }
        }
        return removed;
    }

    private void refreshModule(BridgeModuleType moduleType, String reason) {
        List<ProviderRegistration> providers = providersByModule.get(moduleType);
        if (providers == null) {
            return;
        }

        // Get the config for this module type
        String base = "settings.hooks.modules." + moduleType.getConfigKey() + ".";
        String preferredProvider = plugin.getConfig().getConfigString(base + "preferred-provider.value", "AUTO");
        boolean allowFallback = plugin.getConfig().getConfigBoolean(base + "allow-fallback.value", true);

        // Select the provider based on the config and availability
        ProviderRegistration selected = selectProvider(providers, preferredProvider, allowFallback);
        ProviderRegistration previous = activeProviders.get(moduleType);

        plugin.debug("Module " + moduleType.name()
                + " selection context: preferredProvider=" + preferredProvider
                + ", allowFallback=" + allowFallback + ".");

        if (selected == previous) {
            if (selected != null) {
                plugin.debug("Module " + moduleType.name() + " remains on provider " + selected.provider.getProviderName() + ".");
            }
            return;
        }

        if (selected == null) {
            activeProviders.remove(moduleType);
        } else {
            activeProviders.put(moduleType, selected);
        }

        // Print if no provider is available. This shouldn't really be possible due to the dummy providers
        if (selected == null) {
            plugin.logger(Level.WARNING, "Bridge " + moduleType.name() + ": no provider available (" + reason + ").");
            return;
        }

        // Print the change
        String previousName = previous == null ? "none" : previous.provider.getProviderName();
        plugin.logger(Level.INFO, "Bridge " + moduleType.name() + ": " + previousName + " -> "
                + selected.provider.getProviderName() + " (" + selected.sourceType.name() + ", " + reason + ").");
    }

    private ProviderRegistration selectProvider(List<ProviderRegistration> allProviders, String preferredProvider, boolean allowFallback) {
        // Create a list of available providers
        List<ProviderRegistration> available = new ArrayList<>();
        for (int i = 0; i < allProviders.size(); i++) {
            ProviderRegistration provider = allProviders.get(i);
            if (provider.provider.isAvailable()) {
                available.add(provider);
            }
        }

        if (available.isEmpty()) {
            return null;
        }

        // Try to hook the preferred provider if it's set and not "AUTO"
        String normalizedPreferredProvider = preferredProvider == null ? "AUTO" : preferredProvider.trim();
        if (!normalizedPreferredProvider.equalsIgnoreCase("AUTO")) {
            for (int i = 0; i < available.size(); i++) {
                ProviderRegistration provider = available.get(i);
                if (provider.provider.getProviderName().equalsIgnoreCase(normalizedPreferredProvider)) {
                    return provider;
                }
            }
            if (!allowFallback) {
                return null;
            }
        }

        // Don't fallback if disabled
        if (!allowFallback) {
            return null;
        }

        // Fallback to the first available provider
        return available.get(0);
    }

    private static class ProviderRegistration {
        private final BridgeProvider provider;
        private final ProviderSourceType sourceType;

        private ProviderRegistration(BridgeProvider provider, ProviderSourceType sourceType) {
            this.provider = provider;
            this.sourceType = sourceType;
        }
    }
}
