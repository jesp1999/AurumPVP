package io.github.jesp1999.aurumpvp.player;

import io.github.jesp1999.aurumpvp.events.ItemRestockEvent;
import io.github.jesp1999.aurumpvp.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerEventListener implements Listener {
    private final JavaPlugin plugin;

    public PlayerEventListener(JavaPlugin plugin) {
        super();
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) {
        PlayerInfo.activePlayers.put(event.getPlayer().getName(), new PlayerInfo(event.getPlayer()));
    }

    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) {
        PlayerInfo.activePlayers.remove(event.getPlayer().getName());
    }

    @EventHandler
    public void onItemRestock(ItemRestockEvent event) {
        RestockInformation restockInformation = event.getRestockInformation();
        String slot = restockInformation.getSlot();
        ItemStack restockItemStack = restockInformation.getItemStack();
        Player player = event.getPlayer();
        ItemStack currentItemStack = Utils.getItemStackAtSlot(player, slot);
        if (currentItemStack == null || !restockInformation.getItemStack().getType().equals(currentItemStack.getType())) {
            Utils.setItemStackAtSlot(player, slot, restockItemStack);
        } else {
            currentItemStack.setAmount(Math.min(restockItemStack.getMaxStackSize(), currentItemStack.getAmount() + restockItemStack.getAmount()));
            Utils.setItemStackAtSlot(player, slot, currentItemStack);
        }
    }
}
