package io.github.jesp1999.aurumpvp.listeners;

import io.github.jesp1999.aurumpvp.events.KitChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PassiveEffectListener extends KitListener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Bukkit.broadcastMessage(player + " has respawned!");
        player.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, 30,1)); //test effect, pls change
    }

    @EventHandler
    public void onKitChange(KitChangeEvent event) {
        Player player = event.getPlayer();
        Bukkit.broadcastMessage(player + " has changed their kit from " + event.getPreviousKit() + " to " + event.getCurrentKit());
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 50,2)); //test effect, pls change
    }
}
