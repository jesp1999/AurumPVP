package io.github.jesp1999.aurumpvp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import org.bukkit.Bukkit;

import io.github.jesp1999.aurumpvp.kit.Kit;
import io.github.jesp1999.aurumpvp.confighandler.JSONConstants;
import io.github.jesp1999.aurumpvp.confighandler.JSONHandler;

public class AurumPVP extends JavaPlugin {

	@Override
	public void onEnable() {
		getLogger().info("onEnable has been invoked!");
		File kitConfigFile = new File(getDataFolder(), JSONConstants.KIT_FILENAME);
		getLogger().info("Attempting to initialize kits from AurumPVP/kits.json ...");
		final boolean kitsInitialized = Kit.initializeKits(kitConfigFile);
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
		
		if(cmd.getName().equalsIgnoreCase("reloadkits") || cmd.getName().equalsIgnoreCase("rk")) {
			File kitConfigFile = new File(getDataFolder(), JSONConstants.KIT_FILENAME);
			getLogger().info("Attempting to reload kits");
			final boolean kitsInitialized = Kit.initializeKits(kitConfigFile);
			if (kitsInitialized) {
			    //TODO note any minor errors which are functionally ignored to prevent client from ripping hair out in finding bugs
			    getLogger().info("Kits reloaded!");
			} else {
			    getLogger().info("Kit reload unsuccessful, see error details above.");
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("kit")) { // The kit command gives the player a kit
			if (!(sender instanceof Player)) {
				sender.sendMessage("This command can only be run by a player.");
			} else {
				final Player player;
				final String kitName;
				if (args[0].equalsIgnoreCase("list")) { // /kit list
					// TODO list the available kits
				} else if (args.length == 1 || args.length == 2) { // /kit [player] <kit name>
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
		
		if (cmd.getName().equalsIgnoreCase("writekit")) { // The writekit command saves a player's inventory as a kit
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
		return false;
	}
}