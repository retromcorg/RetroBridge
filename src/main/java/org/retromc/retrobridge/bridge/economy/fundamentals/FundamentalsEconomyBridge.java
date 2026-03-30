package org.retromc.retrobridge.bridge.economy.fundamentals;

import com.johnymuffin.beta.fundamentals.Fundamentals;
import com.johnymuffin.beta.fundamentals.api.EconomyAPI;
import org.retromc.retrobridge.bridge.economy.EconomyBridge;

import java.util.UUID;

public class FundamentalsEconomyBridge implements EconomyBridge {
    public boolean has(UUID playerUuid, double amount) {
        if (playerUuid == null) {
            return false;
        }

        EconomyAPI.BalanceWrapper balance = getBalanceWrapper(playerUuid);
        return balance != null
                && balance.getEconomyResult() == EconomyAPI.EconomyResult.successful
                && balance.getBalance() >= amount;
    }

    public double getBalance(UUID playerUuid) {
        if (playerUuid == null) {
            return 0.0D;
        }

        EconomyAPI.BalanceWrapper balance = getBalanceWrapper(playerUuid);
        if (balance == null || balance.getEconomyResult() != EconomyAPI.EconomyResult.successful) {
            return 0.0D;
        }
        return balance.getBalance();
    }

    public boolean deposit(UUID playerUuid, double amount) {
        if (playerUuid == null) {
            return false;
        }

        EconomyAPI economyApi = getEconomyApi();
        if (economyApi == null) {
            return false;
        }

        try {
            return economyApi.additionBalance(playerUuid, amount) == EconomyAPI.EconomyResult.successful;
        } catch (Throwable ignored) {
            return false;
        }
    }

    public boolean withdraw(UUID playerUuid, double amount) {
        if (playerUuid == null) {
            return false;
        }

        EconomyAPI economyApi = getEconomyApi();
        if (economyApi == null) {
            return false;
        }

        try {
            return economyApi.subtractBalance(playerUuid, amount) == EconomyAPI.EconomyResult.successful;
        } catch (Throwable ignored) {
            return false;
        }
    }

    private EconomyAPI.BalanceWrapper getBalanceWrapper(UUID playerUuid) {
        EconomyAPI economyApi = getEconomyApi();
        if (economyApi == null) {
            return null;
        }

        try {
            return economyApi.getBalance(playerUuid);
        } catch (Throwable ignored) {
            return null;
        }
    }

    private EconomyAPI getEconomyApi() {
        Fundamentals fundamentals = Fundamentals.getPlugin();
        if (fundamentals == null) {
            return null;
        }
        return fundamentals.getEconomyAPI();
    }
}
