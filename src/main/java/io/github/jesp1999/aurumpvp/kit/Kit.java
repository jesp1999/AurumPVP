package io.github.jesp1999.aurumpvp.kit;

import java.util.Map;
import java.util.Optional;

import org.bukkit.inventory.ItemStack;

/**
 * Class representing the information associated with a kit pvp kit
 * @author Julian Espada
 *
 */
public class Kit {
    private final KitName name;
    private final Map<String, Optional<ItemStack>> inventory;
    
    
    public static Kit ninja, bomber, tactician, hunter, sniper, archer, assassin, scout, medic, marauder;
    public static Kit knight, berserker, nymph, challenger, miner, reaper;
    public static Kit mage, shapeshifter, pyromancer, necromancer, spirit, druid, witchDoctor, warper, phaser;
    public static Kit paladin, protector, blacksmith, machinist, angel, frostWarden;
    
    /**
     * Initializes the base kits as static members of Kit
     */
    public static void initializeKits() {
        // TODO eventually convert this to reading of a config file
        // TODO initialize the inventories for each kit
        Kit.ninja = new Kit(KitName.NINJA, KitCategory.RANGED, Map.of());
        Kit.bomber = new Kit(KitName.BOMBER, KitCategory.RANGED, Map.of());
        Kit.tactician = new Kit(KitName.TACTICIAN, KitCategory.RANGED, Map.of());
        Kit.hunter = new Kit(KitName.HUNTER, KitCategory.RANGED, Map.of());
        Kit.sniper = new Kit(KitName.SNIPER, KitCategory.RANGED, Map.of());
        Kit.archer = new Kit(KitName.ARCHER, KitCategory.RANGED, Map.of());
        Kit.assassin = new Kit(KitName.ASSASSIN, KitCategory.RANGED, Map.of());
        Kit.scout = new Kit(KitName.SCOUT, KitCategory.RANGED, Map.of());
        Kit.medic = new Kit(KitName.MEDIC, KitCategory.RANGED, Map.of());
        Kit.marauder = new Kit(KitName.MARAUDER, KitCategory.RANGED, Map.of());
        Kit.knight = new Kit(KitName.KNIGHT, KitCategory.PHYSICAL, Map.of());
        Kit.berserker = new Kit(KitName.BERSERKER, KitCategory.PHYSICAL, Map.of());
        Kit.nymph = new Kit(KitName.NYMPH, KitCategory.PHYSICAL, Map.of());
        Kit.challenger = new Kit(KitName.CHALLENGER, KitCategory.PHYSICAL, Map.of());
        Kit.miner = new Kit(KitName.MINER, KitCategory.PHYSICAL, Map.of());
        Kit.reaper = new Kit(KitName.REAPER, KitCategory.PHYSICAL, Map.of());
        Kit.mage = new Kit(KitName.MAGE, KitCategory.MAGIC, Map.of());
        Kit.shapeshifter = new Kit(KitName.SHAPESHIFTER, KitCategory.MAGIC, Map.of());
        Kit.pyromancer = new Kit(KitName.PYROMANCER, KitCategory.MAGIC, Map.of());
        Kit.necromancer = new Kit(KitName.NECROMANCER, KitCategory.MAGIC, Map.of());
        Kit.spirit = new Kit(KitName.SPIRIT, KitCategory.MAGIC, Map.of());
        Kit.druid = new Kit(KitName.DRUID, KitCategory.MAGIC, Map.of());
        Kit.witchDoctor = new Kit(KitName.WITCH_DOCTOR, KitCategory.MAGIC, Map.of());
        Kit.warper = new Kit(KitName.WARPER, KitCategory.MAGIC, Map.of());
        Kit.phaser = new Kit(KitName.PHASER, KitCategory.MAGIC, Map.of());
        Kit.paladin = new Kit(KitName.PALADIN, KitCategory.TANK, Map.of());
        Kit.protector = new Kit(KitName.PROTECTOR, KitCategory.TANK, Map.of());
        Kit.blacksmith = new Kit(KitName.BLACKSMITH, KitCategory.TANK, Map.of());
        Kit.machinist = new Kit(KitName.MACHINIST, KitCategory.TANK, Map.of());
        Kit.angel = new Kit(KitName.ANGEL, KitCategory.TANK, Map.of());
        Kit.frostWarden = new Kit(KitName.FROST_WARDEN, KitCategory.TANK, Map.of());
    }
    
    /**
     * Constructor for a Kit based on identifiers and the respective inventory arrangement
     * @param name the KitName enum identifier for this kit
     * @param category KitCategory enum identifier for this kit
     * @param inventory map of the inventory slot names to Optional<ItemStack>, Optional.empty() if no item in the slot
     */
    public Kit(KitName name, KitCategory category, Map<String, Optional<ItemStack>> inventory) {
        this.name = name;
        this.inventory = inventory;
    }
    
    /**
     * Retrieves the name of the kit as a KitName enum
     * @return the KitName enum identifier for this kit
     */
    public KitName getName() {
        return this.name;
    }

    /**
     * Retrieves an Optional<ItemStack> of the item in the helmet slot, or Optional.empty() if one is not present where specified
     * @return the item in this kit's helmet slot, or Optional.empty() if there is no such item
     */
    public Optional<ItemStack> getHelmet() {
        return this.getItemAtSlot("armor.head");
    }
    
    /**
     * Retrieves an Optional<ItemStack> of the item in the chestplate slot, or Optional.empty() if one is not present where specified
     * @return the item in this kit's chestplate slot, or Optional.empty() if there is no such item
     */
    public Optional<ItemStack> getChestplate() {
        return this.getItemAtSlot("armor.chest");
    }
    
    /**
     * Retrieves an Optional<ItemStack> of the item in the leggings slot, or Optional.empty() if one is not present where specified
     * @return the item in this kit's leggings slot, or Optional.empty() if there is no such item
     */
    public Optional<ItemStack> getLeggings() {
        return this.getItemAtSlot("armor.legs");
    }
    
    /**
     * Retrieves an Optional<ItemStack> of the item in the boots slot, or Optional.empty() if one is not present where specified
     * @return the item in this kit's boots slot, or Optional.empty() if there is no such item
     */
    public Optional<ItemStack> getBoots() {
        return this.getItemAtSlot("armor.feet");
    }
    
    /**
     * Retrieves an Optional<ItemStack> of the item at a specified slot, or Optional.empty() if one is not present where specified
     * @param slotName the String identifier for the slot
     * @return the item in the specified slot, or Optional.empty() if there is no such item
     */
    public Optional<ItemStack> getItemAtSlot(String slotName) {
        return this.inventory.getOrDefault(slotName, Optional.empty());
    }
}
