package org.retromc.retrobridge.bridge.ignore.essentials;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.retromc.retrobridge.bridge.ignore.IgnoreBridge;
import org.retromc.retrobridge.util.PlayerLookup;

import java.util.UUID;

public class EssentialsIgnoreBridge implements IgnoreBridge {
    @Override
    public boolean isIgnoring(UUID playerUuid, UUID targetUuid) {
        if (playerUuid == null || targetUuid == null) {
            return false;
        }

        User user = getEssentialsUser(playerUuid);
        OfflinePlayer target = Bukkit.getOfflinePlayer(targetUuid);
        return user != null && target != null && target.getName() != null && user.isIgnoredPlayer(target.getName());
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
