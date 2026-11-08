package org.retromc.retrobridge.bridge.ignore.zcore;

import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;
import org.retromc.retrobridge.bridge.ignore.IgnoreBridge;
import org.retromc.retrobridge.bridge.ignore.IgnoreProvider;

public class ZCoreIgnoreProvider extends AbstractBridgeProvider implements IgnoreProvider {
    private final IgnoreBridge ignoreBridge = new ZCoreIgnoreBridge();

    public ZCoreIgnoreProvider(String ownerPluginName) {
        super("ZCore", ownerPluginName, BridgeModuleType.IGNORE, "ZCore");
    }

    public IgnoreBridge getIgnoreBridge() {
        return ignoreBridge;
    }
}
