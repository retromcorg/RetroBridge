package org.retromc.retrobridge.bridge.auth;

import org.retromc.retrobridge.bridge.BridgeProvider;

public interface AuthProvider extends BridgeProvider {
    AuthBridge getAuthBridge();
}
