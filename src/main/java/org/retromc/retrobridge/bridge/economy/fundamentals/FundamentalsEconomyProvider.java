package org.retromc.retrobridge.bridge.economy.fundamentals;

import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;
import org.retromc.retrobridge.bridge.economy.EconomyBridge;
import org.retromc.retrobridge.bridge.economy.EconomyProvider;

public class FundamentalsEconomyProvider extends AbstractBridgeProvider implements EconomyProvider {
    private final EconomyBridge economyBridge = new FundamentalsEconomyBridge();

    public FundamentalsEconomyProvider(String ownerPluginName) {
        super("Fundamentals", ownerPluginName, BridgeModuleType.ECONOMY, "Fundamentals");
    }

    public EconomyBridge getEconomyBridge() {
        return economyBridge;
    }
}
