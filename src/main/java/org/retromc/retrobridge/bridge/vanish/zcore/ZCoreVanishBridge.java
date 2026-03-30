package org.retromc.retrobridge.bridge.vanish.zcore;

import me.zavdav.zcore.ZCore;
import me.zavdav.zcore.player.OfflinePlayer;
import org.retromc.retrobridge.bridge.vanish.VanishBridge;

import java.util.UUID;

public class ZCoreVanishBridge implements VanishBridge {
    public boolean isVanished(UUID playerUuid) {
        OfflinePlayer player = getPlayer(playerUuid);
        return player != null && player.isVanished();
    }

    public boolean setVanished(UUID playerUuid, boolean vanished) {
        OfflinePlayer player = getPlayer(playerUuid);
        if (player == null) {
            return false;
        }

        if (player.isVanished() == vanished) {
            return true;
        }

        try {
            player.setVanished(vanished);
            return player.isVanished() == vanished;
        } catch (Throwable ignored) {
            return false;
        }
    }

    private OfflinePlayer getPlayer(UUID playerUuid) {
        if (playerUuid == null) {
            return null;
        }

        try {
            return ZCore.getOfflinePlayer(playerUuid);
        } catch (Throwable ignored) {
            return null;
        }
    }
}
