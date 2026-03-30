package org.retromc.retrobridge.bridge.permission;

import java.util.UUID;

public class DummyPermissionBridge implements PermissionBridge {
    public boolean has(UUID playerUuid, String permissionNode) {
        return false;
    }

    public boolean isInGroup(UUID playerUuid, String groupName) {
        return false;
    }
}
