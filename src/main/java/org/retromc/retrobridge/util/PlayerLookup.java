package org.retromc.retrobridge.util;

import com.projectposeidon.api.PoseidonUUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public final class PlayerLookup {
    private PlayerLookup() {
    }

    public static Player getOnlinePlayer(UUID playerUuid) {
        // Just using the Bukkit method now Poseidon has been updated
        return Bukkit.getServer().getPlayer(playerUuid);
    }

    public static String getPlayerName(UUID playerUuid) {
        Player player = getOnlinePlayer(playerUuid);
        if (player != null) {
            return player.getName();
        }
        if (playerUuid == null) {
            return null;
        }

        try {
            return PoseidonUUID.getPlayerUsernameFromUUID(playerUuid);
        } catch (Throwable ignored) {
            return null;
        }
    }
}
