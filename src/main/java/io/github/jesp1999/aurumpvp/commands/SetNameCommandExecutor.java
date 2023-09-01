package io.github.jesp1999.aurumpvp.commands;

import io.github.jesp1999.aurumpvp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SetNameCommandExecutor implements CommandExecutor {
    private final JavaPlugin plugin;

    public SetNameCommandExecutor(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        } else {
            if (args.length != 1) {
                sender.sendMessage("Incorrect format! Do this instead: /setname <name> OR /sn <name>");
                return false;
            } else {
                final Player player = (Player) sender;
                final ItemStack item = player.getInventory().getItemInMainHand();
                ItemMeta itemMeta = item.getItemMeta();
                if (itemMeta == null) {
                    itemMeta = Bukkit.getItemFactory().getItemMeta(item.getType());
                }
                final String newName = Utils.formatText(args[0]);
                Objects.requireNonNull(itemMeta).setDisplayName(newName);
                item.setItemMeta(itemMeta);
                sender.sendMessage("The name of the item in the mainhand has been changed to \"" + newName + "\" successfully!");
                return true;
            }
        }
    }
}
