package io.github.jesp1999.aurumpvp.commands;

import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class SetColorCommandExecutor implements CommandExecutor {
    private final JavaPlugin plugin;

    public SetColorCommandExecutor(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        } else {
            if (args.length != 1 || !args[0].chars().allMatch(c -> (Character.digit(c, 16) != -1))) {
                sender.sendMessage("Incorrect format! Do this instead: /setcolor <hex color> OR /sc <hex color>");
                return false;
            } else {
                final Player player = (Player) sender;
                final Color color = Color.fromRGB(Integer.parseInt(args[0], 16));
                final ItemStack item = player.getInventory().getItemInMainHand();
                final ItemMeta itemMeta = item.getItemMeta();
                if (itemMeta instanceof LeatherArmorMeta) {
                    ((LeatherArmorMeta)itemMeta).setColor(color);
                } else if (itemMeta instanceof PotionMeta) {
                    ((PotionMeta)itemMeta).setColor(color);
                } else {
                    //TODO maybe suggest a format to fix any small syntax errors?
                    sender.sendMessage("The specified item doesn't have a color!");
                    return false;
                }
                item.setItemMeta(itemMeta);
                sender.sendMessage("The color of the item in the mainhand has been changed to \"" + args[0] + "\" successfully!");
                return true;
            }
        }
    }
}
