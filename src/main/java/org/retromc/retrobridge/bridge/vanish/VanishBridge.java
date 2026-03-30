package org.retromc.retrobridge.bridge.vanish;

import java.util.UUID;

public interface VanishBridge {
    boolean isVanished(UUID playerUuid);

    boolean setVanished(UUID playerUuid, boolean vanished);
}
