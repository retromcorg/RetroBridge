package org.retromc.retrobridge.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Fired on the main thread when a player transitions to authenticated under the active auth provider.
 * Not fired for the dummy auth fallback.
 */
public class PlayerAuthenticatedEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player player;
    private final String providerName;

    public PlayerAuthenticatedEvent(Player player, String providerName) {
        super();
        this.player = player;
        this.providerName = providerName;
    }

    public Player getPlayer() {
        return player;
    }

    public String getProviderName() {
        return providerName;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
