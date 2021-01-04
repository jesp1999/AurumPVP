package io.github.jesp1999.aurumpvp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import io.github.jesp1999.aurumpvp.kit.Kit;
import io.github.jesp1999.aurumpvp.confighandler.JSONConstants;

public class AurumPVP extends JavaPlugin {

	@Override
	public void onEnable() {
		// TODO Insert logic to be performed when the plugin is enabled
		getLogger().info("onEnable has been invoked!");
		File kitConfigFile = new File(getDataFolder(), JSONConstants.KIT_FILENAME);
		Kit.initializeKits(kitConfigFile);
	}

	@Override
	public void onDisable() {
		// TODO Insert logic to be performed when the plugin is disabled
		getLogger().info("onDisable has been invoked!");
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
					counter++;
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
							sender.sendMessage("Successfully equipped the \"" + kitName + "\" kit!");
							return true;
						} else {
							sender.sendMessage("Failed to equip the \"" + kitName + "\" kit!");
							return false;
						}
					} else {
						sender.sendMessage("This kit does not exist!");
						return false;
					}
					// TODO remove this code and put in respective place
					// ItemStack ironSwordItem = new ItemStack(Material.IRON_SWORD);
					// ItemMeta ironSwordMeta = ironSwordItem.getItemMeta();
					// ironSwordMeta.setDisplayName("Ninjato");
					// List<String> ironSwordLore = new ArrayList<String>();
					// ironSwordLore.add("Traditional sword of the ninja");
					// ironSwordMeta.setLore(ironSwordLore);
					// player.getInventory().addItem(ironSwordItem);
					// player.sendMessage("You are now a Ninja");
				} else {
					sender.sendMessage("Incorrect format! Do this instead: /kit [player] <kit name>");
					return false;
				}
			}
		}
		return false;
	}
}