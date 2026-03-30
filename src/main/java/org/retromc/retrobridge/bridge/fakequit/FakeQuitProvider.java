package org.retromc.retrobridge.bridge.fakequit;

import org.retromc.retrobridge.bridge.BridgeProvider;

public interface FakeQuitProvider extends BridgeProvider {
    FakeQuitBridge getFakeQuitBridge();
}
