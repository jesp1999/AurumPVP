package io.github.jesp1999.aurumpvp.confighandler;

import io.github.jesp1999.aurumpvp.kit.Kit;
import io.github.jesp1999.aurumpvp.kit.KitCategory;
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
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Used to load from JSON config files
 * @author Ian McDowell
 *
 */
public class JSONHandler extends JSONConstants{
	
	private static Map<String, Enchantment> enchantmentMap = new HashMap<>(); 
	static {
		enchantmentMap.put("protection", Enchantment.PROTECTION_ENVIRONMENTAL);
		//TODO add all enchantments
	};
	
	/**
	 * Takes JSON file of kits and translates it to Java objects
	 * @return List of valid kits with all
	 */
	public static Map<String, Kit> importKits(File kitConfigFile) {
		Map<String, Kit> kits = new HashMap<>();
		//TODO input verification
		try {
			if(!kitConfigFile.exists()) {
				new FileWriter(kitConfigFile).flush();
				return kits;
			}
			FileReader reader = new FileReader(kitConfigFile);
			JSONArray JSONKits = (JSONArray) new JSONParser().parse(reader);
			for(int i = 0; i < JSONKits.size(); i++) {
				JSONObject JSONKit = (JSONObject)JSONKits.get(i);
				String kitName = (String)JSONKit.get(KIT_NAME);
				KitCategory kitCategory = (KitCategory)JSONKit.get(KIT_CATEGORY);
				JSONArray JSONInventory = (JSONArray)JSONKit.get(KIT_INVENTORY);
				Map<String, ItemStack> kitInventory = new HashMap<>();
				for(int j = 0; j < JSONInventory.size(); j++) {
					JSONObject JSONItem = (JSONObject)JSONInventory.get(j);
					String itemSlot = (String)JSONItem.get(ITEM_SLOT);
					String itemName = (String)JSONItem.get(ITEM_NAME);
					String itemDisplayName = (String)JSONItem.get(ITEM_DISPLAY_NAME);
					int itemDurability = (int)JSONItem.get(ITEM_DURABILITY);
//					int itemColor = (int)JSONItem.get(ITEM_COLOR);
					//TODO add item color functionality
					int itemAmount = (int)JSONItem.get(ITEM_AMOUNT);

					Material itemMaterial = Material.matchMaterial(itemName);
					ItemStack item = new ItemStack(itemMaterial, itemAmount);
					ItemMeta itemMeta = item.getItemMeta();
					itemMeta.setDisplayName(itemDisplayName);
					if(itemDurability == -1) {
						itemMeta.setUnbreakable(true);
					} else {
						//TODO add durability
					}
					
//					ArrayList<String> itemEnchantments = new ArrayList<String>();
					JSONArray JSONEnchantments = (JSONArray)JSONItem.get(ITEM_ENCHANTMENTS);
					for(int k = 0; k < JSONEnchantments.size(); k++) {
						String enchantString = (String)JSONEnchantments.get(k);
						String[] itemEnchant = enchantString.split("-");						
						itemMeta.addEnchant(enchantmentMap.get(itemEnchant[0]), Integer.parseInt(itemEnchant[1]),true);
					}
					
					JSONArray JSONLore = (JSONArray)JSONItem.get(ITEM_LORE);
					ArrayList<String> itemLore = new ArrayList<String>();
					for(int k = 0; k < JSONEnchantments.size(); k++) {
						itemLore.add((String)JSONLore.get(k));
					}
					itemMeta.setLore(itemLore);
					
//					JSONArray JSONCustomEffects = (JSONArray)JSONItem.get(ITEM_CUSTOM_EFFECTS);
//					ArrayList<String> itemCustomEffects = new ArrayList<String>();
//					for(int k = 0; k < JSONCustomEffects.size(); k++) {
//						itemCustomEffects.add((String)JSONCustomEffects.get(k));
//					}					
					kitInventory.put(itemSlot,item);
				}
				Kit kit = new Kit(kitName, kitCategory, kitInventory);
				kits.put(kitName, kit);
			}			
		} catch(IOException | ParseException e) {
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
