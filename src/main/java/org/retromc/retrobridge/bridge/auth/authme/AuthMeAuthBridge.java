package org.retromc.retrobridge.bridge.auth.authme;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.retromc.retrobridge.bridge.auth.AuthBridge;
import org.retromc.retrobridge.util.PlayerLookup;
import uk.org.whoami.authme.AuthMe;
import uk.org.whoami.authme.cache.auth.PlayerCache;
import uk.org.whoami.authme.datasource.DataSource;

import java.util.UUID;

public class AuthMeAuthBridge implements AuthBridge {
    public boolean isAuthenticated(UUID playerUuid) {
        String playerName = PlayerLookup.getPlayerName(playerUuid);
        if (playerName == null) {
            return false;
        }
        try {
            return PlayerCache.getInstance().isAuthenticated(playerName.toLowerCase());
        } catch (Throwable ignored) {
            return false;
        }
    }

    public boolean isRegistered(UUID playerUuid) {
        String playerName = PlayerLookup.getPlayerName(playerUuid);
        if (playerName == null) {
            return false;
        }

        AuthMe authMe = getAuthMe();
        if (authMe == null) {
            return false;
        }

        try {
            DataSource dataSource = authMe.getAuthDatabase();
            return dataSource != null && dataSource.isAuthAvailable(playerName.toLowerCase());
        } catch (Throwable ignored) {
            return false;
        }
    }

    private AuthMe getAuthMe() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("AuthMe");
        if (plugin instanceof AuthMe) {
            return (AuthMe) plugin;
        }
        return null;
    }
}
