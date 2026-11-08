# Plugin Integration

Another plugin can integrate with RetroBridge in two main ways:

- consume the active bridge selected by RetroBridge
- register a provider at runtime

## Consuming an Active Bridge

RetroBridge ships an accessor, `org.retromc.retrobridge.api.RetroBridgeAccess`, that wraps the plugin lookup and null checks. Depending on how your plugin depends on RetroBridge, you either use that class directly or bundle your own copy. See [Choosing an Accessor](#choosing-an-accessor).

A bundled accessor looks like this:

```java
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.retromc.retrobridge.RetroBridge;
import org.retromc.retrobridge.bridge.BridgeManager;
import org.retromc.retrobridge.bridge.afk.AFKBridge;
import org.retromc.retrobridge.bridge.auth.AuthBridge;
import org.retromc.retrobridge.bridge.economy.EconomyBridge;
import org.retromc.retrobridge.bridge.fakequit.FakeQuitBridge;
import org.retromc.retrobridge.bridge.ignore.IgnoreBridge;
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

    public IgnoreBridge getIgnoreBridge() {
        BridgeManager manager = getBridgeManager();
        return manager == null ? null : manager.getIgnoreBridge();
    }

    private BridgeManager getBridgeManager() {
        RetroBridge retroBridge = getRetroBridge();
        return retroBridge == null ? null : retroBridge.getBridgeManager();
    }

    private RetroBridge getRetroBridge() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(PLUGIN_NAME);
        if (plugin == null || !plugin.isEnabled()) {
            return null;
        }
        return plugin instanceof RetroBridge ? (RetroBridge) plugin : null;
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

## Choosing an Accessor

The included `RetroBridgeAccess` lives inside the RetroBridge jar. On a server without RetroBridge, that class does not exist either, so `new RetroBridgeAccess()` throws `NoClassDefFoundError` before any of its checks run. What matters is whether your code can reach the accessor on a server without RetroBridge.

### Use the included class

Use `org.retromc.retrobridge.api.RetroBridgeAccess` directly when:

- RetroBridge is a hard `depend`. Bukkit will not enable your plugin without RetroBridge, so the class is always present.
- RetroBridge is a `softdepend`, but you only create the accessor after checking that RetroBridge is present and enabled (see [Optional Dependency Setup](#optional-dependency-setup)).

Declare RetroBridge with `provided` scope so it is not packaged into your jar:

```xml
<dependency>
    <groupId>org.retromc</groupId>
    <artifactId>RetroBridge</artifactId>
    <version>1.1.2</version>
    <scope>provided</scope>
</dependency>
```

The included class always matches the RetroBridge version running on the server, so you get fixes and new bridge getters without rebuilding your plugin.

### Bundle your own copy

Bundle an accessor in your own package when RetroBridge is a `softdepend` and your code creates the accessor unconditionally, for example as a field on a listener that is always registered.

Copy [`RetroBridgeAccess.java`](RetroBridgeAccess.java) into your plugin and change its package to one you own. That template checks for RetroBridge by name before referencing any RetroBridge class, so every method returns `null` or `false` on servers without it.

You can use maven-shade instead of copying the file, but limit it to that one class with a filter and relocate it into your own package.

Never shade the class without relocating it. Two plugins would then both contain `org.retromc.retrobridge.api.RetroBridgeAccess`, and because Bukkit plugin classloaders share loaded classes, which copy you get depends on plugin load order.

A bundled copy is frozen at the API version you built it against. If it calls a getter that the installed RetroBridge does not have (for example `getIgnoreBridge()`, added in 1.1.2, on an older RetroBridge), it throws `NoSuchMethodError` at the call site. Either call only bridges that exist in the oldest RetroBridge version you support, or catch `NoSuchMethodError` around newer ones.

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

If you use the included `RetroBridgeAccess` with a soft dependency, check that RetroBridge is present and enabled before creating it:

```java
import org.bukkit.plugin.Plugin;
import org.retromc.retrobridge.api.RetroBridgeAccess;

Plugin retroBridgePlugin = getServer().getPluginManager().getPlugin("RetroBridge");
if (retroBridgePlugin != null && retroBridgePlugin.isEnabled()) {
    RetroBridgeAccess retroBridge = new RetroBridgeAccess();
}
```

This avoids loading RetroBridge-specific classes on servers that do not have RetroBridge installed. A bundled accessor like `RetroBridgeHook` above does this check itself, so it can be created unconditionally.

The same pattern applies to the other modules:

- `getPermissionBridge()`
- `getAuthBridge()`
- `getWhoisBridge()`
- `getVanishBridge()`
- `getFakeQuitBridge()`
- `getIgnoreBridge()`

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
