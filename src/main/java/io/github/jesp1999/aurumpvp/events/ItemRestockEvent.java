package io.github.jesp1999.aurumpvp.events;

import io.github.jesp1999.aurumpvp.kit.Kit;
import io.github.jesp1999.aurumpvp.player.RestockInformation;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemRestockEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private final RestockInformation restockInformation;

    public ItemRestockEvent(@NotNull final Player player, @NotNull final RestockInformation restockInformation) {
        super(player);
        this.player = player;
        this.restockInformation = restockInformation;
    }

    /**
     * Gets the item stack to be restocked
     *
     * @return RestockInformation for the stack to be restocked
     */
    public RestockInformation getRestockInformation() {
        return this.restockInformation;
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
