package org.retromc.retrobridge.bridge.ignore;

import org.retromc.retrobridge.bridge.BridgeProvider;

public interface IgnoreProvider extends BridgeProvider {
    IgnoreBridge getIgnoreBridge();
}
