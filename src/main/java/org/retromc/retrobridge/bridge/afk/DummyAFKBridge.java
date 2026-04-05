package org.retromc.retrobridge.bridge.afk;

import java.util.UUID;

public class DummyAFKBridge implements AFKBridge {
    public boolean isAFK(UUID playerUuid) {
        return false;
    }

    public boolean setAFK(UUID playerUuid, boolean afk) {
        return false;
    }
}