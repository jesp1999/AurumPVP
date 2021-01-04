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
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

/**
 * Used to load from JSON config files
 * @author Ian McDowell
 *
 */
public class JSONHandler extends JSONConstants{
	
	private static Map<String, Enchantment> enchantmentMap = new HashMap<>(); 
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
	};
	
	/**
	 * Takes JSON file of kits and translates it to Java objects
	 * @return List of valid kits with all
	 */
	public static Map<String, Kit> importKits(File kitConfigFile) {
		final Map<String, Kit> kits = new HashMap<>();
		//TODO input verification
		try {
			if(!kitConfigFile.exists()) {
				kitConfigFile.getParentFile().mkdirs();
				final FileWriter writer = new FileWriter(kitConfigFile);
				writer.flush();
				writer.close();
				return kits;
			}
			final FileReader reader = new FileReader(kitConfigFile);
			final JSONArray kitsJSON = (JSONArray) new JSONParser().parse(reader);
			for(int i = 0; i < kitsJSON.size(); i++) {
			    final JSONObject kitJSON = (JSONObject)kitsJSON.get(i);
			    final String kitName = (String)kitJSON.get(KIT_NAME);
			    final String kitCategory = (String)kitJSON.get(KIT_CATEGORY);
			    final JSONArray inventoryJSON = (JSONArray)kitJSON.get(KIT_INVENTORY);
				Map<String, ItemStack> kitInventory = new HashMap<>();
				for(int j = 0; j < inventoryJSON.size(); j++) {
				    final JSONObject itemJSON = (JSONObject)inventoryJSON.get(j);
				    
				    final String itemSlot = (String)itemJSON.get(ITEM_SLOT);
				    final String itemName = (String)itemJSON.get(ITEM_NAME);
                    final int itemAmount = Math.toIntExact((long)itemJSON.get(ITEM_AMOUNT));
				    final String itemDisplayName = (String)itemJSON.get(ITEM_DISPLAY_NAME);
                    final Material itemMaterial = Material.matchMaterial(itemName);
                    final ItemStack item = new ItemStack(itemMaterial, itemAmount);
                    final ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.setDisplayName(itemDisplayName);
                    
				    final boolean damageSpecified = itemJSON.containsKey(ITEM_DAMAGE);
				    if (damageSpecified) {
	                    final int itemDamage = Math.toIntExact((long)itemJSON.get(ITEM_DAMAGE));
	                    if(itemDamage == -1) {
	                        itemMeta.setUnbreakable(true);
	                    } else if (itemMeta instanceof Damageable) {
	                        ((Damageable)itemMeta).setDamage(itemDamage);
	                    }
				    }
					final int itemColor = Math.toIntExact((long)itemJSON.get(ITEM_COLOR));

                    if (itemMeta instanceof LeatherArmorMeta)
                        ((LeatherArmorMeta) itemMeta).setColor(Color.fromRGB(itemColor));
					final JSONArray JSONEnchantments = (JSONArray)itemJSON.get(ITEM_ENCHANTMENTS);
					for(int k = 0; k < JSONEnchantments.size(); k++) {
					    final String enchantString = (String)JSONEnchantments.get(k);
					    final String[] itemEnchant = enchantString.split("-");						
						itemMeta.addEnchant(enchantmentMap.get(itemEnchant[0]), Integer.parseInt(itemEnchant[1]), true);
					}
					
					final JSONArray JSONLore = (JSONArray)itemJSON.get(ITEM_LORE);
					final ArrayList<String> itemLore = new ArrayList<String>();
					for(int k = 0; k < JSONLore.size(); k++) {
						itemLore.add((String)JSONLore.get(k));
					}
					itemMeta.setLore(itemLore);
					
//					JSONArray JSONCustomEffects = (JSONArray)JSONItem.get(ITEM_CUSTOM_EFFECTS);
//					ArrayList<String> itemCustomEffects = new ArrayList<String>();
//					for(int k = 0; k < JSONCustomEffects.size(); k++) {
//						itemCustomEffects.add((String)JSONCustomEffects.get(k));
//					}
                    item.setItemMeta(itemMeta);
					kitInventory.put(itemSlot,item);
				}
				final Kit kit = new Kit(kitName, kitCategory, kitInventory);
				kits.put(kitName, kit);
			}			
		} catch(IOException e) {
		    e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (ArithmeticException e) {
		    e.printStackTrace();
		}
		return kits;
	}
	
	/**
	 * Takes JSON file of maps and translates it to Java objects
	 * @return List of valid maps
	 */
	public static MapInfo[] importMaps() {
		throw new UnsupportedOperationException("TODO finish JSONHandler.importMaps()");
	}
}
