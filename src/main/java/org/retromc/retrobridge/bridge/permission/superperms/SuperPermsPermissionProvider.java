package org.retromc.retrobridge.bridge.permission.superperms;

import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;
import org.retromc.retrobridge.bridge.permission.PermissionBridge;
import org.retromc.retrobridge.bridge.permission.PermissionProvider;

public class SuperPermsPermissionProvider extends AbstractBridgeProvider implements PermissionProvider {
    private final PermissionBridge bridge = new SuperPermsPermissionBridge();

    public SuperPermsPermissionProvider(String ownerPluginName) {
        super("SuperPerms", ownerPluginName, BridgeModuleType.PERMISSIONS, null);
    }

    public PermissionBridge getPermissionBridge() {
        return bridge;
    }
}
