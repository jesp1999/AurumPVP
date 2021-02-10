package io.github.jesp1999.aurumpvp.listeners;

import io.github.jesp1999.aurumpvp.events.KitChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PassiveEffectListener extends KitListener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Bukkit.broadcastMessage(event.getPlayer() + " is kind of pog but only a little bit");
    }

    @EventHandler
    public void onKitChange(KitChangeEvent event) {
        Bukkit.broadcastMessage("");
    }
}
