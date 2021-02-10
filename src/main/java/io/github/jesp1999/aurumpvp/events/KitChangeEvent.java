package io.github.jesp1999.aurumpvp.events;

import io.github.jesp1999.aurumpvp.kit.Kit;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class KitChangeEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private final Kit previousKit;
    private final Kit currentKit;
    private Location respawnLocation;

    public KitChangeEvent(@NotNull final Player player, final Kit previousKit, final Kit currentKit) {
        super(player);
        this.player = player;
        this.previousKit = previousKit;
        this.currentKit = currentKit;
    }

    /**
     * Gets the current respawn location
     *
     * @return Location current respawn location
     */
    @NotNull
    public Location getRespawnLocation() {
        return this.respawnLocation;
    }

    /**
     * Sets the new respawn location
     *
     * @param respawnLocation new location for the respawn
     */
    public void setRespawnLocation(@NotNull Location respawnLocation) {
        Validate.notNull(respawnLocation, "Respawn location can not be null");
        Validate.notNull(respawnLocation.getWorld(), "Respawn world can not be null");

        this.respawnLocation = respawnLocation;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
