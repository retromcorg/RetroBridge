# Plugin Integration

Another plugin can integrate with RetroBridge in two main ways:

- consume the active bridge selected by RetroBridge
- register a provider at runtime

## Consuming an Active Bridge

Plugins can copy a small accessor into their own codebase to avoid repeating plugin lookup and null checks.

```java
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.retromc.retrobridge.RetroBridge;
import org.retromc.retrobridge.bridge.BridgeManager;
import org.retromc.retrobridge.bridge.afk.AFKBridge;
import org.retromc.retrobridge.bridge.auth.AuthBridge;
import org.retromc.retrobridge.bridge.economy.EconomyBridge;
import org.retromc.retrobridge.bridge.fakequit.FakeQuitBridge;
import org.retromc.retrobridge.bridge.permission.PermissionBridge;
import org.retromc.retrobridge.bridge.vanish.VanishBridge;
import org.retromc.retrobridge.bridge.whois.WhoisBridge;

public final class RetroBridgeHook {
    private static final String PLUGIN_NAME = "RetroBridge";

    public boolean isAvailable() {
        return getRetroBridge() != null;
    }

    public AFKBridge getAFKBridge() {
        BridgeManager manager = getBridgeManager();
        return manager == null ? null : manager.getAFKBridge();
    }

    public EconomyBridge getEconomyBridge() {
        BridgeManager manager = getBridgeManager();
        return manager == null ? null : manager.getEconomyBridge();
    }

    public PermissionBridge getPermissionBridge() {
        BridgeManager manager = getBridgeManager();
        return manager == null ? null : manager.getPermissionBridge();
    }

    public AuthBridge getAuthBridge() {
        BridgeManager manager = getBridgeManager();
        return manager == null ? null : manager.getAuthBridge();
    }

    public WhoisBridge getWhoisBridge() {
        BridgeManager manager = getBridgeManager();
        return manager == null ? null : manager.getWhoisBridge();
    }

    public VanishBridge getVanishBridge() {
        BridgeManager manager = getBridgeManager();
        return manager == null ? null : manager.getVanishBridge();
    }

    public FakeQuitBridge getFakeQuitBridge() {
        BridgeManager manager = getBridgeManager();
        return manager == null ? null : manager.getFakeQuitBridge();
    }

    private BridgeManager getBridgeManager() {
        RetroBridge retroBridge = getRetroBridge();
        return retroBridge == null ? null : retroBridge.getBridgeManager();
    }

    private RetroBridge getRetroBridge() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(PLUGIN_NAME);
        if (plugin instanceof RetroBridge && plugin.isEnabled()) {
            return (RetroBridge) plugin;
        }
        return null;
    }
}
```

Example usage:

```java
import org.retromc.retrobridge.bridge.economy.EconomyBridge;

import java.util.UUID;

public class ExampleUsage {
    private final RetroBridgeHook retroBridge = new RetroBridgeHook();

    public double getBalance(UUID playerUuid) {
        EconomyBridge economyBridge = retroBridge.getEconomyBridge();
        if (economyBridge == null) {
            return 0.0D;
        }

        return economyBridge.getBalance(playerUuid);
    }
}
```

## Optional Dependency Setup

If your plugin can run without RetroBridge, declare it as a soft dependency:

```yml
softdepend: [RetroBridge]
```

If your plugin requires RetroBridge to function, declare it as a hard dependency instead:

```yml
depend: [RetroBridge]
```

Keep RetroBridge imports isolated to your hook/accessor class. Do not reference RetroBridge classes from your main plugin class, static fields, static initializers, or early-loaded method signatures unless RetroBridge is a hard dependency.

Before creating or using the hook, check that RetroBridge is present and enabled:

```java
import org.bukkit.plugin.Plugin;

Plugin retroBridgePlugin = getServer().getPluginManager().getPlugin("RetroBridge");
if (retroBridgePlugin != null && retroBridgePlugin.isEnabled()) {
    RetroBridgeHook retroBridge = new RetroBridgeHook();
}
```

