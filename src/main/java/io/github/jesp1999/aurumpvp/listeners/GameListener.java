package io.github.jesp1999.aurumpvp.listeners;

import io.github.jesp1999.aurumpvp.events.GameStartEvent;
import io.github.jesp1999.aurumpvp.events.GameStopEvent;
import io.github.jesp1999.aurumpvp.game.GameStartCountdownRunnable;
import io.github.jesp1999.aurumpvp.game.Game;
import io.github.jesp1999.aurumpvp.game.Participant;
import io.github.jesp1999.aurumpvp.kit.Kit;
import io.github.jesp1999.aurumpvp.player.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameListener implements Listener {
    private final JavaPlugin plugin;

    public GameListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onParticipantDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Participant participant = Game.activeGame.getParticipants().get(player.getName()); // TODO look for the active game the player is participating in instead of using this static member
        if (participant == null)
            return;
        PlayerInfo playerInfo = PlayerInfo.activePlayers.get(player.getName());
        if (participant.getLives() > 0) {
            participant.setLives(participant.getLives() - 1);
            Kit currentKit = playerInfo.getCurrentKit();
            currentKit.equipKit(player);
        } else {
            Game.activeGame.getParticipants().remove(player.getName());
            Game.activeGame.getOrderedLosers().add(player.getName());
            if (Game.activeGame.getParticipants().size() == 1)
                plugin.getServer().getPluginManager().callEvent(new GameStopEvent(Game.activeGame));
        }
    }

    @EventHandler
    public void onStartGameItemHold(PlayerItemHeldEvent event) {
        if (event.getNewSlot() != 8)
            return;
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();
        ItemStack selectedItem = inventory.getItem(8);
        if (selectedItem == null || !selectedItem.isSimilar(Game.START_GAME_ITEM)) {
            return;
        }
        World world = player.getWorld();
        Game game = tryStartGame(world, event);
        if (game != null) {
            plugin.getServer().getPluginManager().callEvent(new GameStartEvent(game));
        }
    }

    public Game tryStartGame(World world, PlayerItemHeldEvent triggeringEvent) {
        List<Player> playersInWorld = world.getPlayers();
        List<Player> eligiblePlayers = playersInWorld.stream().filter((p) -> {
            ItemStack item = p.getInventory().getItem(8);
            return item != null && item.isSimilar(Game.START_GAME_ITEM);
        }).collect(Collectors.toList());
        Map<String, Participant> participants = new HashMap<>();
        for (Player player : eligiblePlayers) {
            if (triggeringEvent.getPlayer().equals(player)) {
                participants.put(player.getName(), new Participant(player));
            } else if (player.getInventory().getHeldItemSlot() == 8) {
                participants.put(player.getName(), new Participant(player));
            } else {
                return null; // not all players ready
            }
        }
        return new Game(world, participants);
    }

    @EventHandler
    public void onLeaveGameItemDrop(PlayerDropItemEvent event) {
        if (Game.activeGame != null) {
            String playerName = event.getPlayer().getName();
            PlayerInfo.activePlayers.get(playerName).setCurrentKit(null);
            Game.activeGame.getParticipants().remove(playerName);
            if (Game.activeGame.getParticipants().isEmpty()) {
                plugin.getServer().getPluginManager().callEvent(new GameStopEvent(Game.activeGame));
            }
        } else {
            Bukkit.broadcastMessage("no active game");
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player))
            return;
        Player player = (Player) event.getDamager();
        if (PlayerInfo.activePlayers.get(player.getName()).isPacifist())
            event.setCancelled(true);
    }

    @EventHandler
    public void onGameStart(GameStartEvent event) {
        event.getGame().getParticipants().forEach((k, v) -> v.getPlayer().getInventory().setItem(8, null));
        GameStartCountdownRunnable gameStartCountdownRunnable = new GameStartCountdownRunnable(this.plugin, 10, event);
        gameStartCountdownRunnable.runTaskTimer(plugin, 0, 20);
    }

    @EventHandler
    public void onGameStop(GameStopEvent event) {
        Game game = event.getGame();
        Map<String, Participant> participants = game.getParticipants();
        List<String> orderedLosers = game.getOrderedLosers();
        for (String loser : orderedLosers) {
            participants.get(loser).getPlayer().sendTitle(
                    "Game over!", "Winner is " + orderedLosers.get(orderedLosers.size() - 1), 10, 60, 10
            );
            PlayerInfo.activePlayers.get(loser).setPacifist(false);
        }
    }
}
