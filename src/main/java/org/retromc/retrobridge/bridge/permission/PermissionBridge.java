package org.retromc.retrobridge.bridge.permission;

import java.util.UUID;

public interface PermissionBridge {
    boolean has(UUID playerUuid, String permissionNode);

    boolean isInGroup(UUID playerUuid, String groupName);

    String[] getGroups(UUID playerUuid);

    String getPrimaryGroup(UUID playerUuid);

    String getPrefix(UUID playerUuid);

    String getSuffix(UUID playerUuid);
}
