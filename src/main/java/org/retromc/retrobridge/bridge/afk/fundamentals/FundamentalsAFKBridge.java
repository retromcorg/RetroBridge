package org.retromc.retrobridge.bridge.afk.fundamentals;

import com.johnymuffin.beta.fundamentals.Fundamentals;
import com.johnymuffin.beta.fundamentals.FundamentalsPlayerMap;
import com.johnymuffin.beta.fundamentals.player.FundamentalsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.retromc.retrobridge.bridge.afk.AFKBridge;

import java.util.UUID;

// TODO: make sure zcore afk check is safe, add at a later date
public class FundamentalsAFKBridge implements AFKBridge {

    public boolean isAFK(UUID playerUuid) {
        FundamentalsPlayer player = getFundamentalsPlayer(playerUuid);
        return player != null && player.isAFK();
    }

    public boolean setAFK(UUID playerUuid, boolean afk) {
        Fundamentals fundamentals = Fundamentals.getPlugin();
        FundamentalsPlayer player = getFundamentalsPlayer(playerUuid);

        if (fundamentals == null || player == null) {
            return false;
        }

        if (player.isAFK() == afk) {
            return true;
        }

        try {
            player.setAFK(afk);
            return player.isAFK() == afk;
        } catch (Throwable ignored) {
            return false;
        }
    }

    private FundamentalsPlayer getFundamentalsPlayer(UUID playerUuid) {
        if (playerUuid == null) {
            return null;
        }

        Player bukkitPlayer = null;
        Player[] onlinePlayers = Bukkit.getServer().getOnlinePlayers();
        for (int i = 0; i < onlinePlayers.length; i++) {
            Player player = onlinePlayers[i];
            if (playerUuid.equals(player.getUniqueId())) {
                bukkitPlayer = player;
                break;
            }
        }

        if (bukkitPlayer == null) {
            return null;
        }

        Fundamentals fundamentals = Fundamentals.getPlugin();
        if (fundamentals == null) {
            return null;
        }

        FundamentalsPlayerMap playerMap = fundamentals.getPlayerMap();
        if (playerMap == null) {
            return null;
        }

        return playerMap.getPlayer(playerUuid);
    }
}