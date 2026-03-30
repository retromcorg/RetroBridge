package org.retromc.retrobridge;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

public class Listener implements org.bukkit.event.Listener {
    private RetroBridge plugin;

    // Constructor to link the plugin instance
    public Listener(RetroBridge plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = Event.Priority.Normal)
    public void onPluginEnable(PluginEnableEvent event) {
        plugin.debug("Observed plugin enable: " + event.getPlugin().getDescription().getName());
        plugin.getBridgeManager().refreshAll("plugin-enabled:" + event.getPlugin().getDescription().getName());
    }

    @EventHandler(priority = Event.Priority.Normal)
    public void onPluginDisable(PluginDisableEvent event) {
        plugin.debug("Observed plugin disable: " + event.getPlugin().getDescription().getName());
        plugin.getBridgeManager().unregisterProvidersByOwner(event.getPlugin().getDescription().getName());
        plugin.getBridgeManager().refreshAll("plugin-disabled:" + event.getPlugin().getDescription().getName());
    }
}
