package io.github.jesp1999.aurumpvp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PassiveEffectListener extends KitListener {
    public void run() {

//        onPlayerRespawn();
    }
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Bukkit.broadcastMessage(event.getPlayer() + " is kind of pog but only a little bit");
    }
}
