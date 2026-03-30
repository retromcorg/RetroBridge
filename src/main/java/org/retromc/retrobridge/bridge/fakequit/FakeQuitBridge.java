package org.retromc.retrobridge.bridge.fakequit;

import java.util.UUID;

public interface FakeQuitBridge {
    boolean isFakeQuit(UUID playerUuid);

    boolean setFakeQuit(UUID playerUuid, boolean fakeQuit);
}
