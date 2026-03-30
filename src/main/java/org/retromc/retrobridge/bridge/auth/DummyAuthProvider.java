package org.retromc.retrobridge.bridge.auth;

import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;

public class DummyAuthProvider extends AbstractBridgeProvider implements AuthProvider {
    private final AuthBridge bridge = new DummyAuthBridge();

    public DummyAuthProvider(String ownerPluginName) {
        super("DummyAuth", ownerPluginName, BridgeModuleType.AUTH, null);
    }

    public AuthBridge getAuthBridge() {
        return bridge;
    }
}
