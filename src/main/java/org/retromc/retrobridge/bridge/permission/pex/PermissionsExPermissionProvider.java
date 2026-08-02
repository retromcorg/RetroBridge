package org.retromc.retrobridge.bridge.permission.pex;

import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;
import org.retromc.retrobridge.bridge.permission.PermissionBridge;
import org.retromc.retrobridge.bridge.permission.PermissionProvider;

public class PermissionsExPermissionProvider extends AbstractBridgeProvider implements PermissionProvider {
    private final PermissionBridge bridge = new PermissionsExPermissionBridge();

    public PermissionsExPermissionProvider(String ownerPluginName) {
        super("PermissionsEx", ownerPluginName, BridgeModuleType.PERMISSIONS, "PermissionsEx");
    }

    public PermissionBridge getPermissionBridge() {
        return bridge;
    }
}
