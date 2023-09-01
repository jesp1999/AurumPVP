package io.github.jesp1999.aurumpvp.commands;

import io.github.jesp1999.aurumpvp.kit.Kit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class GiveKitCommandExecutor implements CommandExecutor {
    private final JavaPlugin plugin;

    public GiveKitCommandExecutor(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
        } else {
            final Player player;
            final String kitName;
            if (args.length == 1 || args.length == 2) { // /kit [player] <kit name>
                if (args.length == 1) {
                    player = (Player) sender;
                    kitName = args[0].toLowerCase();
                } else {
                    player = sender.getServer().getPlayer(args[0]);
                    kitName = args[1].toLowerCase();
                }
                if (player == null) {
                    sender.sendMessage(
                            "Kit \"" + kitName + "\" could not be equipped for player \"" + args[0] + "\".");
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
        return false;
    }
}