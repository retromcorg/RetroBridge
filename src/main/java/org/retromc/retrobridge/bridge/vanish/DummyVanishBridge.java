package org.retromc.retrobridge.bridge.vanish;

import java.util.UUID;

public class DummyVanishBridge implements VanishBridge {
    public boolean isVanished(UUID playerUuid) {
        return false;
    }

    public boolean setVanished(UUID playerUuid, boolean vanished) {
        return false;
    }
}
