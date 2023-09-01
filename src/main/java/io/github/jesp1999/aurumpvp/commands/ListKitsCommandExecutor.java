package io.github.jesp1999.aurumpvp.commands;

import io.github.jesp1999.aurumpvp.kit.Kit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class ListKitsCommandExecutor implements CommandExecutor {
    private final JavaPlugin plugin;

    public ListKitsCommandExecutor(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0) {
            sender.sendMessage("Incorrect format! Do this instead: /listkits");
            return false;
        } else {
            final Set<String> kitNames = new HashSet<>();
            for (Kit kit : Kit.kits.values()) {
                kitNames.add(kit.getName());
            }
            sender.sendMessage("Kits: " + kitNames);
            return true;
        }
    }
}
