package io.github.jesp1999.aurumpvp.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEventListener implements Listener {
    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) {
        PlayerInfo.activePlayers.put(event.getPlayer().getName(), new PlayerInfo(event.getPlayer()));
    }

    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) {
        PlayerInfo.activePlayers.remove(event.getPlayer().getName());
    }
}
