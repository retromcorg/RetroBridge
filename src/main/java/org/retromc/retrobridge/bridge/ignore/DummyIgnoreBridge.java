package org.retromc.retrobridge.bridge.ignore;

import java.util.UUID;

public class DummyIgnoreBridge implements IgnoreBridge {
    public boolean isIgnoring(UUID playerUuid, UUID targetUuid) {
        return false;
    }
}
