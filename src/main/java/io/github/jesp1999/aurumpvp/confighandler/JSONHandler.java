package io.github.jesp1999.aurumpvp.confighandler;

import io.github.jesp1999.aurumpvp.kit.Kit;
import io.github.jesp1999.aurumpvp.map.MapInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Used to load from JSON config files
 * @author Ian McDowell
 *
 */
public class JSONHandler extends JSONConstants{
	
	private static final Map<String, Enchantment> enchantmentMap = new HashMap<>(); 
	private static final Map<Enchantment, String> reverseEnchantmentMap = new HashMap<>();
	static {
		enchantmentMap.put("protection", Enchantment.PROTECTION_ENVIRONMENTAL);
        enchantmentMap.put("fire_protection", Enchantment.PROTECTION_FIRE);
        enchantmentMap.put("feather_falling", Enchantment.PROTECTION_FALL);
        enchantmentMap.put("blast_protection", Enchantment.PROTECTION_EXPLOSIONS);
        enchantmentMap.put("projectile_protection", Enchantment.PROTECTION_PROJECTILE);
        enchantmentMap.put("respiration", Enchantment.OXYGEN);
        enchantmentMap.put("aqua_affinity", Enchantment.WATER_WORKER);
        enchantmentMap.put("thorns", Enchantment.THORNS);
        enchantmentMap.put("depth_strider", Enchantment.DEPTH_STRIDER);
        enchantmentMap.put("frost_walker", Enchantment.FROST_WALKER);
        enchantmentMap.put("curse_of_binding", Enchantment.BINDING_CURSE);
        enchantmentMap.put("sharpness", Enchantment.DAMAGE_ALL);
        enchantmentMap.put("smite", Enchantment.DAMAGE_UNDEAD);
        enchantmentMap.put("bane_of_arthropods", Enchantment.DAMAGE_ARTHROPODS);
        enchantmentMap.put("knockback", Enchantment.KNOCKBACK);
        enchantmentMap.put("fire_aspect", Enchantment.FIRE_ASPECT);
        enchantmentMap.put("looting", Enchantment.LOOT_BONUS_MOBS);
        enchantmentMap.put("sweeping_edge", Enchantment.SWEEPING_EDGE);
        enchantmentMap.put("efficiency", Enchantment.DIG_SPEED);
        enchantmentMap.put("silk_touch", Enchantment.SILK_TOUCH);
        enchantmentMap.put("unbreaking", Enchantment.DURABILITY);
        enchantmentMap.put("fortune", Enchantment.LOOT_BONUS_BLOCKS);
        enchantmentMap.put("power", Enchantment.ARROW_DAMAGE);
        enchantmentMap.put("punch", Enchantment.ARROW_KNOCKBACK);
        enchantmentMap.put("flame", Enchantment.ARROW_FIRE);
        enchantmentMap.put("infinity", Enchantment.ARROW_INFINITE);
        enchantmentMap.put("luck_of_the_sea", Enchantment.LUCK);
        enchantmentMap.put("lure", Enchantment.LURE);
        enchantmentMap.put("loyalty", Enchantment.LOYALTY);
        enchantmentMap.put("impaling", Enchantment.IMPALING);
        enchantmentMap.put("riptide", Enchantment.RIPTIDE);
        enchantmentMap.put("channeling", Enchantment.CHANNELING);
        enchantmentMap.put("multishot", Enchantment.MULTISHOT);
        enchantmentMap.put("quick_charge", Enchantment.QUICK_CHARGE);
        enchantmentMap.put("piercing", Enchantment.PIERCING);
        enchantmentMap.put("mending", Enchantment.MENDING);
        enchantmentMap.put("curse_of_vanishing", Enchantment.VANISHING_CURSE);
        enchantmentMap.put("soul_speed", Enchantment.SOUL_SPEED);
        for (Map.Entry<String, Enchantment> entry : enchantmentMap.entrySet()) {
            reverseEnchantmentMap.put(entry.getValue(), entry.getKey());
        }
	}

    private static final Map<String, PotionEffectType> potionEffectMap = new HashMap<>();
    private static final Map<PotionEffectType, String> reversePotionEffectMap = new HashMap<>();
	static {
        potionEffectMap.put("speed", PotionEffectType.SPEED);
        potionEffectMap.put("slowness", PotionEffectType.SLOW);
        potionEffectMap.put("haste", PotionEffectType.FAST_DIGGING);
        potionEffectMap.put("mining_fatigue", PotionEffectType.SLOW_DIGGING);
        potionEffectMap.put("strength", PotionEffectType.INCREASE_DAMAGE);
        potionEffectMap.put("instant_health", PotionEffectType.HEAL);
        potionEffectMap.put("instant_damage", PotionEffectType.HARM);
        potionEffectMap.put("jump_boost", PotionEffectType.JUMP);
        potionEffectMap.put("nausea", PotionEffectType.CONFUSION);
        potionEffectMap.put("regeneration", PotionEffectType.REGENERATION);
        potionEffectMap.put("resistance", PotionEffectType.DAMAGE_RESISTANCE);
        potionEffectMap.put("fire_resistance", PotionEffectType.FIRE_RESISTANCE);
        potionEffectMap.put("water_breathing", PotionEffectType.WATER_BREATHING);
        potionEffectMap.put("invisibility", PotionEffectType.INVISIBILITY);
        potionEffectMap.put("blindness", PotionEffectType.BLINDNESS);
        potionEffectMap.put("night_vision", PotionEffectType.NIGHT_VISION);
        potionEffectMap.put("hunger", PotionEffectType.HUNGER);
        potionEffectMap.put("weakness", PotionEffectType.WEAKNESS);
        potionEffectMap.put("poison", PotionEffectType.POISON);
        potionEffectMap.put("wither", PotionEffectType.WITHER);
        potionEffectMap.put("health_boost", PotionEffectType.HEALTH_BOOST);
        potionEffectMap.put("absorption", PotionEffectType.ABSORPTION);
        potionEffectMap.put("saturation", PotionEffectType.SATURATION);
        potionEffectMap.put("glowing", PotionEffectType.GLOWING);
        potionEffectMap.put("levitation", PotionEffectType.LEVITATION);
        potionEffectMap.put("luck", PotionEffectType.LUCK);
        potionEffectMap.put("bad_luck", PotionEffectType.UNLUCK);
        potionEffectMap.put("slow_falling", PotionEffectType.SLOW_FALLING);
        potionEffectMap.put("conduit_power", PotionEffectType.CONDUIT_POWER);
        potionEffectMap.put("dolphins_grace", PotionEffectType.DOLPHINS_GRACE);
        potionEffectMap.put("bad_omen", PotionEffectType.BAD_OMEN);
        potionEffectMap.put("hero_of_the_village", PotionEffectType.HERO_OF_THE_VILLAGE);
        for (Map.Entry<String, PotionEffectType> entry : potionEffectMap.entrySet()) {
            reversePotionEffectMap.put(entry.getValue(), entry.getKey());
        }
	}
	
	/**
	 * 
	 * @param unformattedText
	 * @return
	 */
	private static String formatText(String unformattedText) {
	    String formattedText = unformattedText.replace("&0", ChatColor.BLACK+"");
        formattedText = formattedText.replace("&1", ChatColor.DARK_BLUE+"");
        formattedText = formattedText.replace("&2", ChatColor.DARK_GREEN+"");
        formattedText = formattedText.replace("&3", ChatColor.DARK_AQUA+"");
        formattedText = formattedText.replace("&4", ChatColor.DARK_RED+"");
        formattedText = formattedText.replace("&5", ChatColor.DARK_PURPLE+"");
        formattedText = formattedText.replace("&6", ChatColor.GOLD+"");
        formattedText = formattedText.replace("&7", ChatColor.GRAY+"");
        formattedText = formattedText.replace("&8", ChatColor.DARK_GRAY+"");
        formattedText = formattedText.replace("&9", ChatColor.BLUE+"");
        formattedText = formattedText.replace("&a", ChatColor.GREEN+"");
        formattedText = formattedText.replace("&b", ChatColor.AQUA+"");
        formattedText = formattedText.replace("&c", ChatColor.RED+"");
        formattedText = formattedText.replace("&d", ChatColor.LIGHT_PURPLE+"");
        formattedText = formattedText.replace("&e", ChatColor.YELLOW+"");
        formattedText = formattedText.replace("&f", ChatColor.WHITE+"");
        formattedText = formattedText.replace("&k", ChatColor.MAGIC+"");
        formattedText = formattedText.replace("&l", ChatColor.BOLD+"");
        formattedText = formattedText.replace("&m", ChatColor.STRIKETHROUGH+"");
        formattedText = formattedText.replace("&n", ChatColor.UNDERLINE+"");
        formattedText = formattedText.replace("&n", ChatColor.ITALIC+"");
        formattedText = formattedText.replace("&n", ChatColor.RESET+"");
	    return formattedText;
	}
	
