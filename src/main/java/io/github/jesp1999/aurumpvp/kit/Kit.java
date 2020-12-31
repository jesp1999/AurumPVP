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
    private final KitType type;
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
        Kit.ninja = new Kit(KitType.NINJA, KitCategory.RANGED, Map.of());
        Kit.bomber = new Kit(KitType.BOMBER, KitCategory.RANGED, Map.of());
        Kit.tactician = new Kit(KitType.TACTICIAN, KitCategory.RANGED, Map.of());
        Kit.hunter = new Kit(KitType.HUNTER, KitCategory.RANGED, Map.of());
        Kit.sniper = new Kit(KitType.SNIPER, KitCategory.RANGED, Map.of());
        Kit.archer = new Kit(KitType.ARCHER, KitCategory.RANGED, Map.of());
        Kit.assassin = new Kit(KitType.ASSASSIN, KitCategory.RANGED, Map.of());
        Kit.scout = new Kit(KitType.SCOUT, KitCategory.RANGED, Map.of());
        Kit.medic = new Kit(KitType.MEDIC, KitCategory.RANGED, Map.of());
        Kit.marauder = new Kit(KitType.MARAUDER, KitCategory.RANGED, Map.of());
        Kit.knight = new Kit(KitType.KNIGHT, KitCategory.PHYSICAL, Map.of());
        Kit.berserker = new Kit(KitType.BERSERKER, KitCategory.PHYSICAL, Map.of());
        Kit.nymph = new Kit(KitType.NYMPH, KitCategory.PHYSICAL, Map.of());
        Kit.challenger = new Kit(KitType.CHALLENGER, KitCategory.PHYSICAL, Map.of());
        Kit.miner = new Kit(KitType.MINER, KitCategory.PHYSICAL, Map.of());
        Kit.reaper = new Kit(KitType.REAPER, KitCategory.PHYSICAL, Map.of());
        Kit.mage = new Kit(KitType.MAGE, KitCategory.MAGIC, Map.of());
        Kit.shapeshifter = new Kit(KitType.SHAPESHIFTER, KitCategory.MAGIC, Map.of());
        Kit.pyromancer = new Kit(KitType.PYROMANCER, KitCategory.MAGIC, Map.of());
        Kit.necromancer = new Kit(KitType.NECROMANCER, KitCategory.MAGIC, Map.of());
        Kit.spirit = new Kit(KitType.SPIRIT, KitCategory.MAGIC, Map.of());
        Kit.druid = new Kit(KitType.DRUID, KitCategory.MAGIC, Map.of());
        Kit.witchDoctor = new Kit(KitType.WITCH_DOCTOR, KitCategory.MAGIC, Map.of());
        Kit.warper = new Kit(KitType.WARPER, KitCategory.MAGIC, Map.of());
        Kit.phaser = new Kit(KitType.PHASER, KitCategory.MAGIC, Map.of());
        Kit.paladin = new Kit(KitType.PALADIN, KitCategory.TANK, Map.of());
        Kit.protector = new Kit(KitType.PROTECTOR, KitCategory.TANK, Map.of());
        Kit.blacksmith = new Kit(KitType.BLACKSMITH, KitCategory.TANK, Map.of());
        Kit.machinist = new Kit(KitType.MACHINIST, KitCategory.TANK, Map.of());
        Kit.angel = new Kit(KitType.ANGEL, KitCategory.TANK, Map.of());
        Kit.frostWarden = new Kit(KitType.FROST_WARDEN, KitCategory.TANK, Map.of());
    }
    
    /**
     * Constructor for a Kit based on identifiers and the respective inventory arrangement
     * @param type the KitType enum identifier for this kit
     * @param category KitCategory enum identifier for this kit
     * @param inventory map of the inventory slot names to Optional<ItemStack>, Optional.empty() if no item in the slot
     */
    public Kit(KitType type, KitCategory category, Map<String, Optional<ItemStack>> inventory) {
        this.type = type;
        this.inventory = inventory;
    }
    
    /**
     * Retrieves the name of the kit as a KitType enum
     * @return the KitType enum identifier for this kit
     */
    public KitType getType() {
        return this.type;
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
