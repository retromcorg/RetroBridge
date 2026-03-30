package org.retromc.retrobridge.bridge.auth;

import java.util.UUID;

public class DummyAuthBridge implements AuthBridge {
    public boolean isAuthenticated(UUID playerUuid) {
        return false;
    }

    public boolean isRegistered(UUID playerUuid) {
        return false;
    }
}
