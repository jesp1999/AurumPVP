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
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
	}
	
	/**
	 * Takes JSON file of kits and translates it to Java objects
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
			final FileReader reader = new FileReader(kitConfigFile);
			final JSONArray kitsJSON = (JSONArray) new JSONParser().parse(reader);
			for(int i = 0; i < kitsJSON.size(); i++) {
			    final JSONObject kitJSON = (JSONObject)kitsJSON.get(i);
			    if (!(kitJSON.containsKey(KIT_NAME) || kitJSON.containsKey(KIT_CATEGORY) || kitJSON.containsKey(KIT_INVENTORY))) {
			        //TODO provide a more meaningful error message here
			        continue;
			    }
			    final String kitName = (String)kitJSON.get(KIT_NAME);
			    final String kitCategory = (String)kitJSON.get(KIT_CATEGORY);
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
	                    itemDisplayName = (String)itemJSON.get(ITEM_DISPLAY_NAME);
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
                    
				    //TODO add individual enchantment validation
				    if (itemJSON.containsKey(ITEM_ENCHANTMENTS)) {
				        final JSONArray enchantmentsJSON = (JSONArray)itemJSON.get(ITEM_ENCHANTMENTS);
	                    for(int k = 0; k < enchantmentsJSON.size(); k++) {
	                        final String enchantString = (String)enchantmentsJSON.get(k);
	                        final String[] itemEnchant = enchantString.split("-");
	                        itemMeta.addEnchant(enchantmentMap.get(itemEnchant[0]), Integer.parseInt(itemEnchant[1]), EXCEED_ENCHANTMENT_LEVEL_CAP);
	                    }
				    }
					
					if (itemJSON.containsKey(ITEM_LORE)) {
	                    final JSONArray loreJSON = (JSONArray)itemJSON.get(ITEM_LORE);
	                    final ArrayList<String> itemLore = new ArrayList<String>();
	                    for(int k = 0; k < loreJSON.size(); k++) {
	                        itemLore.add((String)loreJSON.get(k));
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
                            final PotionEffectType customEffectType = PotionEffectType.getByName(customEffectTypeString);
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
				final Kit kit = new Kit(kitName, kitCategory, kitInventory);
				kits.put(kitName, kit);
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
	 * Takes JSON file of maps and translates it to Java objects
	 * @return List of valid maps
	 */
	public static MapInfo[] importMaps() {
		throw new UnsupportedOperationException("TODO finish JSONHandler.importMaps()");
	}
}
