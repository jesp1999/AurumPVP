package io.github.jesp1999.aurumpvp.commands;

import io.github.jesp1999.aurumpvp.confighandler.JSONConstants;
import io.github.jesp1999.aurumpvp.confighandler.JSONHandler;
import io.github.jesp1999.aurumpvp.kit.Kit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WriteKitCommandExecutor implements CommandExecutor {
    private final JavaPlugin plugin;

    public WriteKitCommandExecutor(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        } else {
            if (args.length != 2) {
                sender.sendMessage("Incorrect format! Do this instead: /writekit <kit name> <kit category>");
                return false;
            } else {
                final Player player = (Player) sender;
                final String kitName = args[0];
                final String kitCategory = args[1];
                final List<Listener> listeners = new ArrayList<Listener>();
                final Kit currentKit = new Kit(kitName, kitCategory, player.getInventory(), listeners);
                Kit.kits.put(kitName, currentKit);
                JSONHandler.exportKits(new File(plugin.getDataFolder(), JSONConstants.KIT_FILENAME), Kit.kits);
                final boolean kitsInitialized = Kit.initializeKits(plugin.getLogger(), new File(plugin.getDataFolder(), JSONConstants.KIT_FILENAME));
                // TODO check if kit reinitialization was successful
                sender.sendMessage("The kit \"" + kitName + "\" has been written to the config successfully!");
                return true;
            }
        }
    }
}
