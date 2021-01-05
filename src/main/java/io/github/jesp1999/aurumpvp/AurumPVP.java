package io.github.jesp1999.aurumpvp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Color;

import io.github.jesp1999.aurumpvp.kit.Kit;
import io.github.jesp1999.aurumpvp.utils.Utils;
import io.github.jesp1999.aurumpvp.confighandler.JSONConstants;
import io.github.jesp1999.aurumpvp.confighandler.JSONHandler;

public class AurumPVP extends JavaPlugin {

	@Override
	public void onEnable() {
		getLogger().info("onEnable has been invoked!");
		File kitConfigFile = new File(getDataFolder(), JSONConstants.KIT_FILENAME);
		getLogger().info("Attempting to initialize kits from AurumPVP/kits.json ...");
		final boolean kitsInitialized = Kit.initializeKits(getLogger(), kitConfigFile);
		if (kitsInitialized) {
		    //TODO note any minor errors which are functionally ignored to prevent client from ripping hair out in finding bugs
		    getLogger().info("Kits initialized successfully!");
		} else {
		    getLogger().info("Kit initialization unsuccessful, see error details above.");
		}
	}

	@Override
	public void onDisable() {
		getLogger().info("onDisable has been invoked!");
		getLogger().info("Attempting to reset in-game kits");
		final boolean kitsReset = Kit.resetKits();
		if (kitsReset) {
		    getLogger().info("Kits reset successfully!");
		} else {
		    getLogger().info("Kit resetting unsuccessful, see error details above.");
		}
	}

