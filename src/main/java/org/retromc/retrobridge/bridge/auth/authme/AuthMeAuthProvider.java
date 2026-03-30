package org.retromc.retrobridge.bridge.auth.authme;

import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;
import org.retromc.retrobridge.bridge.auth.AuthBridge;
import org.retromc.retrobridge.bridge.auth.AuthProvider;

public class AuthMeAuthProvider extends AbstractBridgeProvider implements AuthProvider {
    private final AuthBridge authBridge = new AuthMeAuthBridge();

    public AuthMeAuthProvider(String ownerPluginName) {
        super("AuthMe", ownerPluginName, BridgeModuleType.AUTH, "AuthMe");
    }

    public AuthBridge getAuthBridge() {
        return authBridge;
    }
}
