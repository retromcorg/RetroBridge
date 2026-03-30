package org.retromc.retrobridge.bridge.permission;

import org.retromc.retrobridge.bridge.BridgeProvider;

public interface PermissionProvider extends BridgeProvider {
    PermissionBridge getPermissionBridge();
}