	/**
	 * Is run whenever the countdown command is run
	 * 
	 * @param numOfSeconds How long the countdown is
	 */
	private void countDown(int numOfSeconds) {
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			int counter = numOfSeconds;

			public void run() {
				if (counter >= 0) {
					Bukkit.broadcastMessage(Integer.toString(counter));
					counter--;
				}
			}
		}, 0L, 20L);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("countdown")) {
			if (args.length > 1) {
				sender.sendMessage("Too many arguments");
				return false;
			}
			if (args.length < 1) {
				sender.sendMessage("Too few arguments");
				return false;
			}
			if (!(args[0].chars().allMatch(Character::isDigit))) {
				sender.sendMessage("First argument must be an integer");
				return false;
			}
			countDown(Integer.parseInt(args[0]));
			return true;
		}
        
        if (cmd.getName().equalsIgnoreCase("deletekit") || cmd.getName().equalsIgnoreCase("dk")) { // The deletekit command removes a kit from the configuration (and in-game)
            if (args.length != 1) {
                sender.sendMessage("Incorrect format! Do this instad: /deletekit <kit name> OR /dk <kit name>");
                return false;
            } else {
                final String kitName = args[0];
                if (!Kit.kits.containsKey(kitName)) {
                    sender.sendMessage("Kit \"" + kitName + "\" does not currently exist!");
                    return false;
                }
                Kit.kits.remove(kitName);
                JSONHandler.exportKits(new File(getDataFolder(), JSONConstants.KIT_FILENAME), Kit.kits);
                sender.sendMessage("The kit \"" + kitName + "\" has been removed from the config successfully!");
                return true;
            }
        }
        
        if(cmd.getName().equalsIgnoreCase("reloadkits") || cmd.getName().equalsIgnoreCase("rk")) {
            File kitConfigFile = new File(getDataFolder(), JSONConstants.KIT_FILENAME);
            getLogger().info("Attempting to reload kits");
            final boolean kitsInitialized = Kit.initializeKits(getLogger(), kitConfigFile);
            if (kitsInitialized) {
                //TODO note any minor errors which are functionally ignored to prevent client from ripping hair out in finding bugs
                getLogger().info("Kits reloaded!");
            } else {
                getLogger().info("Kit reload unsuccessful, see error details above.");
            }
        }
		
		if (cmd.getName().equalsIgnoreCase("givekit") || cmd.getName().equalsIgnoreCase("gk")) { // The kit command gives the player a kit
			if (!(sender instanceof Player)) {
				sender.sendMessage("This command can only be run by a player.");
			} else {
				final Player player;
				final String kitName;
				if (args.length == 1 || args.length == 2) { // /kit [player] <kit name>
					if (args.length == 1) {
						player = (Player) sender;
						kitName = args[0].toLowerCase();
					} else {
						player = sender.getServer().getPlayer(args[0]);
						kitName = args[1].toLowerCase();
					}
					if (player == null) {
						sender.sendMessage(
								"Kit \"" + kitName + "\" could not be equipped for player \"" + player + "\".");
						return false;
					}
					if (Kit.kits.containsKey(kitName)) {
						boolean equipSuccess = Kit.kits.get(kitName).equipKit(player);
						if (equipSuccess) {
						    final String formattedKitName = Kit.kits.get(kitName).getName();
							sender.sendMessage("Successfully equipped the \"" + formattedKitName + "\" kit!");
							return true;
						} else {
							sender.sendMessage("Failed to equip the \"" + kitName + "\" kit!");
							return false;
						}
					} else {
						sender.sendMessage("This kit does not exist!");
						return false;
					}
				} else {
					sender.sendMessage("Incorrect format! Do this instead: /kit [player] <kit name>");
					return false;
				}
			}
		}
        
        if (cmd.getName().equalsIgnoreCase("writekit") || cmd.getName().equalsIgnoreCase("wk")) { // The writekit command saves a player's inventory as a kit
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be run by a player.");
            } else {
                if (args.length != 2) {
                    sender.sendMessage("Incorrect format! Do this instad: /writekit <kit name> <kit category>");
                    return false;
                } else {
                    final Player player = (Player) sender;
                    final String kitName = args[0];
                    final String kitCategory = args[1];
                    final Kit currentKit = new Kit(kitName, kitCategory, player.getInventory());
                    Kit.kits.put(kitName, currentKit);
                    JSONHandler.exportKits(new File(getDataFolder(), JSONConstants.KIT_FILENAME), Kit.kits);
                    sender.sendMessage("The kit \"" + kitName + "\" has been written to the config successfully!");
                    return true;
                }
            }
        }
        
        if (cmd.getName().equalsIgnoreCase("moveslot") || cmd.getName().equalsIgnoreCase("ms")) { // The moveslot command moves the mainhand item into the specified slot
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be run by a player.");
            } else {
                if (args.length != 1) {
                    sender.sendMessage("Incorrect format! Do this instad: /moveslot <slot name> OR /ms <slot name>");
                    return false;
                } else {
                    final Player player = (Player) sender;
                    final String slotName = args[0];
                    final PlayerInventory inventory = player.getInventory();
                    if (Utils.inventorySlots.containsKey(slotName)) {
                        inventory.setItem(Utils.inventorySlots.get(slotName), inventory.getItemInMainHand());
                    } else if (slotName.equals("armor.head")) {
                        inventory.setHelmet(inventory.getItemInMainHand());
                    } else if (slotName.equals("armor.chest")) {
                        inventory.setChestplate(inventory.getItemInMainHand());
                    } else if (slotName.equals("armor.legs")) {
                        inventory.setLeggings(inventory.getItemInMainHand());
                    } else if (slotName.equals("armor.feet")) {
                        inventory.setBoots(inventory.getItemInMainHand());
                    } else if (slotName.equals("weapon.offhand")) {
                        inventory.setItemInOffHand(inventory.getItemInMainHand());
                    } else {
                        //TODO maybe suggest a format to fix any small syntax errors?
                        sender.sendMessage("The specified slot does not exist!");
                        return false;
                    }
                    inventory.setItemInMainHand(null);
                    sender.sendMessage("The main hand item has been moved to the " + slotName + " slot successfully!");
                    return true;
                }
            }
        }
        
        if (cmd.getName().equalsIgnoreCase("setcolor") || cmd.getName().equalsIgnoreCase("sc")) { // The setcolor command changes the color of the mainhand item
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be run by a player.");
            } else {
                if (args.length != 1 || !args[0].chars().allMatch(c -> (Character.digit(c, 16) != -1))) {
                    sender.sendMessage("Incorrect format! Do this instad: /setcolor <hex color> OR /sc <hex color>");
                    return false;
                } else {
                    final Player player = (Player) sender;
                    final Color color = Color.fromRGB(Integer.parseInt(args[0], 16));
                    final ItemStack item = player.getInventory().getItemInMainHand();
                    final ItemMeta itemMeta = item.getItemMeta();
                    if (itemMeta instanceof LeatherArmorMeta) {
                        ((LeatherArmorMeta)itemMeta).setColor(color);
                    } else if (itemMeta instanceof PotionMeta) {
                        ((PotionMeta)itemMeta).setColor(color);
                    } else {
                        //TODO maybe suggest a format to fix any small syntax errors?
                        sender.sendMessage("The specified item doesn't have a color!");
                        return false;
                    }
                    item.setItemMeta(itemMeta);
                    sender.sendMessage("The color of the item in the mainhand has been changed to " + args[0] + " successfully!");
                    return true;
                }
            }
        }
        
        if (cmd.getName().equalsIgnoreCase("stacksize") || cmd.getName().equalsIgnoreCase("ss")) { // The stacksize command changes the stack size of the mainhand item
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be run by a player.");
            } else {
                if (args.length != 1 || !args[0].chars().allMatch(Character::isDigit)) {
                    sender.sendMessage("Incorrect format! Do this instad: /stacksize <number> OR /ss <number>");
                    return false;
                } else {
                    final Player player = (Player) sender;
                    final int stackSize = Integer.parseInt(args[0]);
                    if (stackSize < 0 || stackSize > 127) {
                        sender.sendMessage("The specified stack size is too high!");
                        return false;
                    }
                    player.getInventory().getItemInMainHand().setAmount(stackSize);
                    sender.sendMessage("The stack size of the item in the mainhand has been changed to " + args[0] + " successfully!");
                    return true;
                }
            }
        }
		
		if (cmd.getName().equalsIgnoreCase("listkits") || cmd.getName().equalsIgnoreCase("lk")) { // The listkits command lists the currently loaded kits
            if (args.length > 0) {
                sender.sendMessage("Incorrect format! Do this instad: /listkits");
                return false;
            } else {
                final Set<String> kitNames = new HashSet<>();
                for (Kit kit : Kit.kits.values()) {
                    kitNames.add(kit.getName());
                }
                sender.sendMessage("Kits: " + kitNames);
                return true;
            }
        }
		return false;
	}
}