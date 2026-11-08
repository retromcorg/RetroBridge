package org.retromc.retrobridge.bridge.ignore.essentials;

import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;
import org.retromc.retrobridge.bridge.ignore.IgnoreBridge;
import org.retromc.retrobridge.bridge.ignore.IgnoreProvider;

public class EssentialsIgnoreProvider extends AbstractBridgeProvider implements IgnoreProvider {
    private final IgnoreBridge ignoreBridge = new EssentialsIgnoreBridge();

    public EssentialsIgnoreProvider(String ownerPluginName) {
        super("Essentials", ownerPluginName, BridgeModuleType.IGNORE, "Essentials");
    }

    public IgnoreBridge getIgnoreBridge() {
        return ignoreBridge;
    }
}
