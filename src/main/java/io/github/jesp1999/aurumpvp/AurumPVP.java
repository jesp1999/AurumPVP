package io.github.jesp1999.aurumpvp;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.jesp1999.aurumpvp.kit.Kit;


public class AurumPVP extends JavaPlugin {
    @Override
    public void onEnable() {
        // TODO Insert logic to be performed when the plugin is enabled
        getLogger().info("onEnable has been invoked!");
        Kit.initializeKits();
    }
    
    @Override
    public void onDisable() {
        // TODO Insert logic to be performed when the plugin is disabled
        getLogger().info("onDisable has been invoked!");
    }
}