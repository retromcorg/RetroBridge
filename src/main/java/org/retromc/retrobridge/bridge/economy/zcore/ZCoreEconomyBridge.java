package org.retromc.retrobridge.bridge.economy.zcore;

import me.zavdav.zcore.ZCore;
import me.zavdav.zcore.economy.PersonalAccount;
import me.zavdav.zcore.player.OfflinePlayer;
import org.retromc.retrobridge.bridge.economy.EconomyBridge;

import java.math.BigDecimal;
import java.util.UUID;

public class ZCoreEconomyBridge implements EconomyBridge {
    public boolean has(UUID playerUuid, double amount) {
        PersonalAccount account = getAccount(playerUuid);
        if (account == null) {
            return false;
        }

        return account.getBalance().doubleValue() >= amount;
    }

    public double getBalance(UUID playerUuid) {
        PersonalAccount account = getAccount(playerUuid);
        if (account == null) {
            return 0.0D;
        }

        return account.getBalance().doubleValue();
    }

    public boolean deposit(UUID playerUuid, double amount) {
        PersonalAccount account = getAccount(playerUuid);
        if (account == null) {
            return false;
        }

        try {
            account.add(BigDecimal.valueOf(amount));
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }

    public boolean withdraw(UUID playerUuid, double amount) {
        PersonalAccount account = getAccount(playerUuid);
        if (account == null) {
            return false;
        }

        try {
            return account.subtract(BigDecimal.valueOf(amount));
        } catch (Throwable ignored) {
            return false;
        }
    }

    private PersonalAccount getAccount(UUID playerUuid) {
        if (playerUuid == null) {
            return null;
        }

        try {
            // Assuming Offlineplayer is fine for even online players???
            //TODO: Zav to validate
            OfflinePlayer offlinePlayer = ZCore.getOfflinePlayer(playerUuid);
            if (offlinePlayer == null) {
                return null;
            }
            return offlinePlayer.getAccount();
        } catch (Throwable ignored) {
            return null;
        }
    }
}
