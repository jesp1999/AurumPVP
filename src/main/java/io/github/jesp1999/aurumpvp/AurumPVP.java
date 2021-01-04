package io.github.jesp1999.aurumpvp;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


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
    
    /**
     * Is run whenever the countdown command is run
     * @param numOfSeconds How long the countdown is
     */
    private void countDown(int numOfSeconds) {  	
    	this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
    		int counter = numOfSeconds;
            public void run() {            	
            	if(counter >= 0) {
    				Bukkit.broadcastMessage(Integer.toString(counter));
    				counter++;
    			}            	
            }
        }, 0L, (long)(20*1.5));
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
    		if(!(args[0].chars().allMatch( Character::isDigit ))) {
    			sender.sendMessage("First argument must be an integer");
    			return false;
    		}
    		countDown(Integer.parseInt(args[0]));    		
    		return true;
    	}
    	return false;
    }
}