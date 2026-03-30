package org.retromc.retrobridge.bridge.economy;

import java.util.UUID;

public interface EconomyBridge {
    boolean has(UUID playerUuid, double amount);

    double getBalance(UUID playerUuid);

    boolean deposit(UUID playerUuid, double amount);

    boolean withdraw(UUID playerUuid, double amount);
}
