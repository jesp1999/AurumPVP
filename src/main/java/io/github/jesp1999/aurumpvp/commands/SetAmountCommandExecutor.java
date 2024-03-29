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

public class SetAmountCommandExecutor implements CommandExecutor {
    private final JavaPlugin plugin;

    public SetAmountCommandExecutor(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        } else {
            if (args.length != 1 || !args[0].chars().allMatch(Character::isDigit)) {
                sender.sendMessage("Incorrect format! Do this instead: /setamount <number> OR /sa <number>");
                return false;
            } else {
                final Player player = (Player) sender;
                final int stackSize = Integer.parseInt(args[0]);
                if (stackSize < 0 || stackSize > 127) {
                    sender.sendMessage("The specified amount is too high!");
                    return false;
                }
                player.getInventory().getItemInMainHand().setAmount(stackSize);
                sender.sendMessage("The amount of the item in the mainhand has been changed to " + args[0] + " successfully!");
                return true;
            }
        }
    }
}
