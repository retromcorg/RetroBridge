package org.retromc.retrobridge.bridge.fakequit;

import java.util.UUID;

public class DummyFakeQuitBridge implements FakeQuitBridge {
    public boolean isFakeQuit(UUID playerUuid) {
        return false;
    }

    public boolean setFakeQuit(UUID playerUuid, boolean fakeQuit) {
        return false;
    }
}
