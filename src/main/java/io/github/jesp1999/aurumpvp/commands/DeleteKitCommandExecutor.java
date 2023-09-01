package io.github.jesp1999.aurumpvp.commands;

import io.github.jesp1999.aurumpvp.confighandler.JSONConstants;
import io.github.jesp1999.aurumpvp.confighandler.JSONHandler;
import io.github.jesp1999.aurumpvp.kit.Kit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class DeleteKitCommandExecutor implements CommandExecutor {
    private final JavaPlugin plugin;

    public DeleteKitCommandExecutor(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Incorrect format! Do this instead: /deletekit <kit name> OR /dk <kit name>");
            return false;
        } else {
            final String kitName = args[0];
            if (!Kit.kits.containsKey(kitName)) {
                sender.sendMessage("Kit \"" + kitName + "\" does not currently exist!");
                return false;
            }
            Kit.kits.remove(kitName);
            JSONHandler.exportKits(new File(plugin.getDataFolder(), JSONConstants.KIT_FILENAME), Kit.kits);
            final boolean kitsInitialized = Kit.initializeKits(plugin.getLogger(), new File(plugin.getDataFolder(), JSONConstants.KIT_FILENAME));
            // TODO check if kit reinitialization was successful
            sender.sendMessage("The kit \"" + kitName + "\" has been removed from the config successfully!");
            return true;
        }
    }
}