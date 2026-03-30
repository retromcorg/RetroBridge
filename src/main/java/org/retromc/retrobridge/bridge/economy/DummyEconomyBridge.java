package org.retromc.retrobridge.bridge.economy;

import java.util.UUID;

public class DummyEconomyBridge implements EconomyBridge {
    public boolean has(UUID playerUuid, double amount) {
        return false;
    }

    public double getBalance(UUID playerUuid) {
        return 0.0D;
    }

    public boolean deposit(UUID playerUuid, double amount) {
        return false;
    }

    public boolean withdraw(UUID playerUuid, double amount) {
        return false;
    }
}
