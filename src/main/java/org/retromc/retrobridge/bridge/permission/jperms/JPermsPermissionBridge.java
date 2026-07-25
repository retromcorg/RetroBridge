package org.retromc.retrobridge.bridge.permission.jperms;

import com.johnymuffin.jperms.beta.JohnyPerms;
import com.johnymuffin.jperms.beta.JohnyPermsAPI;
import com.johnymuffin.jperms.core.models.PermissionsGroup;
import com.johnymuffin.jperms.core.models.PermissionsUser;
import org.retromc.retrobridge.bridge.permission.PermissionBridge;

import java.util.UUID;

public class JPermsPermissionBridge implements PermissionBridge {
    public boolean has(UUID playerUuid, String permissionNode) {
        PermissionsUser user = getUser(playerUuid);
        return user != null && permissionNode != null && permissionNode.trim().length() > 0
                && user.hasPermissionSomehow(permissionNode, true);
    }

    public boolean isInGroup(UUID playerUuid, String groupName) {
        if (playerUuid == null || groupName == null || groupName.trim().length() == 0) {
            return false;
        }

        String[] groups = getGroups(playerUuid);
        for (int i = 0; i < groups.length; i++) {
            if (groupName.equalsIgnoreCase(groups[i])) {
                return true;
            }
        }
        return false;
    }

    public String[] getGroups(UUID playerUuid) {
        PermissionsGroup primary = getPrimaryGroupObject(playerUuid);
        if (primary == null) {
            return new String[0];
        }

        String groupName = primary.getName();
        return groupName == null || groupName.length() == 0 ? new String[0] : new String[]{groupName};
    }

    public String getPrimaryGroup(UUID playerUuid) {
        PermissionsGroup group = getPrimaryGroupObject(playerUuid);
        if (group == null || group.getName() == null) {
            return "";
        }
        return group.getName();
    }

    public String getPrefix(UUID playerUuid) {
        PermissionsUser user = getUser(playerUuid);
        if (user == null) {
            return "";
        }

        String prefix = user.getPrefix();
        if (prefix != null && prefix.length() > 0) {
            return prefix;
        }

        PermissionsGroup group = getPrimaryGroupObject(playerUuid);
        if (group == null || group.getPrefix() == null) {
            return "";
        }
        return group.getPrefix();
    }

    public String getSuffix(UUID playerUuid) {
        PermissionsUser user = getUser(playerUuid);
        if (user == null) {
            return "";
        }

        String suffix = user.getSuffix();
        if (suffix != null && suffix.length() > 0) {
            return suffix;
        }

        PermissionsGroup group = getPrimaryGroupObject(playerUuid);
        if (group == null || group.getSuffix() == null) {
            return "";
        }
        return group.getSuffix();
    }

    private PermissionsUser getUser(UUID playerUuid) {
        if (playerUuid == null) {
            return null;
        }

        JohnyPermsAPI api = getApi();
        return api == null ? null : api.getUser(playerUuid);
    }

    private JohnyPermsAPI getApi() {
        return JohnyPerms.getJPermsAPI();
    }

    private PermissionsGroup getPrimaryGroupObject(UUID playerUuid) {
        PermissionsUser user = getUser(playerUuid);
        return user == null ? null : user.getGroup();
    }

}
