package org.retromc.retrobridge.bridge.whois;

import java.util.Map;
import java.util.UUID;

public interface WhoisBridge {
    Map<String, String> getWhoisData(UUID playerUuid);

    Map<String, String> getWhoisData(String ip);
}
