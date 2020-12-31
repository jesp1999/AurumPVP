package io.github.jesp1999.aurumpvp;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;


public class AurumPVP extends JavaPlugin {
    @Override
    public void onEnable() {
        // TODO Insert logic to be performed when the plugin is enabled
        getLogger().info("onEnable has been invoked!");
    }
    
    @Override
    public void onDisable() {
        // TODO Insert logic to be performed when the plugin is disabled
        getLogger().info("onDisable has been invoked!");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	if (cmd.getName().equalsIgnoreCase("kit")) { // The kit command gives the player a kit
    		if (!(sender instanceof Player)) {
    			sender.sendMessage("This command can only be run by a player.");
    		} else {
    			Player player = (Player) sender;
    			if(args[0].length() == 0) {
    				player.sendMessage("Incorrect format! Do this instead: /kit <kit name>"); //TODO reserve for alt vulgarity plugin
    				return false;
    			} else {
    				if(args[0].equalsIgnoreCase("ninja")) {
	    				Material ironSwordMaterial = Material.matchMaterial("minecraft:iron_sword");
	    				ItemStack ironSwordItem = new ItemStack(ironSwordMaterial);
	    				ItemMeta ironSwordMeta = ironSwordItem.getItemMeta();
	    				ironSwordMeta.setDisplayName("Ninjato");
	    				List<String> ironSwordLore = new ArrayList<String>();
	    				ironSwordLore.add("Traditional sword of the ninja");
	    				ironSwordMeta.setLore(ironSwordLore);
	    				player.getInventory().addItem(ironSwordItem);
	    				player.sendMessage("You are now a Ninja");
	    			} else {
						player.sendMessage("This kit does not exist!");
	    			}
	    		}
	    		return true;
    		}
    	} else if (cmd.getName().equalsIgnoreCase("basic2")) {
    		if (!(sender instanceof Player)) {
    			sender.sendMessage("This command can only be run by a player.");
    		} else {
    			Player player = (Player) sender;
    			// do something
    		}
    		return true;
    	}
    	return false;
    }
}