	private static Map<Material, String> materialItemMap = new HashMap<>();
	static {
        materialItemMap.put(Material.ACACIA_BOAT, "acacia_boat");
        materialItemMap.put(Material.ACACIA_BUTTON, "acacia_button");
	    materialItemMap.put(Material.ACACIA_DOOR, "acacia_door");
	    materialItemMap.put(Material.ACACIA_FENCE, "acacia_fence");
	    materialItemMap.put(Material.ACACIA_FENCE_GATE, "acacia_fence_gate");
	    materialItemMap.put(Material.ACACIA_LEAVES, "acacia_leaves");
	    materialItemMap.put(Material.ACACIA_LOG, "acacia_log");
	    materialItemMap.put(Material.ACACIA_PLANKS, "acacia_planks");
	    materialItemMap.put(Material.ACACIA_PRESSURE_PLATE, "acacia_pressure_plate");
	    materialItemMap.put(Material.ACACIA_SAPLING, "acacia_sapling");
	    materialItemMap.put(Material.ACACIA_SIGN, "acacia_sign");
	    materialItemMap.put(Material.ACACIA_SLAB, "acacia_slab");
	    materialItemMap.put(Material.ACACIA_STAIRS, "acacia_stairs");
	    materialItemMap.put(Material.ACACIA_TRAPDOOR, "acacia_trapdoor");
	    materialItemMap.put(Material.ACACIA_WOOD, "acacia_wood");
	    materialItemMap.put(Material.ACTIVATOR_RAIL, "activator_rail");
	    materialItemMap.put(Material.AIR, "air");
	    materialItemMap.put(Material.ALLIUM, "allium");
	    materialItemMap.put(Material.ANCIENT_DEBRIS, "ancient_debris");
	    materialItemMap.put(Material.ANDESITE, "andesite");
	    materialItemMap.put(Material.ANDESITE_SLAB, "andesite_slab");
	    materialItemMap.put(Material.ANDESITE_STAIRS, "andesite_stairs");
	    materialItemMap.put(Material.ANDESITE_WALL, "andesite_wall");
        materialItemMap.put(Material.ANVIL, "anvil");
        materialItemMap.put(Material.APPLE, "apple");
        materialItemMap.put(Material.ARMOR_STAND, "armor_stand");
        materialItemMap.put(Material.ARROW, "arrow");
	    materialItemMap.put(Material.AZURE_BLUET, "azure_bluet");
        materialItemMap.put(Material.BAKED_POTATO, "baked_potato");
        materialItemMap.put(Material.BAMBOO, "bamboo");
	    materialItemMap.put(Material.BARREL, "barrel");
	    materialItemMap.put(Material.BARRIER, "barrier");
        materialItemMap.put(Material.BASALT, "basalt");
        materialItemMap.put(Material.BAT_SPAWN_EGG, "bat_spawn_egg");
	    materialItemMap.put(Material.BEACON, "beacon");
	    materialItemMap.put(Material.BEDROCK, "bedrock");
        materialItemMap.put(Material.BEE_NEST, "bee_nest");
        materialItemMap.put(Material.BEE_SPAWN_EGG, "bee_spawn_egg");
        materialItemMap.put(Material.BEEF, "beef");
        materialItemMap.put(Material.BEEHIVE, "beehive");
        materialItemMap.put(Material.BEETROOTS, "beetroot");
        materialItemMap.put(Material.BEETROOT_SEEDS, "beetroot_seeds");
        materialItemMap.put(Material.BEETROOT_SOUP, "beetroot_soup");
	    materialItemMap.put(Material.BELL, "bell");
        materialItemMap.put(Material.BIRCH_BOAT, "birch_boat");
        materialItemMap.put(Material.BIRCH_BUTTON, "birch_button");
	    materialItemMap.put(Material.BIRCH_DOOR, "birch_door");
	    materialItemMap.put(Material.BIRCH_FENCE, "birch_fence");
	    materialItemMap.put(Material.BIRCH_FENCE_GATE, "birch_fence_gate");
	    materialItemMap.put(Material.BIRCH_LEAVES, "birch_leaves");
	    materialItemMap.put(Material.BIRCH_LOG, "birch_log");
	    materialItemMap.put(Material.BIRCH_PLANKS, "birch_planks");
	    materialItemMap.put(Material.BIRCH_PRESSURE_PLATE, "birch_pressure_plate");
	    materialItemMap.put(Material.BIRCH_SAPLING, "birch_sapling");
	    materialItemMap.put(Material.BIRCH_SIGN, "birch_sign");
	    materialItemMap.put(Material.BIRCH_SLAB, "birch_slab");
	    materialItemMap.put(Material.BIRCH_STAIRS, "birch_stairs");
	    materialItemMap.put(Material.BIRCH_TRAPDOOR, "birch_trapdoor");
	    materialItemMap.put(Material.BIRCH_WOOD, "birch_wood");
	    materialItemMap.put(Material.BLACKSTONE, "blackstone");
	    materialItemMap.put(Material.BLACKSTONE_SLAB, "blackstone_slab");
	    materialItemMap.put(Material.BLACKSTONE_STAIRS, "blackstone_stairs");
	    materialItemMap.put(Material.BLACKSTONE_WALL, "blackstone_wall");
	    materialItemMap.put(Material.BLACK_BANNER, "black_banner");
	    materialItemMap.put(Material.BLACK_BED, "black_bed");
	    materialItemMap.put(Material.BLACK_CARPET, "black_carpet");
	    materialItemMap.put(Material.BLACK_CONCRETE, "black_concrete");
        materialItemMap.put(Material.BLACK_CONCRETE_POWDER, "black_concrete_powder");
        materialItemMap.put(Material.BLACK_DYE, "black_dye");
	    materialItemMap.put(Material.BLACK_GLAZED_TERRACOTTA, "black_glazed_terracotta");
	    materialItemMap.put(Material.BLACK_SHULKER_BOX, "black_shulker_box");
	    materialItemMap.put(Material.BLACK_STAINED_GLASS, "black_stained_glass");
	    materialItemMap.put(Material.BLACK_STAINED_GLASS_PANE, "black_stained_glass_pane");
	    materialItemMap.put(Material.BLACK_TERRACOTTA, "black_terracotta");
	    materialItemMap.put(Material.BLACK_WOOL, "black_wool");
        materialItemMap.put(Material.BLAST_FURNACE, "blast_furnace");
        materialItemMap.put(Material.BLAZE_POWDER, "blaze_powder");
        materialItemMap.put(Material.BLAZE_ROD, "blaze_rod");
        materialItemMap.put(Material.BLAZE_SPAWN_EGG, "blaze_spawn_egg");
	    materialItemMap.put(Material.BLUE_BANNER, "blue_banner");
	    materialItemMap.put(Material.BLUE_BED, "blue_bed");
	    materialItemMap.put(Material.BLUE_CARPET, "blue_carpet");
	    materialItemMap.put(Material.BLUE_CONCRETE, "blue_concrete");
        materialItemMap.put(Material.BLUE_CONCRETE_POWDER, "blue_concrete_powder");
        materialItemMap.put(Material.BLUE_DYE, "blue_dye");
	    materialItemMap.put(Material.BLUE_GLAZED_TERRACOTTA, "blue_glazed_terracotta");
	    materialItemMap.put(Material.BLUE_ICE, "blue_ice");
	    materialItemMap.put(Material.BLUE_ORCHID, "blue_orchid");
	    materialItemMap.put(Material.BLUE_SHULKER_BOX, "blue_shulker_box");
	    materialItemMap.put(Material.BLUE_STAINED_GLASS, "blue_stained_glass");
	    materialItemMap.put(Material.BLUE_STAINED_GLASS_PANE, "blue_stained_glass_pane");
	    materialItemMap.put(Material.BLUE_TERRACOTTA, "blue_terracotta");
	    materialItemMap.put(Material.BLUE_WOOL, "blue_wool");
        materialItemMap.put(Material.BONE, "bone");
        materialItemMap.put(Material.BONE_BLOCK, "bone_block");
        materialItemMap.put(Material.BONE_MEAL, "bone_meal");
        materialItemMap.put(Material.BOOK, "book");
        materialItemMap.put(Material.BOOKSHELF, "bookshelf");
        materialItemMap.put(Material.BOW, "bow");
        materialItemMap.put(Material.BOWL, "bowl");
	    materialItemMap.put(Material.BRAIN_CORAL, "brain_coral");
	    materialItemMap.put(Material.BRAIN_CORAL_BLOCK, "brain_coral_block");
        materialItemMap.put(Material.BRAIN_CORAL_FAN, "brain_coral_fan");
        materialItemMap.put(Material.BREAD, "bread");
	    materialItemMap.put(Material.BREWING_STAND, "brewing_stand");
        materialItemMap.put(Material.BRICK, "brick");
	    materialItemMap.put(Material.BRICK_SLAB, "brick_slab");
	    materialItemMap.put(Material.BRICK_STAIRS, "brick_stairs");
	    materialItemMap.put(Material.BRICK_WALL, "brick_wall");
        materialItemMap.put(Material.BRICKS, "bricks");
	    materialItemMap.put(Material.BROWN_BANNER, "brown_banner");
	    materialItemMap.put(Material.BROWN_BED, "brown_bed");
	    materialItemMap.put(Material.BROWN_CARPET, "brown_carpet");
	    materialItemMap.put(Material.BROWN_CONCRETE, "brown_concrete");
        materialItemMap.put(Material.BROWN_CONCRETE_POWDER, "brown_concrete_powder");
        materialItemMap.put(Material.BROWN_DYE, "brown_dye");
	    materialItemMap.put(Material.BROWN_GLAZED_TERRACOTTA, "brown_glazed_terracotta");
	    materialItemMap.put(Material.BROWN_MUSHROOM, "brown_mushroom");
	    materialItemMap.put(Material.BROWN_MUSHROOM_BLOCK, "brown_mushroom_block");
	    materialItemMap.put(Material.BROWN_SHULKER_BOX, "brown_shulker_box");
	    materialItemMap.put(Material.BROWN_STAINED_GLASS, "brown_stained_glass");
	    materialItemMap.put(Material.BROWN_STAINED_GLASS_PANE, "brown_stained_glass_pane");
	    materialItemMap.put(Material.BROWN_TERRACOTTA, "brown_terracotta");
	    materialItemMap.put(Material.BROWN_WOOL, "brown_wool");
	    materialItemMap.put(Material.BUBBLE_CORAL, "bubble_coral");
	    materialItemMap.put(Material.BUBBLE_CORAL_BLOCK, "bubble_coral_block");
	    materialItemMap.put(Material.BUBBLE_CORAL_FAN, "bubble_coral_fan");
	    materialItemMap.put(Material.CACTUS, "cactus");
	    materialItemMap.put(Material.CAKE, "cake");
	    materialItemMap.put(Material.CAMPFIRE, "campfire");
        materialItemMap.put(Material.CARROT, "carrot");
        materialItemMap.put(Material.CARROT_ON_A_STICK, "carrot_on_a_stick");
        materialItemMap.put(Material.CARTOGRAPHY_TABLE, "cartography_table");
	    materialItemMap.put(Material.CARVED_PUMPKIN, "carved_pumpkin");
        materialItemMap.put(Material.CAT_SPAWN_EGG, "cat_spawn_egg");
	    materialItemMap.put(Material.CAULDRON, "cauldron");
        materialItemMap.put(Material.CAVE_SPIDER_SPAWN_EGG, "cave_spider_spawn_egg");
	    materialItemMap.put(Material.CHAIN, "chain");
        materialItemMap.put(Material.CHAIN_COMMAND_BLOCK, "chain_command_block");
        materialItemMap.put(Material.CHAINMAIL_BOOTS, "chainmail_boots");
        materialItemMap.put(Material.CHAINMAIL_CHESTPLATE, "chainmail_chestplate");
        materialItemMap.put(Material.CHAINMAIL_HELMET, "chainmail_helmet");
        materialItemMap.put(Material.CHAINMAIL_LEGGINGS, "chainmail_leggings");
        materialItemMap.put(Material.CHARCOAL, "charcoal");
        materialItemMap.put(Material.CHEST, "chest");
        materialItemMap.put(Material.CHEST_MINECART, "chest_minecart");
        materialItemMap.put(Material.CHICKEN, "chicken");
        materialItemMap.put(Material.CHICKEN_SPAWN_EGG, "chicken_spawn_egg");
	    materialItemMap.put(Material.CHIPPED_ANVIL, "chipped_anvil");
	    materialItemMap.put(Material.CHISELED_NETHER_BRICKS, "chiseled_nether_bricks");
	    materialItemMap.put(Material.CHISELED_POLISHED_BLACKSTONE, "chiseled_polished_blackstone");
	    materialItemMap.put(Material.CHISELED_QUARTZ_BLOCK, "chiseled_quartz_block");
	    materialItemMap.put(Material.CHISELED_RED_SANDSTONE, "chiseled_red_sandstone");
	    materialItemMap.put(Material.CHISELED_SANDSTONE, "chiseled_sandstone");
	    materialItemMap.put(Material.CHISELED_STONE_BRICKS, "chiseled_stone_bricks");
	    materialItemMap.put(Material.CHORUS_FLOWER, "chorus_flower");
        materialItemMap.put(Material.CHORUS_FRUIT, "chorus_fruit");
	    materialItemMap.put(Material.CHORUS_PLANT, "chorus_plant");
        materialItemMap.put(Material.CLAY, "clay");
        materialItemMap.put(Material.CLAY_BALL, "clay_ball");
        materialItemMap.put(Material.CLOCK, "clock");
        materialItemMap.put(Material.COAL, "coal");
	    materialItemMap.put(Material.COAL_BLOCK, "coal_block");
	    materialItemMap.put(Material.COAL_ORE, "coal_ore");
	    materialItemMap.put(Material.COARSE_DIRT, "course_dirt");
	    materialItemMap.put(Material.COBBLESTONE, "cobblestone");
	    materialItemMap.put(Material.COBBLESTONE_SLAB, "cobblestone_slab");
	    materialItemMap.put(Material.COBBLESTONE_STAIRS, "cobblestone_stairs");
	    materialItemMap.put(Material.COBBLESTONE_WALL, "cobblestone_wall");
	    materialItemMap.put(Material.COBWEB, "cobweb");
	    materialItemMap.put(Material.COCOA_BEANS, "cocoa_beans");
        materialItemMap.put(Material.COMMAND_BLOCK, "command_block");
        materialItemMap.put(Material.COMMAND_BLOCK_MINECART, "command_block_minecart");
        materialItemMap.put(Material.COMPARATOR, "comparator");
        materialItemMap.put(Material.COMPASS, "compass");
	    materialItemMap.put(Material.COMPOSTER, "composter");
	    materialItemMap.put(Material.CONDUIT, "conduit");
        materialItemMap.put(Material.COOKED_BEEF, "cooked_beef");
        materialItemMap.put(Material.COOKED_CHICKEN, "cooked_chicken");
        materialItemMap.put(Material.COOKED_COD, "cooked_cod");
        materialItemMap.put(Material.COOKED_MUTTON, "cooked_mutton");
        materialItemMap.put(Material.COOKED_PORKCHOP, "cooked_porkchop");
        materialItemMap.put(Material.COOKED_RABBIT, "cooked_rabbit");
        materialItemMap.put(Material.COOKED_SALMON, "cooked_salmon");
        materialItemMap.put(Material.COOKIE, "cookie");
        materialItemMap.put(Material.CORNFLOWER, "cornflower");
        materialItemMap.put(Material.COW_SPAWN_EGG, "cow_spawn_egg");
	    materialItemMap.put(Material.CRACKED_NETHER_BRICKS, "craked_nether_bricks");
	    materialItemMap.put(Material.CRACKED_POLISHED_BLACKSTONE_BRICKS, "cracked_polished_blackstone_bricks");
	    materialItemMap.put(Material.CRACKED_STONE_BRICKS, "cracked_stone_bricks");
	    materialItemMap.put(Material.CRAFTING_TABLE, "crafting_table");
        materialItemMap.put(Material.CREEPER_HEAD, "creeper_head");
        materialItemMap.put(Material.CREEPER_SPAWN_EGG, "creeper_spawn_egg");
	    materialItemMap.put(Material.CRIMSON_BUTTON, "crimson_button");
	    materialItemMap.put(Material.CRIMSON_DOOR, "crimson_door");
	    materialItemMap.put(Material.CRIMSON_FENCE, "crimson_fence");
	    materialItemMap.put(Material.CRIMSON_FENCE_GATE, "crimson_fence_gate");
	    materialItemMap.put(Material.CRIMSON_FUNGUS, "crimson_fungus");
	    materialItemMap.put(Material.CRIMSON_HYPHAE, "crimson_hyphae");
	    materialItemMap.put(Material.CRIMSON_NYLIUM, "crimson_nylium");
	    materialItemMap.put(Material.CRIMSON_PLANKS, "crimson_planks");
	    materialItemMap.put(Material.CRIMSON_PRESSURE_PLATE, "crimson_pressure_plate");
	    materialItemMap.put(Material.CRIMSON_ROOTS, "crimson_roots");
	    materialItemMap.put(Material.CRIMSON_SIGN, "crimson_sign");
	    materialItemMap.put(Material.CRIMSON_SLAB, "crimson_slab");
	    materialItemMap.put(Material.CRIMSON_STAIRS, "crimson_stairs");
	    materialItemMap.put(Material.CRIMSON_STEM, "crimson_stem");
	    materialItemMap.put(Material.CRIMSON_TRAPDOOR, "crimson_trapdoor");
        materialItemMap.put(Material.CROSSBOW, "crossbow");
        materialItemMap.put(Material.CRYING_OBSIDIAN, "crying_obsidian");
	    materialItemMap.put(Material.CUT_RED_SANDSTONE, "cur_red_sandstone");
	    materialItemMap.put(Material.CUT_RED_SANDSTONE_SLAB, "cut_red_sandstone_slab");
	    materialItemMap.put(Material.CUT_SANDSTONE, "cut_sandstone");
	    materialItemMap.put(Material.CUT_SANDSTONE_SLAB, "cut_sandstone_slab");
	    materialItemMap.put(Material.CYAN_BANNER, "cyan_banner");
	    materialItemMap.put(Material.CYAN_BED, "cyan_bed");
	    materialItemMap.put(Material.CYAN_CARPET, "cyan_carpet");
	    materialItemMap.put(Material.CYAN_CONCRETE, "cyan_concrete");
        materialItemMap.put(Material.CYAN_CONCRETE_POWDER, "cyan_concrete_powder");
        materialItemMap.put(Material.CYAN_DYE, "cyan_dye");
	    materialItemMap.put(Material.CYAN_GLAZED_TERRACOTTA, "cyan_glazed_terracotta");
	    materialItemMap.put(Material.CYAN_SHULKER_BOX, "cyan_shulker_box");
	    materialItemMap.put(Material.CYAN_STAINED_GLASS, "cyan_stained_glass");
	    materialItemMap.put(Material.CYAN_STAINED_GLASS_PANE, "cyan_stained_glass_pane");
	    materialItemMap.put(Material.CYAN_TERRACOTTA, "cyan_terracotta");
	    materialItemMap.put(Material.CYAN_WOOL, "cyan_wool");
	    materialItemMap.put(Material.DAMAGED_ANVIL, "damaged_anvil");
	    materialItemMap.put(Material.DANDELION, "dandelion");
        materialItemMap.put(Material.DARK_OAK_BOAT, "dark_oak_boat");
        materialItemMap.put(Material.DARK_OAK_BUTTON, "dark_oak_button");
	    materialItemMap.put(Material.DARK_OAK_DOOR, "dark_oak_door");
	    materialItemMap.put(Material.DARK_OAK_FENCE, "dark_oak_fence");
	    materialItemMap.put(Material.DARK_OAK_FENCE_GATE, "dark_oak_fence_gate");
	    materialItemMap.put(Material.DARK_OAK_LEAVES, "dark_oak_leaves");
	    materialItemMap.put(Material.DARK_OAK_LOG, "dark_oak_log");
	    materialItemMap.put(Material.DARK_OAK_PLANKS, "dark_oak_planks");
	    materialItemMap.put(Material.DARK_OAK_PRESSURE_PLATE, "dark_oak_pressure_plate");
	    materialItemMap.put(Material.DARK_OAK_SAPLING, "dark_oak_sapling");
	    materialItemMap.put(Material.DARK_OAK_SIGN, "dark_oak_sign");
	    materialItemMap.put(Material.DARK_OAK_SLAB, "dark_oak_slab");
	    materialItemMap.put(Material.DARK_OAK_STAIRS, "dark_oak_stairs");
	    materialItemMap.put(Material.DARK_OAK_TRAPDOOR, "dark_oak_trapdoor");
	    materialItemMap.put(Material.DARK_OAK_WOOD, "dark_oak_wood");
	    materialItemMap.put(Material.DARK_PRISMARINE, "dark_prismarine");
	    materialItemMap.put(Material.DARK_PRISMARINE_SLAB, "dark_prismarine_slab");
	    materialItemMap.put(Material.DARK_PRISMARINE_STAIRS, "dark_prismarine_stairs");
	    materialItemMap.put(Material.DAYLIGHT_DETECTOR, "daylight_detector");
	    materialItemMap.put(Material.DEAD_BRAIN_CORAL, "dead_brain_coral");
	    materialItemMap.put(Material.DEAD_BRAIN_CORAL_BLOCK, "dead_brain_coral_block");
	    materialItemMap.put(Material.DEAD_BRAIN_CORAL_FAN, "dead_brain_coral_fan");
	    materialItemMap.put(Material.DEAD_BUBBLE_CORAL, "dead_bubble_coral");
	    materialItemMap.put(Material.DEAD_BUBBLE_CORAL_BLOCK, "dead_bubble_coral_block");
	    materialItemMap.put(Material.DEAD_BUBBLE_CORAL_FAN, "dead_bubble_coral_fan");
	    materialItemMap.put(Material.DEAD_BUSH, "dead_bush");
	    materialItemMap.put(Material.DEAD_FIRE_CORAL, "dead_fire_coral");
	    materialItemMap.put(Material.DEAD_FIRE_CORAL_BLOCK, "dead_fire_coral_block");
	    materialItemMap.put(Material.DEAD_FIRE_CORAL_FAN, "dead_fire_coral_fan");
	    materialItemMap.put(Material.DEAD_HORN_CORAL, "dead_horn_coral");
	    materialItemMap.put(Material.DEAD_HORN_CORAL_BLOCK, "dead_horn_coral_block");
	    materialItemMap.put(Material.DEAD_HORN_CORAL_FAN, "dead_horn_coral_fan");
	    materialItemMap.put(Material.DEAD_TUBE_CORAL, "dead_tube_coral");
	    materialItemMap.put(Material.DEAD_TUBE_CORAL_BLOCK, "dead_tube_coral_block");
	    materialItemMap.put(Material.DEAD_TUBE_CORAL_FAN, "dead_tube_coral_fan");
        materialItemMap.put(Material.DEBUG_STICK, "debug_stick");
        materialItemMap.put(Material.DETECTOR_RAIL, "detector_rail");
        materialItemMap.put(Material.DIAMOND, "diamond");
        materialItemMap.put(Material.DIAMOND_AXE, "diamond_axe");
        materialItemMap.put(Material.DIAMOND_BLOCK, "diamond_block");
        materialItemMap.put(Material.DIAMOND_BOOTS, "diamond_boots");
        materialItemMap.put(Material.DIAMOND_CHESTPLATE, "diamond_chestplate");
        materialItemMap.put(Material.DIAMOND_HELMET, "diamond_helmet");
        materialItemMap.put(Material.DIAMOND_HOE, "diamond_hoe");
        materialItemMap.put(Material.DIAMOND_HORSE_ARMOR, "diamond_horse_armor");
        materialItemMap.put(Material.DIAMOND_LEGGINGS, "diamond_leggings");
        materialItemMap.put(Material.DIAMOND_ORE, "diamond_ore");
        materialItemMap.put(Material.DIAMOND_PICKAXE, "diamond_pickaxe");
        materialItemMap.put(Material.DIAMOND_SHOVEL, "diamond_shovel");
        materialItemMap.put(Material.DIAMOND_SWORD, "diamond_sword");
	    materialItemMap.put(Material.DIORITE, "diorite");
	    materialItemMap.put(Material.DIORITE_SLAB, "diorite_slab");
	    materialItemMap.put(Material.DIORITE_STAIRS, "diorite_stairs");
	    materialItemMap.put(Material.DIORITE_WALL, "diorite_wall");
	    materialItemMap.put(Material.DIRT, "dirt");
	    materialItemMap.put(Material.DISPENSER, "dispenser");
        materialItemMap.put(Material.DOLPHIN_SPAWN_EGG, "dolphin_spawn_egg");
        materialItemMap.put(Material.DONKEY_SPAWN_EGG, "donkey_spawn_egg");
        materialItemMap.put(Material.DRAGON_BREATH, "dragon_breath");
        materialItemMap.put(Material.DRAGON_EGG, "dragon_egg");
	    materialItemMap.put(Material.DRAGON_HEAD, "dragon_head");
        materialItemMap.put(Material.DRIED_KELP, "dried_kelp");
        materialItemMap.put(Material.DRIED_KELP_BLOCK, "dried_kelp_block");
        materialItemMap.put(Material.DROPPER, "dropper");
        materialItemMap.put(Material.DROWNED_SPAWN_EGG, "drowned_spawn_egg");
        materialItemMap.put(Material.EGG, "egg");
        materialItemMap.put(Material.ELDER_GUARDIAN_SPAWN_EGG, "elder_guardian_spawn_egg");
        materialItemMap.put(Material.ELYTRA, "elytra");
        materialItemMap.put(Material.EMERALD, "emerald");
        materialItemMap.put(Material.EMERALD_BLOCK, "emerald_block");
	    materialItemMap.put(Material.EMERALD_ORE, "emerald_ore");
        materialItemMap.put(Material.ENCHANTED_BOOK, "enchanted_book");
        materialItemMap.put(Material.ENCHANTED_GOLDEN_APPLE, "enchanted_golden_apple");
        materialItemMap.put(Material.ENCHANTING_TABLE, "enchanting_table");
        materialItemMap.put(Material.ENDER_CHEST, "ender_chest");
        materialItemMap.put(Material.ENDER_EYE, "ender_eye");
        materialItemMap.put(Material.ENDER_PEARL, "ender_pearl");
        materialItemMap.put(Material.ENDERMAN_SPAWN_EGG, "enderman_spawn_egg");
        materialItemMap.put(Material.ENDERMITE_SPAWN_EGG, "endermite_spawn_egg");
        materialItemMap.put(Material.EVOKER_SPAWN_EGG, "evoker_spawn_egg");
        materialItemMap.put(Material.EXPERIENCE_BOTTLE, "experience_bottle");
        materialItemMap.put(Material.END_CRYSTAL, "end_crystal");
	    materialItemMap.put(Material.END_PORTAL_FRAME, "end_portal_frame");
	    materialItemMap.put(Material.END_ROD, "end_rod");
	    materialItemMap.put(Material.END_STONE, "end_stone");
	    materialItemMap.put(Material.END_STONE_BRICKS, "end_stone_bricks");
	    materialItemMap.put(Material.END_STONE_BRICK_SLAB, "end_stone_brick_slab");
	    materialItemMap.put(Material.END_STONE_BRICK_STAIRS, "end_stone_brick_stairs");
	    materialItemMap.put(Material.END_STONE_BRICK_WALL, "end_stone_brick_wall");
	    materialItemMap.put(Material.FARMLAND, "farmland");
        materialItemMap.put(Material.FEATHER, "feather");
        materialItemMap.put(Material.FERMENTED_SPIDER_EYE, "fermented_spider_eye");
        materialItemMap.put(Material.FERN, "fern");
        materialItemMap.put(Material.FILLED_MAP, "filled_map");
        materialItemMap.put(Material.FIRE_CHARGE, "fire_charge");
	    materialItemMap.put(Material.FIRE_CORAL, "fire_coral");
	    materialItemMap.put(Material.FIRE_CORAL_BLOCK, "fire_coral_block");
        materialItemMap.put(Material.FIRE_CORAL_FAN, "fire_coral_fan");
        materialItemMap.put(Material.FIREWORK_ROCKET, "firework_rocket");
        materialItemMap.put(Material.FIREWORK_STAR, "firework_star");
        materialItemMap.put(Material.FISHING_ROD, "fishing_rod");
        materialItemMap.put(Material.FLETCHING_TABLE, "fletching_table");
        materialItemMap.put(Material.FLINT, "flint");
        materialItemMap.put(Material.FLINT_AND_STEEL, "flint_and_steel");
        materialItemMap.put(Material.FLOWER_BANNER_PATTERN, "flower_banner_pattern");
        materialItemMap.put(Material.FLOWER_POT, "flower_pot");
        materialItemMap.put(Material.FOX_SPAWN_EGG, "fox_spawn_egg");
        materialItemMap.put(Material.FURNACE, "furnace");
        materialItemMap.put(Material.FURNACE_MINECART, "furnace_minecart");
        materialItemMap.put(Material.GHAST_SPAWN_EGG, "ghast_spawn_egg");
        materialItemMap.put(Material.GHAST_TEAR, "ghast_tear");
        materialItemMap.put(Material.GILDED_BLACKSTONE, "gilded_blackstone");
        materialItemMap.put(Material.GLASS, "glass");
        materialItemMap.put(Material.GLASS_BOTTLE, "glass_bottle");
        materialItemMap.put(Material.GLASS_PANE, "glass_pane");
        materialItemMap.put(Material.GLISTERING_MELON_SLICE, "glistering_melon_slice");
        materialItemMap.put(Material.GLOBE_BANNER_PATTERN, "globe_banner_pattern");
        materialItemMap.put(Material.GLOWSTONE, "glowstone");
        materialItemMap.put(Material.GLOWSTONE_DUST, "glowstone_dust");
        materialItemMap.put(Material.GOLD_BLOCK, "gold_block");
        materialItemMap.put(Material.GOLD_INGOT, "gold_ingot");
        materialItemMap.put(Material.GOLD_NUGGET, "gold_nugget");
        materialItemMap.put(Material.GOLD_ORE, "gold_ore");
        materialItemMap.put(Material.GOLD_ORE, "golden_apple");
        materialItemMap.put(Material.GOLD_ORE, "golden_axe");
        materialItemMap.put(Material.GOLD_ORE, "golden_boots");
        materialItemMap.put(Material.GOLD_ORE, "golden_carrot");
        materialItemMap.put(Material.GOLD_ORE, "golden_chestplate");
        materialItemMap.put(Material.GOLD_ORE, "golden_helmet");
        materialItemMap.put(Material.GOLD_ORE, "golden_hoe");
        materialItemMap.put(Material.GOLD_ORE, "golden_horse_armor");
        materialItemMap.put(Material.GOLD_ORE, "golden_leggings");
        materialItemMap.put(Material.GOLD_ORE, "golden_pickaxe");
        materialItemMap.put(Material.GOLD_ORE, "golden_shovel");
        materialItemMap.put(Material.GOLD_ORE, "golden_sword");
	    materialItemMap.put(Material.GRANITE, "granite");
	    materialItemMap.put(Material.GRANITE_SLAB, "granite_slab");
	    materialItemMap.put(Material.GRANITE_STAIRS, "granite_stairs");
	    materialItemMap.put(Material.GRANITE_WALL, "granite_wall");
	    materialItemMap.put(Material.GRASS, "grass");
	    materialItemMap.put(Material.GRASS_BLOCK, "grass_block");
	    materialItemMap.put(Material.GRASS_PATH, "grass_path");
	    materialItemMap.put(Material.GRAVEL, "gravel");
	    materialItemMap.put(Material.GRAY_BANNER, "gray_banner");
	    materialItemMap.put(Material.GRAY_BED, "gray_bed");
	    materialItemMap.put(Material.GRAY_CARPET, "gray_carpet");
	    materialItemMap.put(Material.GRAY_CONCRETE, "gray_concrete");
        materialItemMap.put(Material.GRAY_CONCRETE_POWDER, "gray_concrete_powder");
        materialItemMap.put(Material.GRAY_DYE, "gray_dye");
	    materialItemMap.put(Material.GRAY_GLAZED_TERRACOTTA, "gray_glazed_terracotta");
	    materialItemMap.put(Material.GRAY_SHULKER_BOX, "gray_shulker_box");
	    materialItemMap.put(Material.GRAY_STAINED_GLASS, "gray_stained_glass");
	    materialItemMap.put(Material.GRAY_STAINED_GLASS_PANE, "gray_stained_glass_pane");
	    materialItemMap.put(Material.GRAY_TERRACOTTA, "gray_terracotta");
	    materialItemMap.put(Material.GRAY_WOOL, "gray_wool");
	    materialItemMap.put(Material.GREEN_BANNER, "green_banner");
	    materialItemMap.put(Material.GREEN_BED, "green_bed");
	    materialItemMap.put(Material.GREEN_CARPET, "green_carpet");
	    materialItemMap.put(Material.GREEN_CONCRETE, "green_concrete");
        materialItemMap.put(Material.GREEN_CONCRETE_POWDER, "green_concrete_powder");
        materialItemMap.put(Material.GREEN_DYE, "green_dye");
	    materialItemMap.put(Material.GREEN_GLAZED_TERRACOTTA, "green_glazed_terracotta");
	    materialItemMap.put(Material.GREEN_SHULKER_BOX, "green_shulker_box");
	    materialItemMap.put(Material.GREEN_STAINED_GLASS, "green_stained_glass");
	    materialItemMap.put(Material.GREEN_STAINED_GLASS_PANE, "green_stained_glass_pane");
	    materialItemMap.put(Material.GREEN_TERRACOTTA, "green_terracotta");
	    materialItemMap.put(Material.GREEN_WOOL, "green_wool");
        materialItemMap.put(Material.GRINDSTONE, "grindstone");
        materialItemMap.put(Material.GUARDIAN_SPAWN_EGG, "guardian_spawn_egg");
        materialItemMap.put(Material.GUNPOWDER, "gunpowder");
        materialItemMap.put(Material.HAY_BLOCK, "hay_block");
        materialItemMap.put(Material.HEART_OF_THE_SEA, "heart_of_the_sea");
	    materialItemMap.put(Material.HEAVY_WEIGHTED_PRESSURE_PLATE, "heavy_weighted_pressure_plate");
        materialItemMap.put(Material.HOGLIN_SPAWN_EGG, "hoglin_spawn_egg");
        materialItemMap.put(Material.HONEYCOMB, "honeycomb_block");
        materialItemMap.put(Material.HONEYCOMB_BLOCK, "honeycomb_block");
        materialItemMap.put(Material.HONEY_BLOCK, "honey_block");
        materialItemMap.put(Material.HONEY_BOTTLE, "honey_block");
        materialItemMap.put(Material.HOPPER, "hopper");
        materialItemMap.put(Material.HOPPER_MINECART, "hopper_minecart");
	    materialItemMap.put(Material.HORN_CORAL, "horn_coral");
	    materialItemMap.put(Material.HORN_CORAL_BLOCK, "horn_coral_block");
        materialItemMap.put(Material.HORN_CORAL_FAN, "horn_coral_fan");
        materialItemMap.put(Material.HORSE_SPAWN_EGG, "horse_spawn_egg");
        materialItemMap.put(Material.HUSK_SPAWN_EGG, "husk_spawn_egg");
	    materialItemMap.put(Material.ICE, "ice");
	    materialItemMap.put(Material.INFESTED_CHISELED_STONE_BRICKS, "infested_chiseled_stone_bricks");
	    materialItemMap.put(Material.INFESTED_COBBLESTONE, "infested_cobblestone");
	    materialItemMap.put(Material.INFESTED_CRACKED_STONE_BRICKS, "infested_cracked_stone_bricks");
	    materialItemMap.put(Material.INFESTED_MOSSY_STONE_BRICKS, "infested_mossy_stone_bricks");
	    materialItemMap.put(Material.INFESTED_STONE, "infested_stone");
        materialItemMap.put(Material.INFESTED_STONE_BRICKS, "infested_stone_bricks");
        materialItemMap.put(Material.INK_SAC, "ink_sac");
        materialItemMap.put(Material.IRON_AXE, "iron_axe");
        materialItemMap.put(Material.IRON_BARS, "iron_bars");
        materialItemMap.put(Material.IRON_BLOCK, "iron_block");
        materialItemMap.put(Material.IRON_BOOTS, "iron_boots");
        materialItemMap.put(Material.IRON_CHESTPLATE, "iron_chestplate");
        materialItemMap.put(Material.IRON_DOOR, "iron_door");
        materialItemMap.put(Material.IRON_HELMET, "iron_helmet");
        materialItemMap.put(Material.IRON_HOE, "iron_hoe");
        materialItemMap.put(Material.IRON_HORSE_ARMOR, "iron_horse_armor");
        materialItemMap.put(Material.IRON_INGOT, "iron_ingot");
        materialItemMap.put(Material.IRON_LEGGINGS, "iron_leggings");
        materialItemMap.put(Material.IRON_NUGGET, "iron_nugget");
        materialItemMap.put(Material.IRON_ORE, "iron_ore");
        materialItemMap.put(Material.IRON_PICKAXE, "iron_pickaxe");
        materialItemMap.put(Material.IRON_SHOVEL, "iron_shovel");
        materialItemMap.put(Material.IRON_SWORD, "iron_sword");
        materialItemMap.put(Material.IRON_TRAPDOOR, "iron_trapdoor");
        materialItemMap.put(Material.ITEM_FRAME, "item_frame");
	    materialItemMap.put(Material.JACK_O_LANTERN, "jack_o_lantern");
	    materialItemMap.put(Material.JIGSAW, "jigsaw");
	    materialItemMap.put(Material.JUKEBOX, "jukebox");
        materialItemMap.put(Material.JUNGLE_BOAT, "jungle_boat");
        materialItemMap.put(Material.JUNGLE_BUTTON, "jungle_button");
	    materialItemMap.put(Material.JUNGLE_DOOR, "jungle_door");
	    materialItemMap.put(Material.JUNGLE_FENCE, "jungle_fence");
	    materialItemMap.put(Material.JUNGLE_FENCE_GATE, "jungle_fence_gate");
	    materialItemMap.put(Material.JUNGLE_LEAVES, "jungle_leaves");
	    materialItemMap.put(Material.JUNGLE_LOG, "jungle_log");
	    materialItemMap.put(Material.JUNGLE_PLANKS, "jungle_planks");
	    materialItemMap.put(Material.JUNGLE_PRESSURE_PLATE, "jungle_pressure_plate");
	    materialItemMap.put(Material.JUNGLE_SAPLING, "jungle_sapling");
	    materialItemMap.put(Material.JUNGLE_SIGN, "jungle_sign");
	    materialItemMap.put(Material.JUNGLE_SLAB, "jungle_slab");
	    materialItemMap.put(Material.JUNGLE_STAIRS, "jungle_stairs");
	    materialItemMap.put(Material.JUNGLE_TRAPDOOR, "jungle_trapdoor");
	    materialItemMap.put(Material.JUNGLE_WOOD, "jungle_wood");
        materialItemMap.put(Material.KELP, "kelp");
        materialItemMap.put(Material.KNOWLEDGE_BOOK, "knowledge_book");
	    materialItemMap.put(Material.LADDER, "ladder");
	    materialItemMap.put(Material.LANTERN, "lantern");
        materialItemMap.put(Material.LAPIS_BLOCK, "lapis_block");
        materialItemMap.put(Material.LAPIS_LAZULI, "lapis_lazuli");
	    materialItemMap.put(Material.LAPIS_ORE, "lapis_ore");
	    materialItemMap.put(Material.LARGE_FERN, "large_fern");
	    materialItemMap.put(Material.LAVA_BUCKET, "lava_bucket");
        materialItemMap.put(Material.LEAD, "lead");
        materialItemMap.put(Material.LECTERN, "lectern");
        materialItemMap.put(Material.LEATHER, "leather");
        materialItemMap.put(Material.LEATHER_BOOTS, "leather_boots");
        materialItemMap.put(Material.LEATHER_CHESTPLATE, "leather_chestplate");
        materialItemMap.put(Material.LEATHER_HELMET, "leather_helmet");
        materialItemMap.put(Material.LEATHER_HORSE_ARMOR, "leather_horse_armor");
        materialItemMap.put(Material.LEATHER_LEGGINGS, "leather_leggings");
        materialItemMap.put(Material.LEVER, "lever");
	    materialItemMap.put(Material.LIGHT_BLUE_BANNER, "light_blue_banner");
	    materialItemMap.put(Material.LIGHT_BLUE_BED, "light_blue_bed");
	    materialItemMap.put(Material.LIGHT_BLUE_CARPET, "light_blue_carpet");
	    materialItemMap.put(Material.LIGHT_BLUE_CONCRETE, "light_blue_concrete");
        materialItemMap.put(Material.LIGHT_BLUE_CONCRETE_POWDER, "light_blue_concrete_powder");
        materialItemMap.put(Material.LIGHT_BLUE_DYE, "light_blue_dye");
	    materialItemMap.put(Material.LIGHT_BLUE_GLAZED_TERRACOTTA, "light_blue_glazed_terracotta");
	    materialItemMap.put(Material.LIGHT_BLUE_SHULKER_BOX, "light_blue_shulker_box");
	    materialItemMap.put(Material.LIGHT_BLUE_STAINED_GLASS, "light_blue_stained_glass");
	    materialItemMap.put(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "light_blue_stained_glass_pane");
	    materialItemMap.put(Material.LIGHT_BLUE_TERRACOTTA, "light_blue_terracotta");
	    materialItemMap.put(Material.LIGHT_BLUE_WOOL, "light_blue_wool");
	    materialItemMap.put(Material.LIGHT_GRAY_BANNER, "light_gray_banner");
	    materialItemMap.put(Material.LIGHT_GRAY_BED, "light_gray_bed");
	    materialItemMap.put(Material.LIGHT_GRAY_CARPET, "light_gray_carpet");
	    materialItemMap.put(Material.LIGHT_GRAY_CONCRETE, "light_gray_concrete");
        materialItemMap.put(Material.LIGHT_GRAY_CONCRETE_POWDER, "light_gray_concrete_powder");
        materialItemMap.put(Material.LIGHT_GRAY_DYE, "light_gray_dye");
	    materialItemMap.put(Material.LIGHT_GRAY_GLAZED_TERRACOTTA, "light_gray_glazed_terracotta");
	    materialItemMap.put(Material.LIGHT_GRAY_SHULKER_BOX, "light_gray_shulker_box");
	    materialItemMap.put(Material.LIGHT_GRAY_STAINED_GLASS, "light_gray_stained_glass");
	    materialItemMap.put(Material.LIGHT_GRAY_STAINED_GLASS_PANE, "light_gray_stained_glass_pane");
	    materialItemMap.put(Material.LIGHT_GRAY_TERRACOTTA, "light_gray_terracotta");
	    materialItemMap.put(Material.LIGHT_GRAY_WOOL, "light_gray_wool");
	    materialItemMap.put(Material.LIGHT_WEIGHTED_PRESSURE_PLATE, "light_weighted_pressure_plate");
	    materialItemMap.put(Material.LILAC, "lilac");
	    materialItemMap.put(Material.LILY_OF_THE_VALLEY, "lily_of_the_valley");
	    materialItemMap.put(Material.LILY_PAD, "lily_pad");
	    materialItemMap.put(Material.LIME_BANNER, "lime_banner");
	    materialItemMap.put(Material.LIME_BED, "lime_bed");
	    materialItemMap.put(Material.LIME_CARPET, "lime_carpet");
	    materialItemMap.put(Material.LIME_CONCRETE, "lime_concrete");
        materialItemMap.put(Material.LIME_CONCRETE_POWDER, "lime_concrete_powder");
        materialItemMap.put(Material.LIME_DYE, "lime_dye");
	    materialItemMap.put(Material.LIME_GLAZED_TERRACOTTA, "lime_glazed_terracotta");
	    materialItemMap.put(Material.LIME_SHULKER_BOX, "lime_shulker_box");
	    materialItemMap.put(Material.LIME_STAINED_GLASS, "lime_stained_glass");
	    materialItemMap.put(Material.LIME_STAINED_GLASS_PANE, "lime_stained_glass_pane");
	    materialItemMap.put(Material.LIME_TERRACOTTA, "lime_terracotta");
        materialItemMap.put(Material.LIME_WOOL, "lime_wool");
        materialItemMap.put(Material.LINGERING_POTION, "lingering_potion");
        materialItemMap.put(Material.LLAMA_SPAWN_EGG, "llama_spawn_egg");
	    materialItemMap.put(Material.LODESTONE, "lodestone");
	    materialItemMap.put(Material.LOOM, "loom");
	    materialItemMap.put(Material.MAGENTA_BANNER, "magenta_banner");
	    materialItemMap.put(Material.MAGENTA_BED, "magenta_bed");
	    materialItemMap.put(Material.MAGENTA_CARPET, "magenta_carpet");
	    materialItemMap.put(Material.MAGENTA_CONCRETE, "magenta_concrete");
        materialItemMap.put(Material.MAGENTA_CONCRETE_POWDER, "magenta_concrete_powder");
        materialItemMap.put(Material.MAGENTA_DYE, "magenta_dye");
	    materialItemMap.put(Material.MAGENTA_GLAZED_TERRACOTTA, "magenta_glazed_terracotta");
	    materialItemMap.put(Material.MAGENTA_SHULKER_BOX, "magenta_shulker_box");
	    materialItemMap.put(Material.MAGENTA_STAINED_GLASS, "magenta_stained_glass");
	    materialItemMap.put(Material.MAGENTA_STAINED_GLASS_PANE, "magenta_stained_glass_pane");
	    materialItemMap.put(Material.MAGENTA_TERRACOTTA, "magenta_terracotta");
	    materialItemMap.put(Material.MAGENTA_WOOL, "magenta_wool");
        materialItemMap.put(Material.MAGMA_BLOCK, "magma_block");
        materialItemMap.put(Material.MAGMA_CREAM, "magma_cream");
        materialItemMap.put(Material.MAGMA_CUBE_SPAWN_EGG, "magma_cube_spawn_egg");
        materialItemMap.put(Material.MAP, "map");
        materialItemMap.put(Material.MELON, "melon");
        materialItemMap.put(Material.MELON_SEEDS, "melon_seeds");
        materialItemMap.put(Material.MELON_SLICE, "melon_slice");
        materialItemMap.put(Material.MILK_BUCKET, "milk_buckets");
        materialItemMap.put(Material.MINECART, "minecart");
        materialItemMap.put(Material.MOJANG_BANNER_PATTERN, "mojang_banner_pattern");
        materialItemMap.put(Material.MOOSHROOM_SPAWN_EGG, "mooshroom_spawn_egg");
	    materialItemMap.put(Material.MOSSY_COBBLESTONE, "mossy_cobblestone");
	    materialItemMap.put(Material.MOSSY_COBBLESTONE_SLAB, "mossy_cobblestone_slab");
	    materialItemMap.put(Material.MOSSY_COBBLESTONE_STAIRS, "mossy_cobblestone_stairs");
	    materialItemMap.put(Material.MOSSY_COBBLESTONE_WALL, "mossy_cobblestone_wall");
	    materialItemMap.put(Material.MOSSY_STONE_BRICKS, "mossy_stone_bricks");
	    materialItemMap.put(Material.MOSSY_STONE_BRICK_SLAB, "mossy_stone_brick_slab");
	    materialItemMap.put(Material.MOSSY_STONE_BRICK_STAIRS, "mossy_stone_brick_stairs");
	    materialItemMap.put(Material.MOSSY_STONE_BRICK_WALL, "mossy_stone_brick_wall");
        materialItemMap.put(Material.MULE_SPAWN_EGG, "mule_spawn_egg");
        materialItemMap.put(Material.MUSHROOM_STEM, "mushroom_stem");
        materialItemMap.put(Material.MUSHROOM_STEW, "mushroom_stew");
        materialItemMap.put(Material.MUSIC_DISC_11, "music_disc_11");
        materialItemMap.put(Material.MUSIC_DISC_13, "music_disc_13");
        materialItemMap.put(Material.MUSIC_DISC_BLOCKS, "music_disc_blocks");
        materialItemMap.put(Material.MUSIC_DISC_CAT, "music_disc_cat");
        materialItemMap.put(Material.MUSIC_DISC_CHIRP, "music_disc_chirp");
        materialItemMap.put(Material.MUSIC_DISC_FAR, "music_disc_far");
        materialItemMap.put(Material.MUSIC_DISC_MALL, "music_disc_mall");
        materialItemMap.put(Material.MUSIC_DISC_MELLOHI, "music_disc_mellohi");
        materialItemMap.put(Material.MUSIC_DISC_PIGSTEP, "music_disc_pigstep");
        materialItemMap.put(Material.MUSIC_DISC_STAL, "music_disc_stal");
        materialItemMap.put(Material.MUSIC_DISC_STRAD, "music_disc_strad");
        materialItemMap.put(Material.MUSIC_DISC_WAIT, "music_disc_wait");
        materialItemMap.put(Material.MUSIC_DISC_WARD, "music_disc_ward");
        materialItemMap.put(Material.MUTTON, "mutton");
	    materialItemMap.put(Material.MYCELIUM, "mycelium");
        materialItemMap.put(Material.NAME_TAG, "name_tag");
        materialItemMap.put(Material.NAUTILUS_SHELL, "nautilus_shell");
        materialItemMap.put(Material.NETHERITE_BLOCK, "netherite_block");
	    materialItemMap.put(Material.NETHERRACK, "netherrack");
        materialItemMap.put(Material.NETHER_BRICK, "nether_brick");
        materialItemMap.put(Material.NETHER_BRICKS, "nether_bricks");
	    materialItemMap.put(Material.NETHER_BRICK_FENCE, "nether_brick_fence");
	    materialItemMap.put(Material.NETHER_BRICK_SLAB, "nether_brick_slab");
	    materialItemMap.put(Material.NETHER_BRICK_STAIRS, "nether_brick_stairs");
	    materialItemMap.put(Material.NETHER_BRICK_WALL, "nether_brick_wall");
	    materialItemMap.put(Material.NETHER_GOLD_ORE, "nether_gold_ore");
	    materialItemMap.put(Material.NETHER_QUARTZ_ORE, "nether_quartz_ore");
        materialItemMap.put(Material.NETHER_SPROUTS, "nether_sprouts");
        materialItemMap.put(Material.NETHER_STAR, "nether_star");
	    materialItemMap.put(Material.NETHER_WART, "nether_wart");
        materialItemMap.put(Material.NETHER_WART_BLOCK, "nether_wart_block");
        materialItemMap.put(Material.NETHERITE_AXE, "netherite_axe");
        materialItemMap.put(Material.NETHERITE_BLOCK, "netherite_block");
        materialItemMap.put(Material.NETHERITE_BOOTS, "netherite_boots");
        materialItemMap.put(Material.NETHERITE_CHESTPLATE, "netherite_chestplate");
        materialItemMap.put(Material.NETHERITE_HELMET, "netherite_helmet");
        materialItemMap.put(Material.NETHERITE_HOE, "netherite_hoe");
        materialItemMap.put(Material.NETHERITE_INGOT, "netherite_ingot");
        materialItemMap.put(Material.NETHERITE_LEGGINGS, "netherite_leggings");
        materialItemMap.put(Material.NETHERITE_PICKAXE, "netherite_pickaxe");
        materialItemMap.put(Material.NETHERITE_SCRAP, "netherite_scrap");
        materialItemMap.put(Material.NETHERITE_SHOVEL, "netherite_shovel");
        materialItemMap.put(Material.NETHERITE_SWORD, "netherite_sword");
        materialItemMap.put(Material.NETHERRACK, "netherrack");
	    materialItemMap.put(Material.NOTE_BLOCK, "note_block");
        materialItemMap.put(Material.OAK_BOAT, "oak_boat");
        materialItemMap.put(Material.OAK_BUTTON, "oak_button");
	    materialItemMap.put(Material.OAK_DOOR, "oak_door");
	    materialItemMap.put(Material.OAK_FENCE, "oak_fence");
	    materialItemMap.put(Material.OAK_FENCE_GATE, "oak_fence_gate");
	    materialItemMap.put(Material.OAK_LEAVES, "oak_leaves");
	    materialItemMap.put(Material.OAK_LOG, "oak_log");
	    materialItemMap.put(Material.OAK_PLANKS, "oak_planks");
	    materialItemMap.put(Material.OAK_PRESSURE_PLATE, "oak_pressure_plate");
	    materialItemMap.put(Material.OAK_SAPLING, "oak_sapling");
	    materialItemMap.put(Material.OAK_SIGN, "oak_sign");
	    materialItemMap.put(Material.OAK_SLAB, "oak_slab");
	    materialItemMap.put(Material.OAK_STAIRS, "oak_stairs");
	    materialItemMap.put(Material.OAK_TRAPDOOR, "oak_trapdoor");
	    materialItemMap.put(Material.OAK_WOOD, "oak_wood");
	    materialItemMap.put(Material.OBSERVER, "observer");
	    materialItemMap.put(Material.OBSIDIAN, "obsidian");
	    materialItemMap.put(Material.ORANGE_BANNER, "orange_banner");
	    materialItemMap.put(Material.ORANGE_BED, "orange_bed");
	    materialItemMap.put(Material.ORANGE_CARPET, "orange_carpet");
	    materialItemMap.put(Material.ORANGE_CONCRETE, "orange_concrete");
        materialItemMap.put(Material.ORANGE_CONCRETE_POWDER, "orange_concrete_powder");
        materialItemMap.put(Material.ORANGE_DYE, "orange_dye");
	    materialItemMap.put(Material.ORANGE_GLAZED_TERRACOTTA, "orange_glazed_terracotta");
	    materialItemMap.put(Material.ORANGE_SHULKER_BOX, "orange_shulker_box");
	    materialItemMap.put(Material.ORANGE_STAINED_GLASS, "orange_stained_glass");
	    materialItemMap.put(Material.ORANGE_STAINED_GLASS_PANE, "orange_stained_glass_pane");
	    materialItemMap.put(Material.ORANGE_TERRACOTTA, "orange_terracotta");
	    materialItemMap.put(Material.ORANGE_TULIP, "orange_tulip");
	    materialItemMap.put(Material.ORANGE_WOOL, "orange_wool");
	    materialItemMap.put(Material.OXEYE_DAISY, "oxeye_daisy");
        materialItemMap.put(Material.PACKED_ICE, "packed_ice");
        materialItemMap.put(Material.PAINTING, "painting");
        materialItemMap.put(Material.PANDA_SPAWN_EGG, "panda_spawn_egg");
        materialItemMap.put(Material.PAPER, "paper");
        materialItemMap.put(Material.PARROT_SPAWN_EGG, "parrot_spawn_egg");
	    materialItemMap.put(Material.PEONY, "peony");
        materialItemMap.put(Material.PETRIFIED_OAK_SLAB, "petrified_oak_slab");
        materialItemMap.put(Material.PHANTOM_MEMBRANE, "phantom_membrane");
        materialItemMap.put(Material.PHANTOM_SPAWN_EGG, "phantom_spawn_egg");
        materialItemMap.put(Material.PIG_SPAWN_EGG, "pig_spawn_egg");
        materialItemMap.put(Material.PINK_BANNER, "pink_banner");
        materialItemMap.put(Material.PIGLIN_BANNER_PATTERN, "piglin_banner_pattern");
	    materialItemMap.put(Material.PINK_BED, "pink_bed");
	    materialItemMap.put(Material.PINK_CARPET, "pink_carpet");
	    materialItemMap.put(Material.PINK_CONCRETE, "pink_concrete");
        materialItemMap.put(Material.PINK_CONCRETE_POWDER, "pink_concrete_powder");
        materialItemMap.put(Material.PINK_DYE, "pink_dye");
	    materialItemMap.put(Material.PINK_GLAZED_TERRACOTTA, "pink_glazed_terracotta");
	    materialItemMap.put(Material.PINK_SHULKER_BOX, "pink_shulker_box");
	    materialItemMap.put(Material.PINK_STAINED_GLASS, "pink_stained_glass");
	    materialItemMap.put(Material.PINK_STAINED_GLASS_PANE, "pink_stained_glass_pane");
	    materialItemMap.put(Material.PINK_TERRACOTTA, "pink_terracotta");
	    materialItemMap.put(Material.PINK_TULIP, "pink_tulip");
	    materialItemMap.put(Material.PINK_WOOL, "pink_wool");
	    materialItemMap.put(Material.PISTON, "piston");
	    materialItemMap.put(Material.PLAYER_HEAD, "player_head");
        materialItemMap.put(Material.PODZOL, "podzol");
        materialItemMap.put(Material.POISONOUS_POTATO, "poisonous_potato");
        materialItemMap.put(Material.POLAR_BEAR_SPAWN_EGG, "polar_bear_spawn_egg");
	    materialItemMap.put(Material.POLISHED_ANDESITE, "polished_andesite");
	    materialItemMap.put(Material.POLISHED_ANDESITE_SLAB, "polished_andesite_slab");
	    materialItemMap.put(Material.POLISHED_ANDESITE_STAIRS, "polished_andesite_stairs");
	    materialItemMap.put(Material.POLISHED_BASALT, "polished_basalt");
	    materialItemMap.put(Material.POLISHED_BLACKSTONE, "polished_blackstone");
	    materialItemMap.put(Material.POLISHED_BLACKSTONE_BRICKS, "polished_blackstone_bricks");
	    materialItemMap.put(Material.POLISHED_BLACKSTONE_BRICK_SLAB, "polished_blackstone_brick_slab");
	    materialItemMap.put(Material.POLISHED_BLACKSTONE_BRICK_STAIRS, "polished_blackstone_brick_stairs");
	    materialItemMap.put(Material.POLISHED_BLACKSTONE_BRICK_WALL, "polished_blackstone_brick_wall");
	    materialItemMap.put(Material.POLISHED_BLACKSTONE_BUTTON, "polished_blackstone_button");
	    materialItemMap.put(Material.POLISHED_BLACKSTONE_PRESSURE_PLATE, "polished_blackstone_pressure_plate");
	    materialItemMap.put(Material.POLISHED_BLACKSTONE_SLAB, "polished_blackstone_slab");
	    materialItemMap.put(Material.POLISHED_BLACKSTONE_STAIRS, "polished_blackstone_stairs");
	    materialItemMap.put(Material.POLISHED_BLACKSTONE_WALL, "polished_blackstone_wall");
	    materialItemMap.put(Material.POLISHED_DIORITE, "polished_diorite");
	    materialItemMap.put(Material.POLISHED_DIORITE_SLAB, "polished_diorite_slab");
	    materialItemMap.put(Material.POLISHED_DIORITE_STAIRS, "polished_diorite_stairs");
	    materialItemMap.put(Material.POLISHED_GRANITE, "polished_granite");
	    materialItemMap.put(Material.POLISHED_GRANITE_SLAB, "polished_granite_slab");
	    materialItemMap.put(Material.POLISHED_GRANITE_STAIRS, "polished_granite_stairs");
        materialItemMap.put(Material.POPPED_CHORUS_FRUIT, "popped_chorus_fruit");
        materialItemMap.put(Material.POPPY, "poppy");
        materialItemMap.put(Material.PORKCHOP, "porkchop");
        materialItemMap.put(Material.POTATO, "potato");
        materialItemMap.put(Material.POTION, "potion");
	    materialItemMap.put(Material.POWERED_RAIL, "powered_rail");
	    materialItemMap.put(Material.PRISMARINE, "prismarine");
	    materialItemMap.put(Material.PRISMARINE_BRICKS, "prismarine_bricks");
	    materialItemMap.put(Material.PRISMARINE_BRICK_SLAB, "prismarine_brick_slab");
        materialItemMap.put(Material.PRISMARINE_BRICK_STAIRS, "prismarine_brick_stairs");
        materialItemMap.put(Material.PRISMARINE_CRYSTALS, "prismarine_crystals");
        materialItemMap.put(Material.PRISMARINE_SHARD, "prismarine_shard");
        materialItemMap.put(Material.PRISMARINE_SLAB, "prismarine_slab");
	    materialItemMap.put(Material.PRISMARINE_STAIRS, "prismarine_stairs");
	    materialItemMap.put(Material.PRISMARINE_WALL, "prismarine_wall");
        materialItemMap.put(Material.PUFFERFISH, "pufferfish");
        materialItemMap.put(Material.PUFFERFISH_BUCKET, "pufferfish_bucket");
        materialItemMap.put(Material.PUFFERFISH_SPAWN_EGG, "pufferfish_spawn_egg");
        materialItemMap.put(Material.PUMPKIN, "pumpkin");
        materialItemMap.put(Material.PUMPKIN_PIE, "pumpkin_pie");
        materialItemMap.put(Material.PUMPKIN_SEEDS, "pumpkin_seeds");
	    materialItemMap.put(Material.PURPLE_BANNER, "purple_banner");
	    materialItemMap.put(Material.PURPLE_BED, "purple_bed");
	    materialItemMap.put(Material.PURPLE_CARPET, "purple_carpet");
	    materialItemMap.put(Material.PURPLE_CONCRETE, "purple_concrete");
        materialItemMap.put(Material.PURPLE_CONCRETE_POWDER, "purple_concrete_powder");
        materialItemMap.put(Material.PURPLE_DYE, "purple_dye");
	    materialItemMap.put(Material.PURPLE_GLAZED_TERRACOTTA, "purple_glazed_terracotta");
	    materialItemMap.put(Material.PURPLE_SHULKER_BOX, "purple_shulker_box");
	    materialItemMap.put(Material.PURPLE_STAINED_GLASS, "purple_stained_glass");
	    materialItemMap.put(Material.PURPLE_STAINED_GLASS_PANE, "purple_stained_glass_pane");
	    materialItemMap.put(Material.PURPLE_TERRACOTTA, "purple_terracotta");
	    materialItemMap.put(Material.PURPLE_WOOL, "purple_wool");
	    materialItemMap.put(Material.PURPUR_BLOCK, "purpur_block");
	    materialItemMap.put(Material.PURPUR_PILLAR, "purpur_pillar");
	    materialItemMap.put(Material.PURPUR_SLAB, "purpur_slab");
	    materialItemMap.put(Material.PURPUR_STAIRS, "purpur_stairs");
        materialItemMap.put(Material.QUARTZ, "quartz");
        materialItemMap.put(Material.QUARTZ_BLOCK, "quartz_block");
	    materialItemMap.put(Material.QUARTZ_BRICKS, "quartz_bricks");
	    materialItemMap.put(Material.QUARTZ_PILLAR, "quartz_pillar");
	    materialItemMap.put(Material.QUARTZ_SLAB, "quartz_slab");
	    materialItemMap.put(Material.QUARTZ_STAIRS, "quartz_stairs");
        materialItemMap.put(Material.RABBIT, "rabbit");
        materialItemMap.put(Material.RABBIT_FOOT, "rabbit_foot");
        materialItemMap.put(Material.RABBIT_HIDE, "rabbit_hide");
        materialItemMap.put(Material.RABBIT_SPAWN_EGG, "rabbit_spawn_egg");
        materialItemMap.put(Material.RABBIT_STEW, "rabbit_stew");
        materialItemMap.put(Material.RAIL, "rail");
        materialItemMap.put(Material.RAVAGER_SPAWN_EGG, "ravager_spawn_egg");
        materialItemMap.put(Material.REDSTONE, "redstone");
        materialItemMap.put(Material.REDSTONE_BLOCK, "redstone_block");
	    materialItemMap.put(Material.REDSTONE_LAMP, "redstone_lamp");
	    materialItemMap.put(Material.REDSTONE_ORE, "redstone_ore");
	    materialItemMap.put(Material.REDSTONE_TORCH, "redstone_torch");
	    materialItemMap.put(Material.REDSTONE_WIRE, "redstone_wire");
	    materialItemMap.put(Material.RED_BANNER, "red_banner");
	    materialItemMap.put(Material.RED_BED, "red_bed");
	    materialItemMap.put(Material.RED_CARPET, "red_carpet");
	    materialItemMap.put(Material.RED_CONCRETE, "red_concrete");
        materialItemMap.put(Material.RED_CONCRETE_POWDER, "red_concrete_powder");
        materialItemMap.put(Material.RED_DYE, "red_dye");
	    materialItemMap.put(Material.RED_GLAZED_TERRACOTTA, "red_glazed_terracotta");
	    materialItemMap.put(Material.RED_MUSHROOM, "red_mushroom");
	    materialItemMap.put(Material.RED_MUSHROOM_BLOCK, "red_mushroom_block");
	    materialItemMap.put(Material.RED_NETHER_BRICKS, "red_nether_bricks");
	    materialItemMap.put(Material.RED_NETHER_BRICK_SLAB, "red_nether_brick_slab");
	    materialItemMap.put(Material.RED_NETHER_BRICK_STAIRS, "red_nether_brick_stairs");
	    materialItemMap.put(Material.RED_NETHER_BRICK_WALL, "red_nether_brick_wall");
	    materialItemMap.put(Material.RED_SAND, "red_sand");
	    materialItemMap.put(Material.RED_SANDSTONE, "red_sandstone");
	    materialItemMap.put(Material.RED_SANDSTONE_SLAB, "red_sandstone_slab");
	    materialItemMap.put(Material.RED_SANDSTONE_STAIRS, "red_sandstone_stairs");
	    materialItemMap.put(Material.RED_SANDSTONE_WALL, "red_sandstone_wall");
	    materialItemMap.put(Material.RED_SHULKER_BOX, "red_shulker_box");
	    materialItemMap.put(Material.RED_STAINED_GLASS, "red_stained_glass");
	    materialItemMap.put(Material.RED_STAINED_GLASS_PANE, "red_stained_glass_pane");
	    materialItemMap.put(Material.RED_TERRACOTTA, "red_terracotta");
	    materialItemMap.put(Material.RED_TULIP, "red_tulip");
	    materialItemMap.put(Material.RED_WOOL, "red_wool");
	    materialItemMap.put(Material.REPEATER, "repeater");
	    materialItemMap.put(Material.REPEATING_COMMAND_BLOCK, "repeating_command_block");
	    materialItemMap.put(Material.RESPAWN_ANCHOR, "respawn_anchor");
        materialItemMap.put(Material.ROSE_BUSH, "rose_bush");
        materialItemMap.put(Material.ROTTEN_FLESH, "rotten_flesh");
        materialItemMap.put(Material.SADDLE, "saddle");
        materialItemMap.put(Material.SALMON, "salmon");
        materialItemMap.put(Material.SALMON_BUCKET, "salmon_bucket");
        materialItemMap.put(Material.SALMON_SPAWN_EGG, "salmon_spawn_egg");
        materialItemMap.put(Material.SAND, "sand");
	    materialItemMap.put(Material.SANDSTONE, "sandstone");
	    materialItemMap.put(Material.SANDSTONE_SLAB, "sandstone_slab");
	    materialItemMap.put(Material.SANDSTONE_STAIRS, "sandstone_stairs");
	    materialItemMap.put(Material.SANDSTONE_WALL, "sandstone_wall");
        materialItemMap.put(Material.SCAFFOLDING, "scaffolding");
        materialItemMap.put(Material.SCUTE, "scute");
	    materialItemMap.put(Material.SEAGRASS, "seagrass");
	    materialItemMap.put(Material.SEA_LANTERN, "sea_lantern");
	    materialItemMap.put(Material.SEA_PICKLE, "sea_pickle");
        materialItemMap.put(Material.SHEARS, "shears");
        materialItemMap.put(Material.SHEEP_SPAWN_EGG, "sheep_spawn_egg");
        materialItemMap.put(Material.SHIELD, "shield");
        materialItemMap.put(Material.SHROOMLIGHT, "shroomlight");
        materialItemMap.put(Material.SHULKER_BOX, "shulker_box");
        materialItemMap.put(Material.SHULKER_SHELL, "shulker_shell");
	    materialItemMap.put(Material.SKELETON_SKULL, "skeleton_skull");
        materialItemMap.put(Material.SHULKER_SPAWN_EGG, "shulker_spawn_egg");
        materialItemMap.put(Material.SILVERFISH_SPAWN_EGG, "silverfish_spawn_egg");
        materialItemMap.put(Material.SKELETON_HORSE_SPAWN_EGG, "skeleton_horse_spawn_egg");
        materialItemMap.put(Material.SKELETON_SPAWN_EGG, "skeleton_spawn_egg");
        materialItemMap.put(Material.SKULL_BANNER_PATTERN, "skull_banner_pattern");
        materialItemMap.put(Material.SLIME_BALL, "slime_ball");
        materialItemMap.put(Material.SLIME_BLOCK, "slime_block");
        materialItemMap.put(Material.SLIME_SPAWN_EGG, "slime_spawn_egg");
	    materialItemMap.put(Material.SMITHING_TABLE, "smithing_table");
	    materialItemMap.put(Material.SMOKER, "smoker");
	    materialItemMap.put(Material.SMOOTH_QUARTZ, "smooth_quartz");
	    materialItemMap.put(Material.SMOOTH_QUARTZ_SLAB, "smooth_quartz_slab");
	    materialItemMap.put(Material.SMOOTH_QUARTZ_STAIRS, "smooth_quartz_stairs");
	    materialItemMap.put(Material.SMOOTH_RED_SANDSTONE, "smooth_red_sandstone");
	    materialItemMap.put(Material.SMOOTH_RED_SANDSTONE_SLAB, "smooth_red_sandstone_slab");
	    materialItemMap.put(Material.SMOOTH_RED_SANDSTONE_STAIRS, "smooth_red_sandstone_stairs");
	    materialItemMap.put(Material.SMOOTH_SANDSTONE, "smooth_sandstone");
	    materialItemMap.put(Material.SMOOTH_SANDSTONE_SLAB, "smooth_sandstone_slab");
	    materialItemMap.put(Material.SMOOTH_SANDSTONE_STAIRS, "smooth_sandstone_stairs");
	    materialItemMap.put(Material.SMOOTH_STONE, "smooth_stone");
	    materialItemMap.put(Material.SMOOTH_STONE_SLAB, "smooth_stone_slab");
	    materialItemMap.put(Material.SNOW, "snow");
        materialItemMap.put(Material.SNOW_BLOCK, "snow_block");
        materialItemMap.put(Material.SNOWBALL, "snowball");
	    materialItemMap.put(Material.SOUL_CAMPFIRE, "soul_campfire");
	    materialItemMap.put(Material.SOUL_LANTERN, "soul_lantern");
	    materialItemMap.put(Material.SOUL_SAND, "soul_sand");
	    materialItemMap.put(Material.SOUL_SOIL, "soul_soil");
	    materialItemMap.put(Material.SOUL_TORCH, "soul_torch");
        materialItemMap.put(Material.SPAWNER, "spawner");
        materialItemMap.put(Material.SPECTRAL_ARROW, "spectral_arrow");
        materialItemMap.put(Material.SPIDER_EYE, "spider_eye");
        materialItemMap.put(Material.SPIDER_SPAWN_EGG, "spider_spawn_egg");
        materialItemMap.put(Material.SPLASH_POTION, "splash_potion");
        materialItemMap.put(Material.SPONGE, "sponge");
        materialItemMap.put(Material.SPRUCE_BOAT, "spruce_boat");
        materialItemMap.put(Material.SPRUCE_BUTTON, "spruce_button");
	    materialItemMap.put(Material.SPRUCE_DOOR, "spruce_door");
	    materialItemMap.put(Material.SPRUCE_FENCE, "spruce_fence");
	    materialItemMap.put(Material.SPRUCE_FENCE_GATE, "spruce_fence_gate");
	    materialItemMap.put(Material.SPRUCE_LEAVES, "spruce_leaves");
	    materialItemMap.put(Material.SPRUCE_LOG, "spruce_log");
	    materialItemMap.put(Material.SPRUCE_PLANKS, "spruce_planks");
	    materialItemMap.put(Material.SPRUCE_PRESSURE_PLATE, "spruce_pressure_plate");
	    materialItemMap.put(Material.SPRUCE_SAPLING, "spruce_sapling");
	    materialItemMap.put(Material.SPRUCE_SIGN, "spruce_sign");
	    materialItemMap.put(Material.SPRUCE_SLAB, "spruce_slab");
	    materialItemMap.put(Material.SPRUCE_STAIRS, "spruce_stairs");
	    materialItemMap.put(Material.SPRUCE_TRAPDOOR, "spruce_trapdoor");
	    materialItemMap.put(Material.SPRUCE_WOOD, "spruce_wood");
        materialItemMap.put(Material.SQUID_SPAWN_EGG, "squid_spawn_egg");
        materialItemMap.put(Material.STICK, "stick");
        materialItemMap.put(Material.STICKY_PISTON, "sticky_piston");
        materialItemMap.put(Material.STONE, "stone");
	    materialItemMap.put(Material.STONECUTTER, "stonecutter");
        materialItemMap.put(Material.STONE_AXE, "stone_axe");
	    materialItemMap.put(Material.STONE_BRICKS, "stone_bricks");
	    materialItemMap.put(Material.STONE_BRICK_SLAB, "stone_brick_slab");
	    materialItemMap.put(Material.STONE_BRICK_STAIRS, "stone_brick_stairs");
	    materialItemMap.put(Material.STONE_BRICK_WALL, "stone_brick_wall");
	    materialItemMap.put(Material.STONE_BUTTON, "stone_button");
        materialItemMap.put(Material.STONE_HOE, "stone_hoe");
        materialItemMap.put(Material.STONE_PICKAXE, "stone_pickaxe");
	    materialItemMap.put(Material.STONE_PRESSURE_PLATE, "stone_pressure_plate");
        materialItemMap.put(Material.STONE_SHOVEL, "stone_shovel");
	    materialItemMap.put(Material.STONE_SLAB, "stone_slab");
	    materialItemMap.put(Material.STONE_STAIRS, "stone_stairs");
        materialItemMap.put(Material.STONE_SWORD, "stone_sword");
        materialItemMap.put(Material.STRAY_SPAWN_EGG, "stray_spawn_egg");
        materialItemMap.put(Material.STRIDER_SPAWN_EGG, "strider_spawn_egg");
        materialItemMap.put(Material.STRING, "string");
        materialItemMap.put(Material.STRIPPED_ACACIA_LOG, "stripped_acacia_log");
	    materialItemMap.put(Material.STRIPPED_ACACIA_WOOD, "stripped_acacia_wood");
	    materialItemMap.put(Material.STRIPPED_BIRCH_LOG, "stripped_birch_log");
	    materialItemMap.put(Material.STRIPPED_BIRCH_WOOD, "stripped_birch_wood");
	    materialItemMap.put(Material.STRIPPED_CRIMSON_HYPHAE, "stripped_crimson_hyphae");
	    materialItemMap.put(Material.STRIPPED_CRIMSON_STEM, "stripped_crimson_stem");
	    materialItemMap.put(Material.STRIPPED_DARK_OAK_LOG, "stripped_dark_oak_log");
	    materialItemMap.put(Material.STRIPPED_DARK_OAK_WOOD, "stripped_dark_oak_wood");
	    materialItemMap.put(Material.STRIPPED_JUNGLE_LOG, "stripped_jungle_log");
	    materialItemMap.put(Material.STRIPPED_JUNGLE_WOOD, "stripped_jungle_wood");
	    materialItemMap.put(Material.STRIPPED_OAK_LOG, "stripped_oak_log");
	    materialItemMap.put(Material.STRIPPED_OAK_WOOD, "stripped_oak_wood");
	    materialItemMap.put(Material.STRIPPED_SPRUCE_LOG, "stripped_spruce_log");
	    materialItemMap.put(Material.STRIPPED_SPRUCE_WOOD, "stripped_spruce_wood");
	    materialItemMap.put(Material.STRIPPED_WARPED_HYPHAE, "stripped_warped_hyphae");
	    materialItemMap.put(Material.STRIPPED_WARPED_STEM, "stripped_warped_stem");
	    materialItemMap.put(Material.STRUCTURE_BLOCK, "structure_block");
	    materialItemMap.put(Material.STRUCTURE_VOID, "structure_void");
        materialItemMap.put(Material.SUGAR, "sugar");
        materialItemMap.put(Material.SUGAR_CANE, "sugar_cane");
        materialItemMap.put(Material.SUNFLOWER, "sunflower");
        materialItemMap.put(Material.SUSPICIOUS_STEW, "suspicious_stew");
	    materialItemMap.put(Material.SWEET_BERRIES, "sweet_berries");
	    materialItemMap.put(Material.TALL_GRASS, "tall_grass");
	    materialItemMap.put(Material.TARGET, "target");
        materialItemMap.put(Material.TERRACOTTA, "terracotta");
        materialItemMap.put(Material.TIPPED_ARROW, "tipped_arrow");
        materialItemMap.put(Material.TNT, "tnt");
        materialItemMap.put(Material.TNT_MINECART, "tnt_minecart");
        materialItemMap.put(Material.TORCH, "torch");
        materialItemMap.put(Material.TOTEM_OF_UNDYING, "totem_of_undying");
        materialItemMap.put(Material.TRADER_LLAMA_SPAWN_EGG, "trader_llama_spawn_egg");
	    materialItemMap.put(Material.TRAPPED_CHEST, "trapped_chest");
	    materialItemMap.put(Material.TRIDENT, "trident");
        materialItemMap.put(Material.TRIPWIRE_HOOK, "tripwire_hook");
        materialItemMap.put(Material.TROPICAL_FISH, "tropical_fish");
        materialItemMap.put(Material.TROPICAL_FISH_BUCKET, "tropical_fish_bucket");
        materialItemMap.put(Material.TROPICAL_FISH_SPAWN_EGG, "tropical_fish_spawn_egg");
	    materialItemMap.put(Material.TUBE_CORAL, "tube_coral");
	    materialItemMap.put(Material.TUBE_CORAL_BLOCK, "tube_coral_block");
	    materialItemMap.put(Material.TUBE_CORAL_FAN, "tube_coral_fan");
        materialItemMap.put(Material.TURTLE_EGG, "turtle_egg");
        materialItemMap.put(Material.TURTLE_HELMET, "turtle_helmet");
        materialItemMap.put(Material.TURTLE_SPAWN_EGG, "turtle_spawn_egg");
	    materialItemMap.put(Material.TWISTING_VINES, "twisting_vines");
        materialItemMap.put(Material.VEX_SPAWN_EGG, "vex_spawn_egg");
        materialItemMap.put(Material.VILLAGER_SPAWN_EGG, "villager_spawn_egg");
        materialItemMap.put(Material.VINDICATOR_SPAWN_EGG, "vindicator_spawn_egg");
        materialItemMap.put(Material.VINE, "vine");
        materialItemMap.put(Material.WANDERING_TRADER_SPAWN_EGG, "wandering_trader_spawn_egg");
	    materialItemMap.put(Material.WARPED_BUTTON, "warped_button");
	    materialItemMap.put(Material.WARPED_DOOR, "warped_door");
	    materialItemMap.put(Material.WARPED_FENCE, "warped_fence");
	    materialItemMap.put(Material.WARPED_FENCE_GATE, "warped_fence_gate");
	    materialItemMap.put(Material.WARPED_FUNGUS, "warped_fungus");
	    materialItemMap.put(Material.WARPED_HYPHAE, "warped_hyphae");
	    materialItemMap.put(Material.WARPED_NYLIUM, "warped_nylium");
	    materialItemMap.put(Material.WARPED_PLANKS, "warped_planks");
	    materialItemMap.put(Material.WARPED_PRESSURE_PLATE, "warped_pressure_plate");
	    materialItemMap.put(Material.WARPED_ROOTS, "warped_roots");
	    materialItemMap.put(Material.WARPED_SIGN, "warped_sign");
	    materialItemMap.put(Material.WARPED_SLAB, "warped_slab");
	    materialItemMap.put(Material.WARPED_STAIRS, "warped_stairs");
	    materialItemMap.put(Material.WARPED_STEM, "warped_stem");
	    materialItemMap.put(Material.WARPED_TRAPDOOR, "warped_trapdoor");
	    materialItemMap.put(Material.WARPED_WART_BLOCK, "warped_wart_block");
	    materialItemMap.put(Material.WATER_BUCKET, "water_bucket");
	    materialItemMap.put(Material.WEEPING_VINES, "weeping_vines");
	    materialItemMap.put(Material.WET_SPONGE, "wet_sponge");
        materialItemMap.put(Material.WHEAT, "wheat");
        materialItemMap.put(Material.WHEAT_SEEDS, "wheat_seeds");
	    materialItemMap.put(Material.WHITE_BANNER, "white_banner");
	    materialItemMap.put(Material.WHITE_BED, "white_bed");
	    materialItemMap.put(Material.WHITE_CARPET, "white_carpet");
	    materialItemMap.put(Material.WHITE_CONCRETE, "white_concrete");
        materialItemMap.put(Material.WHITE_CONCRETE_POWDER, "white_concrete_powder");
        materialItemMap.put(Material.WHITE_DYE, "white_dye");
	    materialItemMap.put(Material.WHITE_GLAZED_TERRACOTTA, "white_glazed_terracotta");
	    materialItemMap.put(Material.WHITE_SHULKER_BOX, "white_shulker_box");
	    materialItemMap.put(Material.WHITE_STAINED_GLASS, "white_stained_glass");
	    materialItemMap.put(Material.WHITE_STAINED_GLASS_PANE, "white_stained_glass_pane");
	    materialItemMap.put(Material.WHITE_TERRACOTTA, "white_terracotta");
	    materialItemMap.put(Material.WHITE_TULIP, "white_tulip");
	    materialItemMap.put(Material.WHITE_WOOL, "white_wool");
        materialItemMap.put(Material.WITCH_SPAWN_EGG, "witch_spawn_egg");
        materialItemMap.put(Material.WITHER_ROSE, "wither_rose");
        materialItemMap.put(Material.WITHER_SKELETON_SKULL, "wither_skeleton_skull");
        materialItemMap.put(Material.WITHER_SKELETON_SPAWN_EGG, "wither_skeleton_spawn_egg");
        materialItemMap.put(Material.WOLF_SPAWN_EGG, "wolf_spawn_egg");
        materialItemMap.put(Material.WOODEN_AXE, "wooden_axe");
        materialItemMap.put(Material.WOODEN_HOE, "wooden_hoe");
        materialItemMap.put(Material.WOODEN_PICKAXE, "wooden_pickaxe");
        materialItemMap.put(Material.WOODEN_SHOVEL, "wooden_shovel");
        materialItemMap.put(Material.WOODEN_SWORD, "wooden_sword");
        materialItemMap.put(Material.WRITABLE_BOOK, "writable_book");
        materialItemMap.put(Material.WRITTEN_BOOK, "written_book");
	    materialItemMap.put(Material.YELLOW_BANNER, "yellow_banner");
	    materialItemMap.put(Material.YELLOW_BED, "yellow_bed");
	    materialItemMap.put(Material.YELLOW_CARPET, "yellow_carpet");
	    materialItemMap.put(Material.YELLOW_CONCRETE, "yellow_concrete");
        materialItemMap.put(Material.YELLOW_CONCRETE_POWDER, "yellow_concrete_powder");
        materialItemMap.put(Material.YELLOW_DYE, "yellow_dye");
	    materialItemMap.put(Material.YELLOW_GLAZED_TERRACOTTA, "yellow_glazed_terracotta");
	    materialItemMap.put(Material.YELLOW_SHULKER_BOX, "yellow_shulker_box");
	    materialItemMap.put(Material.YELLOW_STAINED_GLASS, "yellow_stained_glass");
	    materialItemMap.put(Material.YELLOW_STAINED_GLASS_PANE, "yellow_stained_glass_pane");
	    materialItemMap.put(Material.YELLOW_TERRACOTTA, "yellow_terracotta");
	    materialItemMap.put(Material.YELLOW_WOOL, "yellow_wool");
        materialItemMap.put(Material.ZOGLIN_SPAWN_EGG, "zoglin_spawn_egg");
        materialItemMap.put(Material.ZOMBIE_HEAD, "zombie_head");
        materialItemMap.put(Material.ZOMBIE_HORSE_SPAWN_EGG, "zombie_horse_spawn_egg");
        materialItemMap.put(Material.ZOMBIE_SPAWN_EGG, "zombie_spawn_egg");
        materialItemMap.put(Material.ZOMBIE_VILLAGER_SPAWN_EGG, "zombie_villager_spawn_egg");
        materialItemMap.put(Material.ZOMBIFIED_PIGLIN_SPAWN_EGG, "zombified_piglin_spawn_egg");

	}
	
