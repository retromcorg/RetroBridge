package org.retromc.retrobridge.bridge.whois;

import org.retromc.retrobridge.bridge.BridgeProvider;

public interface WhoisProvider extends BridgeProvider {
    WhoisBridge getWhoisBridge();
}
