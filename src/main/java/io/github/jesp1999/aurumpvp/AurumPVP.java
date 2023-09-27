package io.github.jesp1999.aurumpvp;

import io.github.jesp1999.aurumpvp.commands.*;
import io.github.jesp1999.aurumpvp.confighandler.JSONConstants;
import io.github.jesp1999.aurumpvp.confighandler.JSONHandler;
import io.github.jesp1999.aurumpvp.listeners.DropItemListener;
import io.github.jesp1999.aurumpvp.kit.Kit;
import io.github.jesp1999.aurumpvp.listeners.GameListener;
import io.github.jesp1999.aurumpvp.listeners.PlayerEventListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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
		listeners.add(new DropItemListener(this));
		listeners.add(new GameListener(this));
		listeners.add(new PlayerEventListener(this));
		for (Listener listener : listeners)
			getServer().getPluginManager().registerEvents(listener, this);
        Objects.requireNonNull(this.getCommand("countdown")).setExecutor(new CountdownCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("clearkit")).setExecutor(new ClearKitCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("deletekit")).setExecutor(new DeleteKitCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("givekit")).setExecutor(new GiveKitCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("listkits")).setExecutor(new ListKitsCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("reloadkits")).setExecutor(new ReloadKitsCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("setamount")).setExecutor(new SetAmountCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("setcolor")).setExecutor(new SetColorCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("setlore")).setExecutor(new SetLoreCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("setname")).setExecutor(new SetNameCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("setrestock")).setExecutor(new SetRestockCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("setunbreakable")).setExecutor(new SetUnbreakableCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("setslot")).setExecutor(new SetSlotCommandExecutor(this));
        Objects.requireNonNull(this.getCommand("writekit")).setExecutor(new WriteKitCommandExecutor(this));

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
