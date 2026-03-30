package org.retromc.retrobridge.bridge.vanish.fundamentals;

import com.johnymuffin.beta.fundamentals.Fundamentals;
import com.johnymuffin.beta.fundamentals.FundamentalsPlayerMap;
import com.johnymuffin.beta.fundamentals.commands.CommandVanish;
import com.johnymuffin.beta.fundamentals.player.FundamentalsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.retromc.retrobridge.bridge.vanish.VanishBridge;

import java.util.UUID;

public class FundamentalsVanishBridge implements VanishBridge {
    public boolean isVanished(UUID playerUuid) {
        FundamentalsPlayer player = getFundamentalsPlayer(playerUuid);
        return player != null && player.isVanished();
    }

    public boolean setVanished(UUID playerUuid, boolean vanished) {
        Fundamentals fundamentals = Fundamentals.getPlugin();
        FundamentalsPlayer player = getFundamentalsPlayer(playerUuid);
        if (fundamentals == null || player == null) {
            return false;
        }
        if (player.isVanished() == vanished) {
            return true;
        }

        try {
            new CommandVanish(fundamentals).toggleVanish(player);
            return player.isVanished() == vanished;
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
