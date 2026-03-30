package org.retromc.retrobridge.bridge.fakequit.fundamentals;

import com.johnymuffin.beta.fundamentals.Fundamentals;
import com.johnymuffin.beta.fundamentals.FundamentalsPlayerMap;
import com.johnymuffin.beta.fundamentals.hooks.permissions.PermissionsHook;
import com.johnymuffin.beta.fundamentals.player.FundamentalsPlayer;
import com.johnymuffin.beta.fundamentals.settings.FundamentalsConfig;
import com.johnymuffin.beta.fundamentals.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.retromc.retrobridge.bridge.fakequit.FakeQuitBridge;
import org.retromc.retrobridge.util.PlayerLookup;

import java.util.UUID;

public class FundamentalsFakeQuitBridge implements FakeQuitBridge {
    public boolean isFakeQuit(UUID playerUuid) {
        FundamentalsPlayer fundamentalsPlayer = getFundamentalsPlayer(playerUuid);
        return fundamentalsPlayer != null && fundamentalsPlayer.isFakeQuit();
    }

    public boolean setFakeQuit(UUID playerUuid, boolean fakeQuit) {
        // Not implemented
        return false;
    }

    private FundamentalsPlayer getFundamentalsPlayer(UUID playerUuid) {
        if (playerUuid == null) {
            return null;
        }

        Player player = PlayerLookup.getOnlinePlayer(playerUuid);
        Fundamentals fundamentals = Fundamentals.getPlugin();
        if (player == null || fundamentals == null) {
            return null;
        }

        FundamentalsPlayerMap playerMap = fundamentals.getPlayerMap();
        if (playerMap == null) {
            return null;
        }
        return playerMap.getPlayer(player);
    }
}
