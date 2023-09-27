package io.github.jesp1999.aurumpvp.player;

import io.github.jesp1999.aurumpvp.kit.Kit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PlayerInfo {
    public static Map<String, PlayerInfo> activePlayers = new HashMap<>();
    private static Plugin plugin;
    private final @NotNull Player player;
    private Kit currentKit;
    private Kit previousKit;
    private final Map<String, RestockInformation> slotRestockInformation;
    private final Set<Integer> scheduledTasks;
    private boolean pacifist;

    /**
     *
     * @param player
     */
    public PlayerInfo(@NotNull Player player) {
        this.player = player;
        this.currentKit = null;
        this.previousKit = null;
        this.slotRestockInformation = new HashMap<>();
        this.scheduledTasks = new HashSet<>();
        this.pacifist = true;
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
        this.slotRestockInformation.clear();
        this.previousKit = this.currentKit;
        this.currentKit = currentKit;
    }

    public Kit getPreviousKit() {
        return this.previousKit;
    }

    public Map<String, RestockInformation> getSlotRestockInformation() {
        return this.slotRestockInformation;
    }

    public Set<Integer> getScheduledTasks() {
        return this.scheduledTasks;
    }

    public boolean isPacifist() {
        return this.pacifist;
    }

    public void setPacifist(boolean pacifist) {
        this.pacifist = pacifist;
    }
}
