package io.github.jesp1999.aurumpvp.commands;

import io.github.jesp1999.aurumpvp.confighandler.JSONConstants;
import io.github.jesp1999.aurumpvp.kit.Kit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ReloadKitsCommandExecutor implements CommandExecutor {
    private final JavaPlugin plugin;

    public ReloadKitsCommandExecutor(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        File kitConfigFile = new File(plugin.getDataFolder(), JSONConstants.KIT_FILENAME);
        plugin.getLogger().info("Attempting to reload kits");
        final boolean kitsInitialized = Kit.initializeKits(plugin.getLogger(), kitConfigFile);
        if (kitsInitialized) {
            //TODO note any minor errors which are functionally ignored to prevent client from ripping hair out in finding bugs
            plugin.getLogger().info("Kits reloaded!");
            return true;
        } else {
            plugin.getLogger().info("Kit reload unsuccessful, see error details above.");
            return false;
        }
    }
}
