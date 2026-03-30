package org.retromc.retrobridge.bridge.whois;

import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;

public class DummyWhoisProvider extends AbstractBridgeProvider implements WhoisProvider {
    private final WhoisBridge bridge = new DummyWhoisBridge();

    public DummyWhoisProvider(String ownerPluginName) {
        super("DummyWhois", ownerPluginName, BridgeModuleType.WHOIS, null);
    }

    public WhoisBridge getWhoisBridge() {
        return bridge;
    }
}
