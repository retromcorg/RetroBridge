package org.retromc.retrobridge.bridge.auth.xauth;

import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;
import org.retromc.retrobridge.bridge.auth.AuthBridge;
import org.retromc.retrobridge.bridge.auth.AuthProvider;

public class XAuthAuthProvider extends AbstractBridgeProvider implements AuthProvider {
    private final AuthBridge authBridge = new XAuthAuthBridge();

    public XAuthAuthProvider(String ownerPluginName) {
        super("xAuth", ownerPluginName, BridgeModuleType.AUTH, "xAuth");
    }

    public AuthBridge getAuthBridge() {
        return authBridge;
    }
}
