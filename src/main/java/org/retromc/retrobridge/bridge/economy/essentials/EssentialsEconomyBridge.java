package org.retromc.retrobridge.bridge.economy.essentials;

import com.earth2me.essentials.api.Economy;
import org.retromc.retrobridge.bridge.economy.EconomyBridge;
import org.retromc.retrobridge.util.PlayerLookup;

import java.util.UUID;

public class EssentialsEconomyBridge implements EconomyBridge {
    public boolean has(UUID playerUuid, double amount) {
        String playerName = PlayerLookup.getPlayerName(playerUuid);
        if (playerName == null) {
            return false;
        }
        try {
            return Economy.hasEnough(playerName, amount);
        } catch (Throwable ignored) {
            return false;
        }
    }

    public double getBalance(UUID playerUuid) {
        String playerName = PlayerLookup.getPlayerName(playerUuid);
        if (playerName == null) {
            return 0.0D;
        }
        try {
            return Economy.getMoney(playerName);
        } catch (Throwable ignored) {
            return 0.0D;
        }
    }

    public boolean deposit(UUID playerUuid, double amount) {
        String playerName = PlayerLookup.getPlayerName(playerUuid);
        if (playerName == null) {
            return false;
        }
        try {
            Economy.add(playerName, amount);
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }

    public boolean withdraw(UUID playerUuid, double amount) {
        String playerName = PlayerLookup.getPlayerName(playerUuid);
        if (playerName == null) {
            return false;
        }
        try {
            Economy.subtract(playerName, amount);
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }
}
