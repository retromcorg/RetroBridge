package org.retromc.retrobridge.bridge.afk.fundamentals;

import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;
import org.retromc.retrobridge.bridge.afk.AFKBridge;
import org.retromc.retrobridge.bridge.afk.AFKProvider;

public class FundamentalsAFKProvider extends AbstractBridgeProvider implements AFKProvider {

    private final AFKBridge afkBridge = new FundamentalsAFKBridge();

    public FundamentalsAFKProvider(String ownerPluginName) {
        super("Fundamentals", ownerPluginName, BridgeModuleType.AFK, "Fundamentals");
    }

    public AFKBridge getAFKBridge() {
        return afkBridge;
    }
}