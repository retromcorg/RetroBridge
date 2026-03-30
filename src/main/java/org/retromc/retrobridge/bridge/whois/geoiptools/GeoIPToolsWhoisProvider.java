package org.retromc.retrobridge.bridge.whois.geoiptools;

import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;
import org.retromc.retrobridge.bridge.whois.WhoisBridge;
import org.retromc.retrobridge.bridge.whois.WhoisProvider;

public class GeoIPToolsWhoisProvider extends AbstractBridgeProvider implements WhoisProvider {
    private final WhoisBridge whoisBridge = new GeoIPToolsWhoisBridge();

    public GeoIPToolsWhoisProvider(String ownerPluginName) {
        super("GeoIPTools", ownerPluginName, BridgeModuleType.WHOIS, "GeoIPTools");
    }

    public WhoisBridge getWhoisBridge() {
        return whoisBridge;
    }
}
