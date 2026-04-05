package org.retromc.retrobridge.bridge.afk;

import org.retromc.retrobridge.bridge.BridgeProvider;

public interface AFKProvider extends BridgeProvider {
    AFKBridge getAFKBridge();
}