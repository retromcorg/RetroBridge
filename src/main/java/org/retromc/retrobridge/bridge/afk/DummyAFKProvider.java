package org.retromc.retrobridge.bridge.afk;

import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;

public class DummyAFKProvider extends AbstractBridgeProvider implements AFKProvider {
    private final AFKBridge bridge = new DummyAFKBridge();

    public DummyAFKProvider(String ownerPluginName) {
        super("DummyAFK", ownerPluginName, BridgeModuleType.AFK, null);
    }

    public AFKBridge getAFKBridge() {
        return bridge;
    }
}