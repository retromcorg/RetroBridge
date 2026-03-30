package org.retromc.retrobridge.bridge.vanish.fundamentals;

import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;
import org.retromc.retrobridge.bridge.vanish.VanishBridge;
import org.retromc.retrobridge.bridge.vanish.VanishProvider;

public class FundamentalsVanishProvider extends AbstractBridgeProvider implements VanishProvider {
    private final VanishBridge vanishBridge = new FundamentalsVanishBridge();

    public FundamentalsVanishProvider(String ownerPluginName) {
        super("Fundamentals", ownerPluginName, BridgeModuleType.VANISH, "Fundamentals");
    }

    public VanishBridge getVanishBridge() {
        return vanishBridge;
    }
}
