package org.retromc.retrobridge.bridge.auth.xauth;

import com.cypherx.xauth.xAuth;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.retromc.retrobridge.bridge.auth.AuthBridge;
import org.retromc.retrobridge.util.PlayerLookup;

import java.util.UUID;

public class XAuthAuthBridge implements AuthBridge {
    public boolean isAuthenticated(UUID playerUuid) {
        Player player = PlayerLookup.getOnlinePlayer(playerUuid);
        if (player == null) {
            return false;
        }

        xAuth xauth = getXAuth();
        if (xauth == null) {
            return false;
        }

        try {
            return xauth.isLoggedIn(player).booleanValue();
        } catch (Throwable ignored) {
            return false;
        }
    }

    public boolean isRegistered(UUID playerUuid) {
        String playerName = PlayerLookup.getPlayerName(playerUuid);
        if (playerName == null) {
            return false;
        }

        xAuth xauth = getXAuth();
        if (xauth == null) {
            return false;
        }

        try {
            return xauth.isRegistered(playerName).booleanValue();
        } catch (Throwable ignored) {
            return false;
        }
    }

    private xAuth getXAuth() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("xAuth");
        if (plugin instanceof xAuth) {
            return (xAuth) plugin;
        }
        return null;
    }
}
