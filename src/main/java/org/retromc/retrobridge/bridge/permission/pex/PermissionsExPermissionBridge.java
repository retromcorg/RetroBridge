package org.retromc.retrobridge.bridge.permission.pex;

import org.bukkit.entity.Player;
import org.retromc.retrobridge.bridge.permission.PermissionBridge;
import org.retromc.retrobridge.util.PlayerLookup;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.UUID;

public class PermissionsExPermissionBridge implements PermissionBridge {
    public boolean has(UUID playerUuid, String permissionNode) {
        if (playerUuid == null || permissionNode == null || permissionNode.trim().length() == 0) {
            return false;
        }

        PermissionManager manager = getManager();
        if (manager == null) {
            return false;
        }

        try {
            Player player = PlayerLookup.getOnlinePlayer(playerUuid);
            if (player != null) {
                return manager.has(player, permissionNode);
            }

            PermissionUser user = getUser(playerUuid);
            return user != null && user.has(permissionNode);
        } catch (Throwable ignored) {
            return false;
        }
    }

    public boolean isInGroup(UUID playerUuid, String groupName) {
        if (playerUuid == null || groupName == null || groupName.trim().length() == 0) {
            return false;
        }

        PermissionUser user = getUser(playerUuid);
        if (user == null) {
            return false;
        }

        try {
            return user.inGroup(groupName);
        } catch (Throwable ignored) {
            return false;
        }
    }

    public String[] getGroups(UUID playerUuid) {
        PermissionUser user = getUser(playerUuid);
        if (user == null) {
            return new String[0];
        }

        try {
            String[] groups = user.getGroupsNames();
            return groups == null ? new String[0] : groups;
        } catch (Throwable ignored) {
            return new String[0];
        }
    }

    public String getPrimaryGroup(UUID playerUuid) {
        String[] groups = getGroups(playerUuid);
        if (groups.length == 0 || groups[0] == null) {
            return "";
        }
        return groups[0];
    }

    public String getPrefix(UUID playerUuid) {
        PermissionUser user = getUser(playerUuid);
        if (user == null) {
            return "";
        }

        try {
            String prefix = user.getPrefix();
            return prefix == null ? "" : prefix;
        } catch (Throwable ignored) {
            return "";
        }
    }

    public String getSuffix(UUID playerUuid) {
        PermissionUser user = getUser(playerUuid);
        if (user == null) {
            return "";
        }

        try {
            String suffix = user.getSuffix();
            return suffix == null ? "" : suffix;
        } catch (Throwable ignored) {
            return "";
        }
    }

    private PermissionUser getUser(UUID playerUuid) {
        String playerName = PlayerLookup.getPlayerName(playerUuid);
        if (playerName == null) {
            return null;
        }

        PermissionManager manager = getManager();
        if (manager == null) {
            return null;
        }

        try {
            return manager.getUser(playerName);
        } catch (Throwable ignored) {
            return null;
        }
    }

    private PermissionManager getManager() {
        try {
            return PermissionsEx.getPermissionManager();
        } catch (Throwable ignored) {
            return null;
        }
    }
}
