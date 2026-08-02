package org.retromc.retrobridge.bridge.auth.osas;

import com.oldschoolminecraft.osas.OSAS;
import com.oldschoolminecraft.osas.impl.fallback.FallbackManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.retromc.retrobridge.bridge.auth.AuthBridge;
import org.retromc.retrobridge.util.PlayerLookup;

import java.util.UUID;

public class OSASAuthBridge implements AuthBridge {
    public boolean isAuthenticated(UUID playerUuid) {
        String playerName = PlayerLookup.getPlayerName(playerUuid);
        if (playerName == null) {
            return false;
        }

        FallbackManager fallbackManager = getFallbackManager();
        if (fallbackManager == null) {
            return false;
        }

        try {
            return fallbackManager.isAuthenticated(playerName);
        } catch (Throwable ignored) {
            return false;
        }
    }

    public boolean isRegistered(UUID playerUuid) {
        String playerName = PlayerLookup.getPlayerName(playerUuid);
        if (playerName == null) {
            return false;
        }

        FallbackManager fallbackManager = getFallbackManager();
        if (fallbackManager == null) {
            return false;
        }

        try {
            return fallbackManager.isRegistered(playerName);
        } catch (Throwable ignored) {
            return false;
        }
    }

    private FallbackManager getFallbackManager() {
        OSAS osas = getOSAS();
        if (osas == null) {
            return null;
        }
        return osas.fallbackManager;
    }

    private OSAS getOSAS() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("OSAS");
        if (plugin instanceof OSAS) {
            return (OSAS) plugin;
        }
        return null;
    }
}
