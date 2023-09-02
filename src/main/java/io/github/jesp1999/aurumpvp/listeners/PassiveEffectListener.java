package io.github.jesp1999.aurumpvp.listeners;

import io.github.jesp1999.aurumpvp.events.KitChangeEvent;
import io.github.jesp1999.aurumpvp.kit.Kit;
import io.github.jesp1999.aurumpvp.player.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
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
        PlayerInfo playerInfo = PlayerInfo.activePlayers.get(player.getName());
        Kit currentKit = playerInfo.getCurrentKit();
        if (currentKit != null) {
            for (PotionEffect potionEffect : currentKit.getPotionEffects()) {
                player.addPotionEffect(potionEffect);
            }
        }
    }

    @EventHandler
    public void onKitChange(KitChangeEvent event) {
        Player player = event.getPlayer();
        Bukkit.broadcastMessage(player + " has changed their kit from " + event.getPreviousKit() + " to " + event.getCurrentKit());
        PlayerInfo playerInfo = PlayerInfo.activePlayers.get(player.getName());
        Kit currentKit = playerInfo.getCurrentKit();
        if (currentKit != null) {
            for (PotionEffect potionEffect : currentKit.getPotionEffects()) {
                player.addPotionEffect(potionEffect);
            }
        }
    }
}
