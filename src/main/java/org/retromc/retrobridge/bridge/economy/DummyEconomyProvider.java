package org.retromc.retrobridge.bridge.economy;

import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;

public class DummyEconomyProvider extends AbstractBridgeProvider implements EconomyProvider {
    private final EconomyBridge bridge = new DummyEconomyBridge();

    public DummyEconomyProvider(String ownerPluginName) {
        super("DummyEconomy", ownerPluginName, BridgeModuleType.ECONOMY, null);
    }

    public EconomyBridge getEconomyBridge() {
        return bridge;
    }
}
