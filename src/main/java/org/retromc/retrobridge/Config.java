package org.retromc.retrobridge;

import org.bukkit.util.config.Configuration;

import java.io.File;

/**
 * A custom configuration class for managing plugin configuration files in a Bukkit environment.
 * Extends the {@link Configuration} class to provide additional utility methods for
 * reading and writing configuration options with defaults.
 */
public class Config extends Configuration {
    private final int configVersion = 1;


    private RetroBridge plugin;

    /**
     * Constructs a new TemplateConfig instance.
     *
     * @param plugin     The plugin instance associated with this configuration.
     * @param configFile The configuration file to be managed.
     */
    public Config(RetroBridge plugin, File configFile) {
        super(configFile);
        this.plugin = plugin;
        this.reload();
    }

    /**
     * Writes default configuration options to the file.
     * Ensures that default options are added when the file is loaded.
     */
    private void write() {
        // Main options
        generateConfigOption("config-version", configVersion);
        generateConfigOption("settings.debug.value", false);
        generateConfigOption("settings.debug.info", "When true, RetroBridge logs extra provider selection and hook refresh details.");

        // Hook preferences
        generateConfigOption("settings.hooks.refresh-on-plugin-events.value", true);
        generateConfigOption("settings.hooks.refresh-on-plugin-events.info", "When true, re-detect hooks when a plugin is enabled or disabled.");

        generateModuleDefaults("economy");
        generateModuleDefaults("permissions");
        generateModuleDefaults("auth");
        generateModuleDefaults("whois");
        generateModuleDefaults("vanish");
        generateModuleDefaults("fakequit");
    }

    private void generateModuleDefaults(String module) {
        final String base = "settings.hooks.modules." + module + ".";
        generateConfigOption(base + "preferred-provider.value", "AUTO");
        generateConfigOption(base + "allow-fallback.value", true);
    }

    /**
     * Reloads the configuration by loading the file, writing defaults, and saving changes.
     */
    private void reload() {
        this.load();
        this.write();
        this.save();
    }

    /**
     * Adds a default value for a configuration key if it is not already set.
     *
     * @param key          The configuration key.
     * @param defaultValue The default value to set.
     */
    public void generateConfigOption(String key, Object defaultValue) {
        if (this.getProperty(key) == null) {
            this.setProperty(key, defaultValue);
        }
        final Object value = this.getProperty(key);
        this.removeProperty(key);
        this.setProperty(key, value);
    }

    //Getters Start
    public Object getConfigOption(String key) {
        return this.getProperty(key);
    }

    public String getConfigString(String key) {
        return String.valueOf(getConfigOption(key));
    }

    public Integer getConfigInteger(String key) {
        return Integer.valueOf(getConfigString(key));
    }

    public Long getConfigLong(String key) {
        return Long.valueOf(getConfigString(key));
    }

    public Double getConfigDouble(String key) {
        return Double.valueOf(getConfigString(key));
    }

    public Boolean getConfigBoolean(String key) {
        return Boolean.valueOf(getConfigString(key));
    }

    public String getConfigString(String key, String defaultValue) {
        final Object value = this.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        return String.valueOf(value);
    }

    public Boolean getConfigBoolean(String key, Boolean defaultValue) {
        final Object value = this.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        return Boolean.valueOf(String.valueOf(value));
    }

    //Getters End
}
