package org.retromc.retrobridge.bridge.auth.osas;

import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;
import org.retromc.retrobridge.bridge.auth.AuthBridge;
import org.retromc.retrobridge.bridge.auth.AuthProvider;

public class OSASAuthProvider extends AbstractBridgeProvider implements AuthProvider {
    private final AuthBridge authBridge = new OSASAuthBridge();

    public OSASAuthProvider(String ownerPluginName) {
        super("OSAS", ownerPluginName, BridgeModuleType.AUTH, "OSAS");
    }

    public AuthBridge getAuthBridge() {
        return authBridge;
    }
}
