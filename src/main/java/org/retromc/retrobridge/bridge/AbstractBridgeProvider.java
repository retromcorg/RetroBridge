package org.retromc.retrobridge.bridge;

public abstract class AbstractBridgeProvider implements BridgeProvider {
    private final String providerName;
    private final String owningPluginName;
    private final BridgeModuleType moduleType;
    private final String supportedPluginName;

    protected AbstractBridgeProvider(String providerName, String owningPluginName, BridgeModuleType moduleType, String supportedPluginName) {
        this.providerName = providerName;
        this.owningPluginName = owningPluginName;
        this.moduleType = moduleType;
        this.supportedPluginName = supportedPluginName;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getOwningPluginName() {
        return owningPluginName;
    }

    public BridgeModuleType getModuleType() {
        return moduleType;
    }

    public String getSupportedPluginName() {
        return supportedPluginName;
    }
}
