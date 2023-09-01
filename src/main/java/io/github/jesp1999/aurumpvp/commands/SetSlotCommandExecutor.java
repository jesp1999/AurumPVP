package io.github.jesp1999.aurumpvp.commands;

import io.github.jesp1999.aurumpvp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SetSlotCommandExecutor implements CommandExecutor {
    private final JavaPlugin plugin;

    public SetSlotCommandExecutor(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        } else {
            if (args.length != 1) {
                sender.sendMessage("Incorrect format! Do this instead: /setslot <slot name> OR /ss <slot name>");
                return false;
            } else {
                final Player player = (Player) sender;
                final String slotName = args[0];
                final PlayerInventory inventory = player.getInventory();
                if (Utils.inventorySlots.containsKey(slotName)) {
                    inventory.setItem(Utils.inventorySlots.get(slotName), inventory.getItemInMainHand());
                } else if (slotName.equals("armor.head")) {
                    inventory.setHelmet(inventory.getItemInMainHand());
                } else if (slotName.equals("armor.chest")) {
                    inventory.setChestplate(inventory.getItemInMainHand());
                } else if (slotName.equals("armor.legs")) {
                    inventory.setLeggings(inventory.getItemInMainHand());
                } else if (slotName.equals("armor.feet")) {
                    inventory.setBoots(inventory.getItemInMainHand());
                } else if (slotName.equals("weapon.offhand")) {
                    inventory.setItemInOffHand(inventory.getItemInMainHand());
                } else {
                    //TODO maybe suggest a format to fix any small syntax errors?
                    sender.sendMessage("The specified slot does not exist!");
                    return false;
                }
                inventory.setItemInMainHand(null);
                sender.sendMessage("The main hand item has been moved to the \"" + slotName + "\" slot successfully!");
                return true;
            }
        }
    }
}
