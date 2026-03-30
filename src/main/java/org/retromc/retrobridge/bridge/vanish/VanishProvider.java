package org.retromc.retrobridge.bridge.vanish;

import org.retromc.retrobridge.bridge.BridgeProvider;

public interface VanishProvider extends BridgeProvider {
    VanishBridge getVanishBridge();
}
