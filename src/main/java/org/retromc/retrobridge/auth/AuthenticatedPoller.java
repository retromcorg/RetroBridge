package org.retromc.retrobridge.auth;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.retromc.retrobridge.RetroBridge;
import org.retromc.retrobridge.api.event.PlayerAuthenticatedEvent;
import org.retromc.retrobridge.bridge.BridgeModuleType;
import org.retromc.retrobridge.bridge.BridgeProvider;
import org.retromc.retrobridge.bridge.auth.AuthBridge;
import org.retromc.retrobridge.bridge.auth.AuthProvider;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AuthenticatedPoller implements Runnable {
    private final RetroBridge plugin;
    private final Set<UUID> authenticatedPlayers = new HashSet<UUID>();
    private int taskId = -1;

    public AuthenticatedPoller(RetroBridge plugin) {
        this.plugin = plugin;
    }

    public void start() {
        if (taskId != -1) {
            return;
        }
        taskId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 5L, 5L);
    }

    public void stop() {
        if (taskId == -1) {
            return;
        }
        plugin.getServer().getScheduler().cancelTask(taskId);
        taskId = -1;
        authenticatedPlayers.clear();
    }

    public void run() {
        BridgeProvider provider = plugin.getBridgeManager().getActiveProvider(BridgeModuleType.AUTH);
        if (!(provider instanceof AuthProvider) || provider.getSupportedPluginName() == null) {
            authenticatedPlayers.clear();
            return;
        }

        AuthBridge authBridge = ((AuthProvider) provider).getAuthBridge();
        if (authBridge == null) {
            authenticatedPlayers.clear();
            return;
        }

        Set<UUID> onlinePlayers = new HashSet<UUID>();
        for(Player player : Bukkit.getOnlinePlayers()) {
            UUID playerUuid = player.getUniqueId();
            onlinePlayers.add(playerUuid);

            boolean authenticated = isAuthenticated(authBridge, playerUuid);
            if (authenticated) {
                if (authenticatedPlayers.add(playerUuid)) {
                    plugin.debug("Firing PlayerAuthenticatedEvent for " + player.getName() + " via " + provider.getProviderName() + ".");
                    plugin.getServer().getPluginManager().callEvent(new PlayerAuthenticatedEvent(player, provider.getProviderName()));
                }
            } else {
                authenticatedPlayers.remove(playerUuid);
            }
        }

        authenticatedPlayers.retainAll(onlinePlayers);
    }

    private boolean isAuthenticated(AuthBridge authBridge, UUID playerUuid) {
        try {
            return authBridge.isAuthenticated(playerUuid);
        } catch (Exception e) {
            plugin.debug("Auth poll failed for " + playerUuid + ": " + e.getMessage());
            return false;
        }
    }
}