	/**
	 * Takes JSON file of kits and translates it to Java objects
	 * @param kitConfigFile The kits.json config file
	 * @return List of valid kits with all
	 */
	public static Map<String, Kit> importKits(File kitConfigFile) {
		final Map<String, Kit> kits = new HashMap<>();
		try {
			if(!kitConfigFile.exists()) {
			    //TODO print message indicating that the kits file is being initialized
			    //TODO populate the kits file with a default kit list maybe?
				kitConfigFile.getParentFile().mkdirs();
				final FileWriter writer = new FileWriter(kitConfigFile);
				writer.flush();
				writer.close();
				return kits;
			}
			//TODO add input validation on ranges of values
			final FileReader reader = new FileReader(kitConfigFile);
			final JSONArray kitsJSON = (JSONArray) new JSONParser().parse(reader);
			for(int i = 0; i < kitsJSON.size(); i++) {
			    final JSONObject kitJSON = (JSONObject)kitsJSON.get(i);
			    if (!(kitJSON.containsKey(KIT_NAME) || kitJSON.containsKey(KIT_CATEGORY) || kitJSON.containsKey(KIT_INVENTORY))) {
			        //TODO provide a more meaningful error message here
			        continue;
			    }
			    final String kitName = formatText((String)kitJSON.get(KIT_NAME));
			    final String kitCategory = formatText((String)kitJSON.get(KIT_CATEGORY));
			    final JSONArray inventoryJSON = (JSONArray)kitJSON.get(KIT_INVENTORY);
				final Map<String, ItemStack> kitInventory = new HashMap<>();
				for(int j = 0; j < inventoryJSON.size(); j++) {
				    final JSONObject itemJSON = (JSONObject)inventoryJSON.get(j);
				    
				    if (!(itemJSON.containsKey(ITEM_SLOT) || itemJSON.containsKey(ITEM_NAME))) {
				        //TODO notify the console that the item is missing a name or slot
				        continue;
				    }
				    
				    final String itemSlot = (String)itemJSON.get(ITEM_SLOT);
				    final String itemName = (String)itemJSON.get(ITEM_NAME);
                    final Material itemMaterial = Material.matchMaterial(itemName);
                    if (itemMaterial == null) {
                        //TODO notify the console that the item is not a valid minecraft item
                        continue;
                    }
                    
                    final boolean amountSpecified = itemJSON.containsKey(ITEM_AMOUNT);
                    final ItemStack item;
                    //TODO input validation on amount being numeric
                    if (amountSpecified) {
                        final int itemAmount = Math.toIntExact((long)itemJSON.get(ITEM_AMOUNT));
                        item = new ItemStack(itemMaterial, itemAmount);
                    } else {
                        item = new ItemStack(itemMaterial);
                    }
                    
                    final ItemMeta itemMeta = item.getItemMeta();
                    
				    final String itemDisplayName;
				    if (itemJSON.containsKey(ITEM_DISPLAY_NAME)) {
	                    itemDisplayName = formatText((String)itemJSON.get(ITEM_DISPLAY_NAME));
	                    itemMeta.setDisplayName(itemDisplayName);
				    }
                    
				    //TODO input validation on damage being numeric
				    if (itemJSON.containsKey(ITEM_DAMAGE)) {
	                    final int itemDamage = Math.toIntExact((long)itemJSON.get(ITEM_DAMAGE));
	                    if(itemDamage == -1) {
	                        itemMeta.setUnbreakable(true);
	                    } else if (itemMeta instanceof Damageable) {
	                        ((Damageable)itemMeta).setDamage(itemDamage);
	                    }
				    }

				    //TODO input validation on color being numeric
				    //TODO allow for non-numeric colors like "purple", "blue" etc
				    if (itemJSON.containsKey(ITEM_COLOR)) {
				        final int itemColor = Math.toIntExact((long)itemJSON.get(ITEM_COLOR));
                        if(itemMeta instanceof LeatherArmorMeta) {
                            ((LeatherArmorMeta)itemMeta).setColor(Color.fromRGB(itemColor));
                        } else if(itemMeta instanceof PotionMeta) {
                            ((PotionMeta)itemMeta).setColor(Color.fromRGB(itemColor));
                        }
				    }
                    
				    if (itemJSON.containsKey(ITEM_ENCHANTMENTS)) {
				        final JSONArray enchantmentsJSON = (JSONArray)itemJSON.get(ITEM_ENCHANTMENTS);
	                    for(int k = 0; k < enchantmentsJSON.size(); k++) {
	                        final String enchantString = (String)enchantmentsJSON.get(k);
	                        final String[] itemEnchant = enchantString.split("-");
	                        final Enchantment itemEnchantment = enchantmentMap.getOrDefault(itemEnchant[0], null);
	                        if (itemEnchantment == null) {
	                            //TODO add error message
	                            continue;
	                        }
	                        itemMeta.addEnchant(itemEnchantment, Integer.parseInt(itemEnchant[1]), EXCEED_ENCHANTMENT_LEVEL_CAP);
	                    }
				    }
					
					if (itemJSON.containsKey(ITEM_LORE)) {
	                    final JSONArray loreJSON = (JSONArray)itemJSON.get(ITEM_LORE);
	                    final ArrayList<String> itemLore = new ArrayList<String>();
	                    for(int k = 0; k < loreJSON.size(); k++) {
	                        itemLore.add(formatText((String)loreJSON.get(k)));
	                    }
	                    itemMeta.setLore(itemLore);
					}
					
					//TODO add custom effects input validation
					if (itemJSON.containsKey(ITEM_CUSTOM_EFFECTS)) {
    					final JSONArray customEffectsJSON = (JSONArray)itemJSON.get(ITEM_CUSTOM_EFFECTS);
    					for(int k = 0; k < customEffectsJSON.size(); k++) {
    					    final String customEffectString = (String)customEffectsJSON.get(k);
    					    final String[] customEffectSplit = customEffectString.split("-");
    					    final String customEffectTypeString = customEffectSplit[0];
                            final PotionEffectType customEffectType = potionEffectMap.getOrDefault(customEffectTypeString, null);
                            if (customEffectType == null) {
                                //TODO generate a console message logging this
                                continue;
                            }
    					    final int customEffectDuration = Integer.parseInt(customEffectSplit[1]);
    					    final int customEffectAmplifier = Integer.parseInt(customEffectSplit[2]);
    					    
    					    // Generate the item custom effect meta based on which parameters are specified in the JSON
                            final PotionEffect itemCustomEffect;
    					    if (customEffectSplit.length > 3) {
    					        final boolean customEffectAmbient = customEffectSplit[3].equals("1"); // ambient is default false, assume false unless "1" specified to override
    					        if (customEffectSplit.length > 4) {
                                    final boolean customEffectParticle = customEffectSplit[4].equals("0"); // particles is default true, assume true unless "0" specified to override
    					            if (customEffectSplit.length > 5) {
    	                                final boolean customEffectIcon = customEffectSplit[5].equals("0"); // icon is default true, assume true unless "0" specified to override
    					                itemCustomEffect = new PotionEffect(customEffectType, customEffectDuration, customEffectAmplifier, customEffectAmbient, customEffectParticle, customEffectIcon);
    					            } else {
                                        itemCustomEffect = new PotionEffect(customEffectType, customEffectDuration, customEffectAmplifier, customEffectAmbient, customEffectParticle);
    					            }
    					        } else {
                                    itemCustomEffect = new PotionEffect(customEffectType, customEffectDuration, customEffectAmplifier, customEffectAmbient);
    					        }
    					    } else {
                                itemCustomEffect = new PotionEffect(customEffectType, customEffectDuration, customEffectAmplifier);
    					    }
    					    ((PotionMeta) itemMeta).addCustomEffect(itemCustomEffect, OVERWRITE_SAME_EFFECT_TYPE);
    					}
					}
                    item.setItemMeta(itemMeta);
					kitInventory.put(itemSlot,item);
				}
				//Format the kit name here so that it can be indexed with the non-formatted kit name
				final Kit kit = new Kit(kitName, kitCategory, kitInventory);
				kits.put(ChatColor.stripColor(kitName), kit);
			}			
		} catch(IOException e) {
		    //TODO print custom error message with more details
		    e.printStackTrace();
		} catch (ParseException e) {
            //TODO print custom error message with more details
			e.printStackTrace();
		} catch (ArithmeticException e) {
            //TODO print custom error message with more details
		    e.printStackTrace();
		}
		return kits;
	}
	
