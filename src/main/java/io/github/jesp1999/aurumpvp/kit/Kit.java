package io.github.jesp1999.aurumpvp.kit;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import io.github.jesp1999.aurumpvp.confighandler.JSONHandler;

/**
 * Class representing the information associated with a kit pvp kit
 * @author Julian Espada
 *
 */
public class Kit {
    private final String name;
    private final String category;
    private final Map<String, ItemStack> inventory;
    
    private static final Map<String, Integer> inventorySlots;
    
    static {
        inventorySlots = new HashMap<>();
        inventorySlots.put("hotbar.0", 0);
        inventorySlots.put("hotbar.1", 1);
        inventorySlots.put("hotbar.2", 2);
        inventorySlots.put("hotbar.3", 3);
        inventorySlots.put("hotbar.4", 4);
        inventorySlots.put("hotbar.5", 5);
        inventorySlots.put("hotbar.6", 6);
        inventorySlots.put("hotbar.7", 7);
        inventorySlots.put("hotbar.8", 8);
        inventorySlots.put("inventory.0", 9);
        inventorySlots.put("inventory.1", 10);
        inventorySlots.put("inventory.2", 11);
        inventorySlots.put("inventory.3", 12);
        inventorySlots.put("inventory.4", 13);
        inventorySlots.put("inventory.5", 14);
        inventorySlots.put("inventory.6", 15);
        inventorySlots.put("inventory.7", 16);
        inventorySlots.put("inventory.8", 17);
        inventorySlots.put("inventory.9", 18);
        inventorySlots.put("inventory.10", 19);
        inventorySlots.put("inventory.11", 20);
        inventorySlots.put("inventory.12", 21);
        inventorySlots.put("inventory.13", 22);
        inventorySlots.put("inventory.14", 23);
        inventorySlots.put("inventory.15", 24);
        inventorySlots.put("inventory.16", 25);
        inventorySlots.put("inventory.17", 26);
        inventorySlots.put("inventory.18", 27);
        inventorySlots.put("inventory.19", 28);
        inventorySlots.put("inventory.20", 29);
        inventorySlots.put("inventory.21", 30);
        inventorySlots.put("inventory.22", 31);
        inventorySlots.put("inventory.23", 32);
        inventorySlots.put("inventory.24", 33);
        inventorySlots.put("inventory.25", 34);
        inventorySlots.put("inventory.26", 35);
    }
    

    public static Map<String, Kit> kits;
    
//    public static Kit ninja, bomber, tactician, hunter, sniper, archer, assassin, scout, medic, marauder;
//    public static Kit knight, berserker, nymph, challenger, miner, reaper;
//    public static Kit mage, shapeshifter, pyromancer, necromancer, spirit, druid, witchDoctor, warper, phaser;
//    public static Kit paladin, protector, blacksmith, machinist, angel, frostWarden;
    
