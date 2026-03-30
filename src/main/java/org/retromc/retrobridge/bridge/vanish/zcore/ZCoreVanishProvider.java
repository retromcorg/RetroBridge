package org.retromc.retrobridge.bridge.vanish.zcore;

import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;
import org.retromc.retrobridge.bridge.vanish.VanishBridge;
import org.retromc.retrobridge.bridge.vanish.VanishProvider;

public class ZCoreVanishProvider extends AbstractBridgeProvider implements VanishProvider {
    private final VanishBridge vanishBridge = new ZCoreVanishBridge();

    public ZCoreVanishProvider(String ownerPluginName) {
        super("ZCore", ownerPluginName, BridgeModuleType.VANISH, "ZCore");
    }

    public VanishBridge getVanishBridge() {
        return vanishBridge;
    }
}
