package org.retromc.retrobridge.bridge.ignore;

import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;

public class DummyIgnoreProvider extends AbstractBridgeProvider implements IgnoreProvider {
    private final IgnoreBridge bridge = new DummyIgnoreBridge();

    public DummyIgnoreProvider(String ownerPluginName) {
        super("DummyIgnore", ownerPluginName, BridgeModuleType.IGNORE, null);
    }

    public IgnoreBridge getIgnoreBridge() {
        return bridge;
    }
}
