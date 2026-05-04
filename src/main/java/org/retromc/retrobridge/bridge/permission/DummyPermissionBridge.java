package org.retromc.retrobridge.bridge.permission;

import java.util.UUID;

public class DummyPermissionBridge implements PermissionBridge {
    public boolean has(UUID playerUuid, String permissionNode) {
        return false;
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
