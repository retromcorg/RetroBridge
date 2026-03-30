# Plugin Integration

Another plugin can integrate with RetroBridge in two main ways:

- consume the active bridge selected by RetroBridge
- register a provider at runtime

## Consuming an Active Bridge

This example reads the active economy bridge and uses it to fetch a balance.

```java
import org.bukkit.plugin.Plugin;
import org.retromc.retrobridge.RetroBridge;
import org.retromc.retrobridge.bridge.economy.EconomyBridge;

import java.util.UUID;

public class ExampleUsage {
    public double getBalance(UUID playerUuid) {
        Plugin plugin = getServer().getPluginManager().getPlugin("RetroBridge");
        if (!(plugin instanceof RetroBridge)) {
            return 0.0D;
        }

        RetroBridge retroBridge = (RetroBridge) plugin;
        EconomyBridge economyBridge = retroBridge.getBridgeManager().getEconomyBridge();
        if (economyBridge == null) {
            return 0.0D;
        }

        return economyBridge.getBalance(playerUuid);
    }
}
```

The same pattern applies to the other modules:

- `getPermissionBridge()`
- `getAuthBridge()`
- `getWhoisBridge()`
- `getVanishBridge()`
- `getFakeQuitBridge()`

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
