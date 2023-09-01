package io.github.jesp1999.aurumpvp;

import io.github.jesp1999.aurumpvp.commands.*;
import io.github.jesp1999.aurumpvp.listeners.PassiveEffectListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.Color;

import io.github.jesp1999.aurumpvp.kit.Kit;
import io.github.jesp1999.aurumpvp.utils.Utils;
import io.github.jesp1999.aurumpvp.confighandler.JSONConstants;
import io.github.jesp1999.aurumpvp.confighandler.JSONHandler;
import org.jetbrains.annotations.NotNull;

public class AurumPVP extends JavaPlugin {
    private final List<Listener> listeners = new LinkedList<>();

	@Override
	public void onEnable() {
		getLogger().info("onEnable has been invoked!");
		JSONHandler.setPluginManager(getServer().getPluginManager());
		File kitConfigFile = new File(getDataFolder(), JSONConstants.KIT_FILENAME);
		getLogger().info("Attempting to initialize kits from AurumPVP/kits.json ...");
		final boolean kitsInitialized = Kit.initializeKits(getLogger(), kitConfigFile);
		if (kitsInitialized) {
		    //TODO note any minor errors which are functionally ignored to prevent client from ripping hair out in finding bugs
		    getLogger().info("Kits initialized successfully!");
		} else {
		    getLogger().info("Kit initialization unsuccessful, see error details above.");
		}
        Kit.setPlugin(this);
        Objects.requireNonNull(this.getCommand("countdown")).setExecutor(new CountdownCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("deletekit")).setExecutor(new DeleteKitCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("givekit")).setExecutor(new GiveKitCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("listkits")).setExecutor(new ListKitsCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("reloadkits")).setExecutor(new ReloadKitsCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("setamount")).setExecutor(new SetAmountCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("setcolor")).setExecutor(new SetColorCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("setlore")).setExecutor(new SetLoreCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("setname")).setExecutor(new SetNameCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("setslot")).setExecutor(new SetSlotCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("writekit")).setExecutor(new ReloadKitsCommandExecutor(this));

	}

	public void startListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
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
}
