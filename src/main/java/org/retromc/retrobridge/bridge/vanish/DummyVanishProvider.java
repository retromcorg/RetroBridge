package org.retromc.retrobridge.bridge.vanish;

import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;

public class DummyVanishProvider extends AbstractBridgeProvider implements VanishProvider {
    private final VanishBridge bridge = new DummyVanishBridge();

    public DummyVanishProvider(String ownerPluginName) {
        super("DummyVanish", ownerPluginName, BridgeModuleType.VANISH, null);
    }

    public VanishBridge getVanishBridge() {
        return bridge;
    }
}
