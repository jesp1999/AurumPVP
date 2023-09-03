package io.github.jesp1999.aurumpvp.player;

import io.github.jesp1999.aurumpvp.events.KitChangeEvent;
import io.github.jesp1999.aurumpvp.kit.Kit;
import io.github.jesp1999.aurumpvp.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class PlayerInfo {
    public static Map<String, PlayerInfo> activePlayers = new HashMap<>();
    private static Plugin plugin;
    private final @NotNull Player player;
    private Kit currentKit;
    private Kit previousKit;
    private Map<String, RestockInformation> slotRestockInformation;

    /**
     *
     * @param player
     */
    public PlayerInfo(@NotNull Player player) {
        this.player = player;
        this.currentKit = null;
        this.previousKit = null;
    }

    /**
     *
     * @param player
     * @param currentKit
     * @param previousKit
     */
    public PlayerInfo(@NotNull Player player, Kit currentKit, Kit previousKit) {
        this.player = player;
        this.currentKit = currentKit;
        this.previousKit = previousKit;
    }

    /**
     * Attaches a given plugin to this kit statically
     * @param plugin instance of the plugin class for this server
     */
    public static void setPlugin(Plugin plugin) {
        PlayerInfo.plugin = plugin;
    }

    public Kit getCurrentKit() {
        return this.currentKit;
    }

    public void setCurrentKit(Kit currentKit) {
        this.previousKit = this.currentKit;
        this.currentKit = currentKit;
    }

    public Kit getPreviousKit() {
        return this.previousKit;
    }

    public Map<String, RestockInformation> getSlotRestockInformation() {
        return this.slotRestockInformation;
    }
}
