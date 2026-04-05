package org.retromc.retrobridge.bridge.afk;

import java.util.UUID;

public interface AFKBridge {
    boolean isAFK(UUID playerUuid);

    boolean setAFK(UUID playerUuid, boolean afk);
}