    /**
     * Initializes the base kits as static members of Kit
     * @param kitConfigFile
     * @return boolean representing initialization success
     */
    public static boolean initializeKits(File kitConfigFile) {
        try {
            Kit.kits = JSONHandler.importKits(kitConfigFile);
            return true;
        } catch (Exception e) {
            //TODO make this more readable
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Resets the static kits mapping
     * @return boolean representing reset success
     */
    public static boolean resetKits() {
        Kit.kits = new HashMap<>();
        return true;
    }
    
    /**
     * Constructor for a Kit based on identifiers and the respective inventory arrangement
     * @param name the KitName String identifier for this kit
     * @param category KitCategory String identifier for this kit
     * @param inventory map of the inventory slot names to ItemStack, null if no item in the slot
     */
    public Kit(String name, String category, Map<String, ItemStack> inventory) {
        this.name = name;
        this.category = category;
        this.inventory = inventory;
    }
    
    /**
     * Constructor for a Kit based on identifiers and the respective inventory arrangement
     * @param name the KitName String identifier for this kit
     * @param category KitCategory String identifier for this kit
     * @param inventory map of the inventory slot names to ItemStack, null if no item in the slot
     */
    public Kit(String name, String category, PlayerInventory inventory) {
        this.name = name;
        this.category = category;
        HashMap<String, ItemStack> inventoryMap = new HashMap<>();
        ItemStack helmetItem = inventory.getHelmet();
        if (helmetItem != null) {
            inventoryMap.put("armor.head", helmetItem);
        }
        ItemStack chestplateItem = inventory.getChestplate();
        if (chestplateItem != null) {
            inventoryMap.put("armor.chest", chestplateItem);
        }
        ItemStack leggingsItem = inventory.getLeggings();
        if (leggingsItem != null) {
            inventoryMap.put("armor.legs", leggingsItem);
        }
        ItemStack bootsItem = inventory.getBoots();
        if (bootsItem != null) {
            inventoryMap.put("armor.feet", helmetItem);
        }
        ItemStack offhandItem = inventory.getItemInOffHand();
        if (offhandItem != null) {
            inventoryMap.put("weapon.offhand", offhandItem);
        }
        for(final Map.Entry<String, Integer> entry : inventorySlots.entrySet()) {
            final ItemStack currentItem = inventory.getItem(entry.getValue());
            if (currentItem != null) {
                inventoryMap.put(entry.getKey(), currentItem);
            }
        }
        this.inventory = inventoryMap;
    }
    
    /**
     * Retrieves the category of the kit as a String
     * @return the category String identifier for this kit
     */
    public String getCategory() {
        return this.category;
    }
    
    /**
     * Retrieves the inventory of the kit as
     * a Map of inventory slot names to ItemStacks
     * @return Inventory associated with this kit
     */
    public Map<String,ItemStack> getInventory() {
    	return this.inventory;
    }
    
    /**
     * Retrieves the name of the kit as a String
     * @return the name String identifier for this kit
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves an Optional<ItemStack> of the item in the helmet slot, or null if one is not present where specified
     * @return the item in this kit's helmet slot, or null if there is no such item
     */
    public ItemStack getHelmet() {
        return this.getItemAtSlot("armor.head");
    }
    
    /**
     * Retrieves an Optional<ItemStack> of the item in the chestplate slot, or null if one is not present where specified
     * @return the item in this kit's chestplate slot, or null if there is no such item
     */
    public ItemStack getChestplate() {
        return this.getItemAtSlot("armor.chest");
    }
    
    /**
     * Retrieves an Optional<ItemStack> of the item in the leggings slot, or null if one is not present where specified
     * @return the item in this kit's leggings slot, or null if there is no such item
     */
    public ItemStack getLeggings() {
        return this.getItemAtSlot("armor.legs");
    }
    
    /**
     * Retrieves an Optional<ItemStack> of the item in the boots slot, or null if one is not present where specified
     * @return the item in this kit's boots slot, or null if there is no such item
     */
    public ItemStack getBoots() {
        return this.getItemAtSlot("armor.feet");
    }
    
    /**
     * Retrieves an Optional<ItemStack> of the item at a specified slot, or null if one is not present where specified
     * @param slotName the String identifier for the slot
     * @return the item in the specified slot, or null if there is no such item
     */
    public ItemStack getItemAtSlot(String slotName) {
        return this.inventory.getOrDefault(slotName, null);
    }
    
    /**
     * Attempts to equip this kit onto the specified player
     * @param player Player entity wishing to equip this kit
     * @return true if equipping action performed successfully, false otherwise
     */
    public boolean equipKit(Player player) {
        if (player == null) {
            return false;
        }
        final PlayerInventory playerInventory = player.getInventory();
        for (final Entry<String, Integer> inventoryEntry : Kit.inventorySlots.entrySet()) {
            playerInventory.setItem(inventoryEntry.getValue(), this.inventory.getOrDefault(inventoryEntry.getKey(), null));
        }
        playerInventory.setHelmet(this.inventory.getOrDefault("armor.head", null));
        playerInventory.setChestplate(this.inventory.getOrDefault("armor.chest", null));
        playerInventory.setLeggings(this.inventory.getOrDefault("armor.legs", null));
        playerInventory.setBoots(this.inventory.getOrDefault("armor.feet", null));
        playerInventory.setItemInOffHand(this.inventory.getOrDefault("weapon.offhand", null));
        return true; //TODO provide situations where this might be false
    }
}
