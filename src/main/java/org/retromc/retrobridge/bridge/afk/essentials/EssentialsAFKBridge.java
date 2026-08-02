package org.retromc.retrobridge.bridge.afk.essentials;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.retromc.retrobridge.bridge.afk.AFKBridge;
import org.retromc.retrobridge.util.PlayerLookup;

import java.util.UUID;

public class EssentialsAFKBridge implements AFKBridge {
    public boolean isAFK(UUID playerUuid) {
        User user = getEssentialsUser(playerUuid);
        return user != null && user.isAfk();
    }

    public boolean setAFK(UUID playerUuid, boolean afk) {
        User user = getEssentialsUser(playerUuid);
        if (user == null) {
            return false;
        }

        if (user.isAfk() == afk) {
            return true;
        }

        try {
            user.setAfk(afk);
            return user.isAfk() == afk;
        } catch (Throwable ignored) {
            return false;
        }
    }

    private User getEssentialsUser(UUID playerUuid) {
        if (playerUuid == null) {
            return null;
        }

        Essentials essentials = getEssentials();
        if (essentials == null) {
            return null;
        }

        Player player = PlayerLookup.getOnlinePlayer(playerUuid);
        if (player == null) {
            return null;
        }

        try {
            return essentials.getUser(player);
        } catch (Throwable ignored) {
            return null;
        }
    }

    private Essentials getEssentials() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("Essentials");
        if (plugin instanceof Essentials) {
            return (Essentials) plugin;
        }
        return null;
    }
}
