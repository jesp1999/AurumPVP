package io.github.jesp1999.aurumpvp.listeners;

import io.github.jesp1999.aurumpvp.player.PlayerInfo;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DropItemListener implements Listener {
    private final JavaPlugin plugin;

    public DropItemListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (PlayerInfo.activePlayers.get(player.getName()).getCurrentKit() != null)
            event.setCancelled(true);
    }

    @EventHandler
    public void onClickItem(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        Player player = (Player)event.getWhoClicked();
        if (PlayerInfo.activePlayers.get(player.getName()).getCurrentKit() != null)
            event.setCancelled(true);
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;
        Player player = (Player)event.getEntity();
        if (PlayerInfo.activePlayers.get(player.getName()).getCurrentKit() != null)
            event.setCancelled(true);
    }

    @EventHandler
    public void onArrowPickup(PlayerPickupArrowEvent event) {
        Player player = event.getPlayer();
        if (PlayerInfo.activePlayers.get(player.getName()).getCurrentKit() != null)
            event.setCancelled(true);
    }
}
