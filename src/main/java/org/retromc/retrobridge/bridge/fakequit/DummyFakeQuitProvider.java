package org.retromc.retrobridge.bridge.fakequit;

import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;

public class DummyFakeQuitProvider extends AbstractBridgeProvider implements FakeQuitProvider {
    private final FakeQuitBridge bridge = new DummyFakeQuitBridge();

    public DummyFakeQuitProvider(String ownerPluginName) {
        super("DummyFakeQuit", ownerPluginName, BridgeModuleType.FAKEQUIT, null);
    }

    public FakeQuitBridge getFakeQuitBridge() {
        return bridge;
    }
}
