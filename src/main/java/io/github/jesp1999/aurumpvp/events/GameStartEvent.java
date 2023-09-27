package io.github.jesp1999.aurumpvp.events;

import io.github.jesp1999.aurumpvp.game.Game;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class GameStartEvent extends GameEvent {
    private static final HandlerList handlers = new HandlerList();
    private final Game game;

    public GameStartEvent(Game game) {
        this.game = game;
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

    public Game getGame() {
        return this.game;
    }
}
