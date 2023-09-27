package io.github.jesp1999.aurumpvp.commands;

import io.github.jesp1999.aurumpvp.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class SetUnbreakableCommandExecutor implements CommandExecutor {
    private final JavaPlugin plugin;

    public SetUnbreakableCommandExecutor(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        } else {
            if (args.length > 1) {
                sender.sendMessage("Incorrect format! Do this instead: /setunbreakable [value] OR /su [value]");
                return false;
            } else {
                final Player player = (Player) sender;
                final boolean unbreakable = args.length == 0 || Boolean.parseBoolean(args[0]);
                final PlayerInventory inventory = player.getInventory();
                ItemMeta itemMeta = inventory.getItemInMainHand().getItemMeta();
                if (itemMeta == null) {
                    sender.sendMessage("The main hand item doesn't have any metadata, so it cannot be set to unbreakable.");
                    return false;
                }
                itemMeta.setUnbreakable(unbreakable);
                sender.sendMessage("The main hand item has been set to " + (unbreakable ? "un" : "") + "breakable successfully!");
                return true;
            }
        }
    }
}
