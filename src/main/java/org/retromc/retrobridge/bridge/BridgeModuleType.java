package org.retromc.retrobridge.bridge;

public enum BridgeModuleType {
    AFK("afk"),
    ECONOMY("economy"),
    PERMISSIONS("permissions"),
    AUTH("auth"),
    WHOIS("whois"),
    VANISH("vanish"),
    FAKEQUIT("fakequit");

    private final String configKey;

    BridgeModuleType(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigKey() {
        return configKey;
    }
}