	/**
	 * Takes Kit.kits and exports them to JSON
	 * @param kitConfigFile The kits.json config file
	 * @param kits Map of all kits with their names
	 */
	public static void exportKits(File kitConfigFile, Map<String, Kit> kits) {
		JSONArray JSONKits = new JSONArray();
		for(Map.Entry<String, Kit> entry : kits.entrySet()) {
			JSONKits.add(getKitJSON(entry.getValue()));
		}
		
		try(FileWriter file = new FileWriter(kitConfigFile)) {
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets proper JSONObject for a given kit
	 * @param kit Kit to convert to JSON
	 * @return JSONObject representation of kit
	 */
	public static JSONObject getKitJSON(Kit kit) {
		JSONObject kitDetails = new JSONObject();
		kitDetails.put(KIT_NAME, kit.getName());
		kitDetails.put(KIT_CATEGORY, kit.getCategory());
		JSONArray inventory = new JSONArray();
		for(Map.Entry<String, ItemStack> entry : kit.getInventory().entrySet()) {
			inventory.add(getItemJSON(entry.getKey(),entry.getValue()));
		}
		kitDetails.put(KIT_INVENTORY, inventory);
		return kitDetails;
	}
	
	/**
	 * Gets proper JSONObject for a given item
	 * @param slot
	 * @param item
	 * @return
	 */
	public static JSONObject getItemJSON(String slot, ItemStack item) {
		JSONObject itemDetails = new JSONObject();
		ItemMeta itemMeta = item.getItemMeta();
		
		itemDetails.put(ITEM_SLOT, slot);
		itemDetails.put(ITEM_NAME, materialItemMap.get(item.getType()));
		itemDetails.put(ITEM_DISPLAY_NAME, itemMeta.getDisplayName());
		
		if(itemMeta.isUnbreakable()) {
			itemDetails.put(ITEM_DAMAGE, -1);
		} else {
			itemDetails.put(ITEM_DAMAGE, ((Damageable)itemMeta).getDamage());
		}
		
		if(itemMeta instanceof LeatherArmorMeta) {
			itemDetails.put(ITEM_COLOR, ((LeatherArmorMeta)itemMeta).getColor());
        } else if(itemMeta instanceof PotionMeta) {
        	itemDetails.put(ITEM_COLOR, ((PotionMeta)itemMeta).getColor());
        }
		
		itemDetails.put(ITEM_AMOUNT, item.getAmount());
		
		JSONArray enchantments = new JSONArray();
		for(Map.Entry<Enchantment, Integer> entry : itemMeta.getEnchants().entrySet()) {
			String enchString = reverseEnchantmentMap.get(entry.getKey()) + "-" + entry.getValue();
			enchantments.add(enchString);
		}
		itemDetails.put(ITEM_ENCHANTMENTS, enchantments);
		
		JSONArray lore = new JSONArray();
		for(String line : itemMeta.getLore()) {
			lore.add(line);
		}
		
		return itemDetails;
	}
	
	/**
	 * Takes JSON file of maps and translates it to Java objects
	 * @return List of valid maps
	 */
	public static MapInfo[] importMaps() {
		throw new UnsupportedOperationException("TODO finish JSONHandler.importMaps()");
	}
}
