package org.retromc.retrobridge.bridge.whois.geoiptools;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.retromc.geoiptools.GeoIPTools;
import org.retromc.geoiptools.api.GeoIPLookupService;
import org.retromc.retrobridge.util.PlayerLookup;
import org.retromc.retrobridge.bridge.whois.WhoisBridge;

import java.net.InetSocketAddress;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class GeoIPToolsWhoisBridge implements WhoisBridge {
    public Map<String, String> getWhoisData(UUID playerUuid) {
        Player player = PlayerLookup.getOnlinePlayer(playerUuid);
        if (player == null) {
            return new LinkedHashMap<String, String>();
        }

        InetSocketAddress address = player.getAddress();
        return getWhoisData(address.getAddress().getHostAddress());
    }

    public Map<String, String> getWhoisData(String ip) {
        Map<String, String> data = new LinkedHashMap<String, String>();
        if (ip == null || ip.length() == 0) {
            return data;
        }

        data.put("ip", ip);

        GeoIPLookupService lookupService = getLookupService();
        if (lookupService == null) {
            return data;
        }

        try {
            String countryCode = lookupService.resolveCountryCode(ip);
            if (countryCode != null && countryCode.length() > 0) {
                data.put("country_code", countryCode);
            }
        } catch (Throwable ignored) {
        }

        return data;
    }

    private GeoIPLookupService getLookupService() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("GeoIPTools");
        if (plugin instanceof GeoIPTools) {
            return ((GeoIPTools) plugin).getGeoIPLookupService();
        }
        return null;
    }
}
