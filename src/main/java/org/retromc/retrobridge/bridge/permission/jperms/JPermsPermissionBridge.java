package org.retromc.retrobridge.bridge.permission.jperms;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.retromc.retrobridge.bridge.permission.PermissionBridge;

import java.util.UUID;

public class JPermsPermissionBridge implements PermissionBridge {

    @Override
    public boolean has(UUID playerUuid, String permissionNode) {
        Player player = Bukkit.getPlayer(playerUuid);
        if (player != null) {
            return player.hasPermission(permissionNode);
        }
        return false;
    }

    @Override
    public boolean isInGroup(UUID playerUuid, String groupName) {
        Player player = Bukkit.getPlayer(playerUuid);
        if (player != null) {
            return player.hasPermission("group." + groupName.toLowerCase());
        }
        return false;
    }
}