This avoids loading RetroBridge-specific classes on servers that do not have RetroBridge installed.

The same pattern applies to the other modules:

- `getPermissionBridge()`
- `getAuthBridge()`
- `getWhoisBridge()`
- `getVanishBridge()`
- `getFakeQuitBridge()`

## Authentication Events

When a real auth provider is active, RetroBridge checks online players every 5 ticks and fires `PlayerAuthenticatedEvent` when a player transitions to authenticated.

```java
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.retromc.retrobridge.api.event.PlayerAuthenticatedEvent;

public class AuthListener implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerAuthenticated(PlayerAuthenticatedEvent event) {
        String playerName = event.getPlayer().getName();
        String providerName = event.getProviderName();
    }
}
```

Register it the normal way, from `onEnable()`:

```java
getServer().getPluginManager().registerEvents(new AuthListener(), this);
```

The event is fired on the main thread, and is not fired for the dummy auth fallback.

> **Changed in 1.1.1.** `PlayerAuthenticatedEvent` now declares its own `HandlerList`. Before 1.1.1 it
> used the legacy custom-event constructor, so `registerEvents` rejected any listener for it with
> `IllegalPluginAccessException: Unable to find handler list for event ...` — the example above could
> not actually be registered on a Poseidon V2 server. If you worked around that with a
> `CustomEventListener` and `registerEvent(Event.Type.CUSTOM_EVENT, ...)`, drop the workaround and use
> `@EventHandler`; the legacy route no longer receives this event.

## Registering a Provider at Runtime

This example registers a custom economy provider from another plugin during `onEnable()`.

```java
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.retromc.retrobridge.RetroBridge;
import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;
import org.retromc.retrobridge.bridge.economy.EconomyBridge;
import org.retromc.retrobridge.bridge.economy.EconomyProvider;

import java.util.UUID;

public class MyPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        Plugin plugin = getServer().getPluginManager().getPlugin("RetroBridge");
        if (!(plugin instanceof RetroBridge)) {
            return;
        }

        RetroBridge retroBridge = (RetroBridge) plugin;
        retroBridge.getBridgeManager().registerProvider(new MyEconomyProvider(getDescription().getName()));
    }

    private static class MyEconomyProvider extends AbstractBridgeProvider implements EconomyProvider {
        private final EconomyBridge bridge = new MyEconomyBridge();

        private MyEconomyProvider(String ownerPluginName) {
            super("MyEconomy", ownerPluginName, BridgeModuleType.ECONOMY, null);
        }

        public EconomyBridge getEconomyBridge() {
            return bridge;
        }
    }

    private static class MyEconomyBridge implements EconomyBridge {
        public boolean has(UUID playerUuid, double amount) {
            return false;
        }

        public double getBalance(UUID playerUuid) {
            return 0.0D;
        }

        public boolean deposit(UUID playerUuid, double amount) {
            return false;
        }

        public boolean withdraw(UUID playerUuid, double amount) {
            return false;
        }
    }
}
```

## Runtime Provider Lifecycle

Runtime providers are automatically unregistered when the owning plugin disables.

This works because RetroBridge tracks the `owningPluginName` passed to `AbstractBridgeProvider` and removes runtime providers for that plugin during disable handling.

That means most plugins do not need to manually unregister their provider in `onDisable()`.

## Whois Integration

`WhoisBridge` supports both lookup styles:

```java
Map<String, String> byPlayer = whoisBridge.getWhoisData(playerUuid);
Map<String, String> byIp = whoisBridge.getWhoisData("203.0.113.10");
```

For the GeoIPTools provider:

- `getWhoisData(UUID)` resolves the player's current address when the player is online
- `getWhoisData(String)` resolves directly from the supplied IP string

## Provider Naming

Provider names only need to be unique within a module.

So this is valid:

- economy provider named `Fundamentals`
- vanish provider named `Fundamentals`

What is not valid is two providers in the same module with the same provider name.
