package org.retromc.retrobridge.bridge.permission.superperms;

import org.bukkit.entity.Player;
import org.retromc.retrobridge.bridge.permission.PermissionBridge;
import org.retromc.retrobridge.util.PlayerLookup;

import java.util.UUID;

public class SuperPermsPermissionBridge implements PermissionBridge {
    public boolean has(UUID playerUuid, String permissionNode) {
        if (playerUuid == null || permissionNode == null || permissionNode.trim().length() == 0) {
            return false;
        }

        Player player = PlayerLookup.getOnlinePlayer(playerUuid);
        if (player == null) {
            return false;
        }

        try {
            return player.hasPermission(permissionNode);
        } catch (Throwable ignored) {
            return false;
        }
    }

    public boolean isInGroup(UUID playerUuid, String groupName) {
        return false;
    }

    public String[] getGroups(UUID playerUuid) {
        return new String[0];
    }

    public String getPrimaryGroup(UUID playerUuid) {
        return "";
    }

    public String getPrefix(UUID playerUuid) {
        return "";
    }

    public String getSuffix(UUID playerUuid) {
        return "";
    }
}
