package org.retromc.retrobridge;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.retromc.retrobridge.bridge.BridgeManager;
import org.retromc.retrobridge.commands.RetroBridgeCommand;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RetroBridge extends JavaPlugin {
    private static RetroBridge instance;
    private JavaPlugin plugin;
    private Logger log;
    private String pluginName;
    private PluginDescriptionFile pdf;

    private Config configuration;
    private BridgeManager bridgeManager;


    @Override
    public void onEnable() {
        instance = this;
        plugin = this;
        log = this.getServer().getLogger();
        pdf = this.getDescription();
        pluginName = pdf.getName();
        log.info("[" + pluginName + "] Is Loading, Version: " + pdf.getVersion());

        // Load configuration
        configuration = new Config(this, new File(getDataFolder(), "config.yml")); // Load the configuration file from the plugin's data folder

        bridgeManager = new BridgeManager(this);
        bridgeManager.initialize();
        getCommand("retrobridge").setExecutor(new RetroBridgeCommand(this));

        // Register the listeners
        Listener listener = new Listener(this);
        getServer().getPluginManager().registerEvents(listener, this);

        log.info("[" + pluginName + "] Is Loaded, Version: " + pdf.getVersion());
    }

    @Override
    public void onDisable() {
        log.info("[" + pluginName + "] Is Unloading, Version: " + pdf.getVersion());

        // Save configuration
        //config.save(); // Save the configuration file to disk. This should only be necessary if the configuration cam be modified during runtime.

        instance = null;
        log.info("[" + pluginName + "] Is Unloaded, Version: " + pdf.getVersion());
    }

    public static RetroBridge getInstance() {
        return instance;
    }

    public void logger(Level level, String message) {
        Bukkit.getLogger().log(level, "[" + plugin.getDescription().getName() + "] " + message);
    }

    public void debug(String message) {
        if (configuration != null && configuration.getConfigBoolean("settings.debug.value", false)) {
            logger(Level.INFO, "[DEBUG] " + message);
        }
    }

    public Config getConfig() {
        return configuration;
    }

    public BridgeManager getBridgeManager() {
        return bridgeManager;
    }
}
