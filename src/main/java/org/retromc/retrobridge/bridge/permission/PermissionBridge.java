package org.retromc.retrobridge.bridge.permission;

import java.util.UUID;

public interface PermissionBridge {
    boolean has(UUID playerUuid, String permissionNode);

    boolean isInGroup(UUID playerUuid, String groupName);
}
