package io.github.jesp1999.aurumpvp.game;

import org.bukkit.entity.Player;

public class Participant {
    private final Player player;
    private int lives;
    private static final int MAX_LIVES = 2;

    public Participant(Player player) {
        this.player = player;
        this.lives = MAX_LIVES;
    }

    public Player getPlayer() {
        return player;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}
