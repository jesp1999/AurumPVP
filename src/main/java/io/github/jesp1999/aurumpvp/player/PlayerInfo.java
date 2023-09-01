package io.github.jesp1999.aurumpvp.player;

import io.github.jesp1999.aurumpvp.events.KitChangeEvent;
import io.github.jesp1999.aurumpvp.kit.Kit;
import io.github.jesp1999.aurumpvp.listeners.PassiveEffectListener;
import io.github.jesp1999.aurumpvp.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class PlayerInfo {
    private final @NotNull Player player;
    private Kit currentKit;
    private static Plugin plugin;

    /**
     *
     * @param player
     */
    public PlayerInfo(@NotNull Player player) {
        this.player = player;
        this.currentKit = null;
    }

    /**
     *
     * @param player
     * @param kit
     */
    public PlayerInfo(@NotNull Player player, Kit kit) {
        this.player = player;
        this.currentKit = kit;
    }

    /**
     * Attaches a given plugin to this kit statically
     * @param plugin instance of the plugin class for this server
     */
    public static void setPlugin(Plugin plugin) {
        PlayerInfo.plugin = plugin;
    }

    public boolean equipKit(Kit kit) {
        if (this.player == null) {
            return false;
        }
        final PlayerInventory playerInventory = this.player.getInventory();
        final Map<String, ItemStack> kitInventory = kit.getInventory();
        for (final Map.Entry<String, Integer> inventoryEntry : Utils.inventorySlots.entrySet()) {
            playerInventory.setItem(inventoryEntry.getValue(), kitInventory.getOrDefault(inventoryEntry.getKey(), null));
        }
        playerInventory.setHelmet(kitInventory.getOrDefault("armor.head", null));
        playerInventory.setChestplate(kitInventory.getOrDefault("armor.chest", null));
        playerInventory.setLeggings(kitInventory.getOrDefault("armor.legs", null));
        playerInventory.setBoots(kitInventory.getOrDefault("armor.feet", null));
        playerInventory.setItemInOffHand(kitInventory.getOrDefault("weapon.offhand", null));

        KitChangeEvent kitChangeEvent = new KitChangeEvent(player, this.currentKit, kit);
        PlayerInfo.plugin.getServer().getPluginManager().callEvent(kitChangeEvent);
        this.currentKit = kit;


        return true; //TODO provide situations where this might be false
    }
}
