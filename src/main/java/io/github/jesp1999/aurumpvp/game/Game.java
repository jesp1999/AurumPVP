package io.github.jesp1999.aurumpvp.game;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class Game {
    public static Game activeGame;
    public static final ItemStack START_GAME_ITEM;
    public static final ItemStack LEAVE_GAME_ITEM;

    static {
        /*
        TODO COMMENTS
        can pick up arrows and tridents
        leaving game should get rid of kit
        potion effects dont store correctly
        potential idea: light gray glass pane in empty slots waiting to be restocked? glass pane count = number of seconds on restock
        written book isnt saved
        specific painting nbt
         */
        START_GAME_ITEM = new ItemStack(Material.LIME_WOOL);
        ItemMeta startGameItemMeta = START_GAME_ITEM.hasItemMeta() ? START_GAME_ITEM.getItemMeta() : Bukkit.getItemFactory().getItemMeta(START_GAME_ITEM.getType());
        Objects.requireNonNull(START_GAME_ITEM.getItemMeta()).setDisplayName("Hold to Start Game");
        Objects.requireNonNull(START_GAME_ITEM.getItemMeta()).setLocalizedName("Hold to Start Game");
        START_GAME_ITEM.setItemMeta(startGameItemMeta);
        LEAVE_GAME_ITEM = new ItemStack(Material.RED_WOOL);
        ItemMeta leaveGameItemMeta = LEAVE_GAME_ITEM.hasItemMeta() ? LEAVE_GAME_ITEM.getItemMeta() : Bukkit.getItemFactory().getItemMeta(LEAVE_GAME_ITEM.getType());
        Objects.requireNonNull(LEAVE_GAME_ITEM.getItemMeta()).setDisplayName("Drop to Leave Game");
        Objects.requireNonNull(LEAVE_GAME_ITEM.getItemMeta()).setLocalizedName("Drop to Leave Game");
        LEAVE_GAME_ITEM.setItemMeta(leaveGameItemMeta);
    }

    private final World world;
    private final Map<String, Participant> participants;
    private final List<String> orderedLosers;

    public Game(World world, Map<String, Participant> participants) {
        this.world = world;
        this.participants = participants;
        this.orderedLosers = new ArrayList<>();
    }

    public World getWorld() {
        return this.world;
    }

    public Map<String, Participant> getParticipants() {
        return this.participants;
    }

    public List<String> getOrderedLosers() {
        return this.orderedLosers;
    }
}
