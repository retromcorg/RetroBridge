package org.retromc.retrobridge.bridge.permission.jperms;

import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;
import org.retromc.retrobridge.bridge.permission.PermissionBridge;
import org.retromc.retrobridge.bridge.permission.PermissionProvider;

public class JPermsPermissionProvider extends AbstractBridgeProvider implements PermissionProvider {
    private final PermissionBridge bridge = new JPermsPermissionBridge();

    public JPermsPermissionProvider(String ownerPluginName) {
        super("JPerms", ownerPluginName, BridgeModuleType.PERMISSIONS, "JPerms");
    }

    @Override
    public PermissionBridge getPermissionBridge() {
        return bridge;
    }
}
