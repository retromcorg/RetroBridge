package org.retromc.retrobridge.bridge.ignore.zcore;

import me.zavdav.zcore.ZCore;
import me.zavdav.zcore.player.OfflinePlayer;
import org.retromc.retrobridge.bridge.ignore.IgnoreBridge;

import java.util.UUID;

public class ZCoreIgnoreBridge implements IgnoreBridge {
    @Override
    public boolean isIgnoring(UUID playerUuid, UUID targetUuid) {
        if (playerUuid == null || targetUuid == null) {
            return false;
        }

        OfflinePlayer player = getPlayer(playerUuid);
        OfflinePlayer target = getPlayer(targetUuid);
        return player != null && target != null && player.ignores(target);
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
