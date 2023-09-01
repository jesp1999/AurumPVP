package io.github.jesp1999.aurumpvp.events;

import io.github.jesp1999.aurumpvp.kit.Kit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class KitChangeEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private final Kit previousKit;
    private final Kit currentKit;

    public KitChangeEvent(@NotNull final Player player, final Kit previousKit, final Kit currentKit) {
        super(player);
        this.player = player;
        this.previousKit = previousKit;
        this.currentKit = currentKit;
    }

    /**
     * Gets the previously equipped kit
     *
     * @return Kit previously equipped kit
     */
    public Kit getPreviousKit() {
        return this.previousKit;
    }

    /**
     * Gets the currently equipped kit
     *
     * @return Kit currently equipped kit
     */
    public Kit getCurrentKit() {
        return this.currentKit;
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
