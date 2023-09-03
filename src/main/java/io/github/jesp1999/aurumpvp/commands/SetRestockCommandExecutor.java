package io.github.jesp1999.aurumpvp.commands;

import io.github.jesp1999.aurumpvp.player.PlayerInfo;
import io.github.jesp1999.aurumpvp.player.RestockInformation;
import io.github.jesp1999.aurumpvp.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

public class SetRestockCommandExecutor implements CommandExecutor {
    private final JavaPlugin plugin;

    public SetRestockCommandExecutor(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        } else {
            if (args.length < 2) {
                sender.sendMessage("Incorrect format! Do this instead: /setrestock <slot name> <amount> [cooldown] [max] OR /sr <slot name> <amount> [cooldown] [max]");
                return false;
            } else {
                final Player player = (Player) sender;
                final String slotName = args[0];
                final int amount = Integer.parseInt(args[1]);
                final int cooldown = args.length > 2 ? Integer.parseInt(args[2]) : -1;
                final int maxAmount = args.length > 3 ? Integer.parseInt(args[3]) : -1;
                try {
                    ItemStack restockStack = Utils.getItemStackAtSlot(player, slotName);
                    restockStack.setAmount(amount);
                    RestockInformation restockInformation = new RestockInformation(slotName, restockStack, cooldown, maxAmount);
                    PlayerInfo.activePlayers.get(player.getName()).getSlotRestockInformation().put(slotName, restockInformation);
                    sender.sendMessage("The item in the \"" + slotName + "\" slot has been set to restock successfully!");
                    return true;
                } catch (IllegalArgumentException ex) {
                    sender.sendMessage("Specified slot \"" + slotName + "\" is not a valid inventory slot name!");
                    return false;
                }
            }
        }
    }
}
