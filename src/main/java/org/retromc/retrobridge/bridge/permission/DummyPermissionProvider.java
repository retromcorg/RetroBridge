package org.retromc.retrobridge.bridge.permission;

import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;

public class DummyPermissionProvider extends AbstractBridgeProvider implements PermissionProvider {
    private final PermissionBridge bridge = new DummyPermissionBridge();

    public DummyPermissionProvider(String ownerPluginName) {
        super("DummyPermissions", ownerPluginName, BridgeModuleType.PERMISSIONS, null);
    }

    public PermissionBridge getPermissionBridge() {
        return bridge;
    }
}
