package org.retromc.retrobridge.bridge.afk.essentials;

import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;
import org.retromc.retrobridge.bridge.afk.AFKBridge;
import org.retromc.retrobridge.bridge.afk.AFKProvider;

public class EssentialsAFKProvider extends AbstractBridgeProvider implements AFKProvider {
    private final AFKBridge afkBridge = new EssentialsAFKBridge();

    public EssentialsAFKProvider(String ownerPluginName) {
        super("Essentials", ownerPluginName, BridgeModuleType.AFK, "Essentials");
    }

    public AFKBridge getAFKBridge() {
        return afkBridge;
    }
}
