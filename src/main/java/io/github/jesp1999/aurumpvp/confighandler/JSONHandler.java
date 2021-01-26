package io.github.jesp1999.aurumpvp.confighandler;

import io.github.jesp1999.aurumpvp.kit.Kit;
import io.github.jesp1999.aurumpvp.map.MapInfo;
import io.github.jesp1999.aurumpvp.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.*;
import org.bukkit.block.Banner;
import org.bukkit.block.BlockState;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.meta.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Used to load from JSON config files
 * @author Ian McDowell
 *
 */
public class JSONHandler extends JSONConstants{
	
	/**
	 * Takes JSON file of kits and translates it to Java objects
	 * @param kitConfigFile The kits.json config file
	 * @return List of valid kits with all
	 */
	public static Map<String, Kit> importKits(Logger logger, File kitConfigFile) {
		final Map<String, Kit> kits = new HashMap<>();
		try {
			if(!kitConfigFile.exists()) {
			    logger.log(Level.WARNING, "Kit config not found! Initializing a new kit config file at " + kitConfigFile);
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
			    if (!kitJSON.containsKey(KIT_NAME)) {
                    logger.log(Level.WARNING, "Encountered kit in config without a valid " + KIT_NAME + ". Skipping this kit..");
                    continue;
			    } else if(!kitJSON.containsKey(KIT_CATEGORY)) {
			        logger.log(Level.WARNING, "Encountered kit named \"" + Utils.formatText((String)kitJSON.get(KIT_NAME)) + "\" without a valid " + KIT_CATEGORY + ". Skipping this kit..");
			        continue;
			    } else if (!kitJSON.containsKey(KIT_INVENTORY)) {
			        logger.log(Level.WARNING, "Encountered kit named \"" + Utils.formatText((String)kitJSON.get(KIT_NAME)) + "\" without a valid " + KIT_INVENTORY + ". Skipping this kit..");
                    continue;
			    }
			    final String kitName = Utils.formatText((String)kitJSON.get(KIT_NAME));
			    final String kitCategory = Utils.formatText((String)kitJSON.get(KIT_CATEGORY));
			    final JSONArray inventoryJSON = (JSONArray)kitJSON.get(KIT_INVENTORY);
				final Map<String, ItemStack> kitInventory = new HashMap<>();
				for(int j = 0; j < inventoryJSON.size(); j++) {
				    final JSONObject itemJSON = (JSONObject)inventoryJSON.get(j);
				    
				    if (!itemJSON.containsKey(ITEM_NAME)) {
				        logger.log(Level.WARNING, "Encountered item with unspecified " + ITEM_NAME + " in kit named \"" + kitName + "\". Skipping this item..");
				        continue;
				    } else if(!itemJSON.containsKey(ITEM_SLOT)) {
                        logger.log(Level.WARNING, "Encountered item named \"" + (String)itemJSON.get(ITEM_NAME) + "\" with unspecified " + ITEM_SLOT + " in kit named \"" + kitName + "\". Skipping this item..");
                        continue;
				    }
				    
				    final String itemSlot = (String)itemJSON.get(ITEM_SLOT);
				    final String itemName = (String)itemJSON.get(ITEM_NAME);
				    
				    if (itemName == null) {
				        logger.log(Level.WARNING, "Encountered null item in kit named \"" + kitName + "\". Skipping this item..");
				        continue;
				    }
                    final Material itemMaterial = Material.matchMaterial(itemName);
                    if (itemMaterial == null) {
                        logger.log(Level.WARNING, "Encountered invalid item name \"" + itemName + "\" in kit named \"" + kitName + "\". Skipping this item..");
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
	                    itemDisplayName = Utils.formatText((String)itemJSON.get(ITEM_DISPLAY_NAME));
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
	                        final Enchantment itemEnchantment = Utils.enchantmentMap.getOrDefault(itemEnchant[0], null);
	                        if (itemEnchantment == null) {
	                            logger.log(Level.WARNING, "Encountered invalid enchantment name in entry \"" + enchantString + "\" of item \"" + itemName + "\" in kit named \"" + kitName + "\". Skipping this enchantment entry..");
	                            continue;
	                        }
	                        itemMeta.addEnchant(itemEnchantment, Integer.parseInt(itemEnchant[1]), EXCEED_ENCHANTMENT_LEVEL_CAP);
	                    }
				    }
					
					if (itemJSON.containsKey(ITEM_LORE)) {
	                    final JSONArray loreJSON = (JSONArray)itemJSON.get(ITEM_LORE);
	                    final ArrayList<String> itemLore = new ArrayList<String>();
	                    for(int k = 0; k < loreJSON.size(); k++) {
	                        itemLore.add(Utils.formatText((String)loreJSON.get(k)));
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
                            final PotionEffectType customEffectType = Utils.potionEffectMap.getOrDefault(customEffectTypeString, null);
                            if (customEffectType == null) {
                                logger.log(Level.WARNING, "Encountered invalid customEffect type in entry \"" + customEffectString + "\" of item \"" + itemName + "\" in kit named \"" + kitName + "\". Skipping this enchantment entry..");
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


					//TODO add banner pattern input validation
					if (itemJSON.containsKey(ITEM_BANNER_ART)) {
						if (itemMeta instanceof BlockStateMeta) {
							final BlockState blockState = ((BlockStateMeta) itemMeta).getBlockState();
							if (blockState instanceof Banner) {
								final JSONArray bannerArtJSON = (JSONArray)itemJSON.get(ITEM_BANNER_ART);
								for(int k = 0; k < bannerArtJSON.size(); k++) {
									final JSONObject bannerPatternJSON = (JSONObject)bannerArtJSON.get(k);
									final DyeColor bannerPatternColor = DyeColor.getByColor(Color.fromRGB((int)bannerPatternJSON.get("pattern")));
									final PatternType bannerPatternType = PatternType.getByIdentifier((String)bannerPatternJSON.get("type"));
									Pattern bannerPattern = new Pattern(bannerPatternColor, bannerPatternType);
									((Banner)blockState).addPattern(bannerPattern);
								}
								((BlockStateMeta)itemMeta).setBlockState(blockState);
							}
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
		    logger.log(Level.SEVERE, "Ran into an IOException when trying to interface with kits.json. Details listed below:");
		    e.printStackTrace();
		} catch (ParseException e) {
            //TODO print custom error message with more details
            logger.log(Level.SEVERE, "Ran into an ParseException when trying to interface with kits.json. Details listed below:");
			e.printStackTrace();
		} catch (ArithmeticException e) {
            //TODO print custom error message with more details
            logger.log(Level.SEVERE, "Ran into an ArithmeticException when trying to interface with kits.json. Details listed below:");
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
		JSONArray kitsJSON = new JSONArray();
		for(Map.Entry<String, Kit> entry : kits.entrySet()) {
			kitsJSON.add(getKitJSON(entry.getValue()));
		}
		try(FileWriter fw = new FileWriter(kitConfigFile)) {
			fw.write(kitsJSON.toJSONString());
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
        Map<Object, Object> kitDetailsMap = new HashMap<>();
		kitDetailsMap.put(KIT_NAME, kit.getName());
		kitDetailsMap.put(KIT_CATEGORY, kit.getCategory());
		JSONArray inventory = new JSONArray();
		for(Map.Entry<String, ItemStack> entry : kit.getInventory().entrySet()) {
		    //TODO get to the bottom of this conditional...
			if (entry.getValue() != null && entry.getValue().getAmount() != 0) {
			    inventory.add(getItemJSON(entry.getKey(),entry.getValue()));
			}
		}
		kitDetailsMap.put(KIT_INVENTORY, inventory);
        JSONObject kitDetails = new JSONObject(kitDetailsMap);
		return kitDetails;
	}
	
	/**
	 * TODO document this
	 * Gets proper JSONObject for a given item
	 * @param slot
	 * @param item
	 * @return
	 */
    public static JSONObject getItemJSON(String slot, ItemStack item) {
		ItemMeta itemMeta = item.getItemMeta();
		if (itemMeta == null) {
		    itemMeta = Bukkit.getItemFactory().getItemMeta(item.getType());
		    item.setItemMeta(itemMeta);
		}
		
		final Map<Object, Object> itemDetailsMap = new HashMap<>();
		
		itemDetailsMap.put(ITEM_SLOT, slot);
		itemDetailsMap.put(ITEM_NAME, Utils.materialItemMap.get(item.getType()));
		if (itemMeta.hasDisplayName()) {
		    itemDetailsMap.put(ITEM_DISPLAY_NAME, Utils.deformatText(itemMeta.getDisplayName()));
		}
		
		if(itemMeta.isUnbreakable()) {
		    itemDetailsMap.put(ITEM_DAMAGE, -1);
		} else {
		    itemDetailsMap.put(ITEM_DAMAGE, ((Damageable)itemMeta).getDamage());
		}
		
		if(itemMeta instanceof LeatherArmorMeta) {
		    itemDetailsMap.put(ITEM_COLOR, ((LeatherArmorMeta)itemMeta).getColor().asRGB());
        } else if(itemMeta instanceof PotionMeta) {
            if(((PotionMeta)itemMeta).hasColor())
                itemDetailsMap.put(ITEM_COLOR, ((PotionMeta)itemMeta).getColor().asRGB());
        }
		
		itemDetailsMap.put(ITEM_AMOUNT, item.getAmount());
		
		if (itemMeta.hasEnchants()) {
    		final JSONArray enchantments = new JSONArray();
    		for(final Map.Entry<Enchantment, Integer> entry : itemMeta.getEnchants().entrySet()) {
    			final String enchString = Utils.reverseEnchantmentMap.get(entry.getKey()) + "-" + entry.getValue();
    			enchantments.add(enchString);
    		}
    		itemDetailsMap.put(ITEM_ENCHANTMENTS, enchantments);
		}
		
		if (itemMeta.hasLore()) {
		    final JSONArray lore = new JSONArray();
    		for(final String line : itemMeta.getLore()) {
    			lore.add(Utils.deformatText(line));
    		}
    		itemDetailsMap.put(ITEM_LORE, lore);
		}
		
		if (itemMeta instanceof PotionMeta) {
	        final JSONArray customEffects = new JSONArray();
		    if (((PotionMeta)itemMeta).hasCustomEffects()) {
		        for (final PotionEffect customEffect : ((PotionMeta) itemMeta).getCustomEffects()) {
		            String customEffectString = "" + Utils.reversePotionEffectMap.get(customEffect .getType()) + "-" + customEffect.getDuration() + "-" + customEffect.getAmplifier() + "-";
		            customEffectString += customEffect.isAmbient() ? 1 : 0;
		            customEffectString += "-" + (customEffect.hasParticles() ? 1 : 0);
		            customEffectString += "-" + (customEffect.hasIcon() ? 1 : 0);
		            customEffects.add(customEffectString);
		        }
		    }
	        itemDetailsMap.put(ITEM_CUSTOM_EFFECTS, customEffects);
		}

		//TODO figure out why this isn't working..
		if (itemMeta instanceof BlockStateMeta) {
			final BlockState blockState = ((BlockStateMeta) itemMeta).getBlockState();
			if (blockState instanceof Banner) {
				final JSONArray bannerArtJSON = new JSONArray();
				for (final Pattern bannerPattern : ((Banner)blockState).getPatterns()) {
					final JSONObject patternJSON = new JSONObject();
					patternJSON.put("color", bannerPattern.getColor().getColor().asRGB());
					patternJSON.put("type", bannerPattern.getPattern().getIdentifier());
					bannerArtJSON.add(patternJSON);
				}
				itemDetailsMap.put(ITEM_BANNER_ART, bannerArtJSON);
			}
		}

        final JSONObject itemDetails = new JSONObject(itemDetailsMap);
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
