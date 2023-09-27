package io.github.jesp1999.aurumpvp.game;

import io.github.jesp1999.aurumpvp.events.GameStartEvent;
import io.github.jesp1999.aurumpvp.player.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStartCountdownRunnable extends BukkitRunnable {

    private final JavaPlugin plugin;
    private final GameStartEvent gameStartEvent;

    private int counter;

    public GameStartCountdownRunnable(JavaPlugin plugin, int counter, GameStartEvent gameStartEvent) {
        this.plugin = plugin;
        if (counter <= 0) {
            throw new IllegalArgumentException("counter must be greater than 0");
        } else {
            this.counter = counter;
        }
        this.gameStartEvent = gameStartEvent;
    }

    @Override
    public void run() {
        if (counter > 0) {
            gameStartEvent.getGame().getWorld().getPlayers().forEach(p -> p.sendTitle("Game start in", Integer.toString(counter), 0, 20, 5));
            counter--;
        } else if (counter == 0) {
            Game.activeGame = gameStartEvent.getGame();
            gameStartEvent.getGame().getWorld().getPlayers().forEach(p -> p.sendTitle("Start!", "", 0, 40, 10));
            gameStartEvent.getGame().getParticipants().values().forEach((p) -> {
                Player player = p.getPlayer();
                PlayerInfo.activePlayers.get(player.getName()).setPacifist(false);
                player.getInventory().setItem(8, new ItemStack(Game.LEAVE_GAME_ITEM));
            });
            this.cancel();
        }
    }

}
