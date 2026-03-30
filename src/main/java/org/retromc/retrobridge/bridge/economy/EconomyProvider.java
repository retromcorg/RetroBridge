package org.retromc.retrobridge.bridge.economy;

import org.retromc.retrobridge.bridge.BridgeProvider;

public interface EconomyProvider extends BridgeProvider {
    EconomyBridge getEconomyBridge();
}
