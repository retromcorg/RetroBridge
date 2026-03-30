package org.retromc.retrobridge.bridge.whois;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class DummyWhoisBridge implements WhoisBridge {
    public Map<String, String> getWhoisData(UUID playerUuid) {
        return Collections.emptyMap();
    }

    public Map<String, String> getWhoisData(String ip) {
        return Collections.emptyMap();
    }
}
