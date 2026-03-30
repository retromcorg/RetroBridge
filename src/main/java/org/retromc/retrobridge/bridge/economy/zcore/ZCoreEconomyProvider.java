package org.retromc.retrobridge.bridge.economy.zcore;

import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;
import org.retromc.retrobridge.bridge.economy.EconomyBridge;
import org.retromc.retrobridge.bridge.economy.EconomyProvider;

public class ZCoreEconomyProvider extends AbstractBridgeProvider implements EconomyProvider {
    private final EconomyBridge economyBridge = new ZCoreEconomyBridge();

    public ZCoreEconomyProvider(String ownerPluginName) {
        super("ZCore", ownerPluginName, BridgeModuleType.ECONOMY, "ZCore");
    }

    public EconomyBridge getEconomyBridge() {
        return economyBridge;
    }
}
