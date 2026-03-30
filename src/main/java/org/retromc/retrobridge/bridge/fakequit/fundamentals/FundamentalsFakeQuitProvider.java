package org.retromc.retrobridge.bridge.fakequit.fundamentals;

import org.retromc.retrobridge.bridge.AbstractBridgeProvider;
import org.retromc.retrobridge.bridge.BridgeModuleType;
import org.retromc.retrobridge.bridge.fakequit.FakeQuitBridge;
import org.retromc.retrobridge.bridge.fakequit.FakeQuitProvider;

public class FundamentalsFakeQuitProvider extends AbstractBridgeProvider implements FakeQuitProvider {
    private final FakeQuitBridge fakeQuitBridge = new FundamentalsFakeQuitBridge();

    public FundamentalsFakeQuitProvider(String ownerPluginName) {
        super("Fundamentals", ownerPluginName, BridgeModuleType.FAKEQUIT, "Fundamentals");
    }

    public FakeQuitBridge getFakeQuitBridge() {
        return fakeQuitBridge;
    }
}
