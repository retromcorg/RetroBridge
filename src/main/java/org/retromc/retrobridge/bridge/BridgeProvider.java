package org.retromc.retrobridge.bridge;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public interface BridgeProvider {
    String getProviderName();

    String getOwningPluginName();

    BridgeModuleType getModuleType();

    String getSupportedPluginName();

    default boolean isAvailable() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        String owner = getOwningPluginName();
        if (owner != null && owner.length() > 0 && !pluginManager.isPluginEnabled(owner)) {
            return false;
        }

        String supportedPluginName = getSupportedPluginName();
        if (supportedPluginName == null || supportedPluginName.length() == 0) {
            return true;
        }
        return pluginManager.isPluginEnabled(supportedPluginName);
    }
}
