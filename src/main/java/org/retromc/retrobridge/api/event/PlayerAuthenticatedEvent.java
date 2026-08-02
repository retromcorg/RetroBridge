package org.retromc.retrobridge.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class PlayerAuthenticatedEvent extends Event {
    private final Player player;
    private final String providerName;

    public PlayerAuthenticatedEvent(Player player, String providerName) {
        super("PlayerAuthenticatedEvent");
        this.player = player;
        this.providerName = providerName;
    }

    public Player getPlayer() {
        return player;
    }

    public String getProviderName() {
        return providerName;
    }
}
