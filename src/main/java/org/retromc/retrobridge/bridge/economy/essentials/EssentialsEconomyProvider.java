package org.retromc.retrobridge.bridge.economy.essentials;

import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;
import org.retromc.retrobridge.bridge.economy.EconomyBridge;
import org.retromc.retrobridge.bridge.economy.EconomyProvider;

public class EssentialsEconomyProvider extends AbstractBridgeProvider implements EconomyProvider {
    private final EconomyBridge economyBridge = new EssentialsEconomyBridge();

    public EssentialsEconomyProvider(String ownerPluginName) {
        super("Essentials", ownerPluginName, BridgeModuleType.ECONOMY, "Essentials");
    }

    public EconomyBridge getEconomyBridge() {
        return economyBridge;
    }
}
