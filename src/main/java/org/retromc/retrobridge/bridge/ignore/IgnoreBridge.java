package org.retromc.retrobridge.bridge.ignore;

import java.util.UUID;

public interface IgnoreBridge {
    boolean isIgnoring(UUID playerUuid, UUID targetUuid);
}
