package org.retromc.retrobridge.bridge.auth;

import java.util.UUID;

public interface AuthBridge {
    boolean isAuthenticated(UUID playerUuid);

    boolean isRegistered(UUID playerUuid);
}
