package io.github.jesp1999.aurumpvp.commands;

import io.github.jesp1999.aurumpvp.player.PlayerInfo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class ClearKitCommandExecutor implements CommandExecutor {
    private final JavaPlugin plugin;

    public ClearKitCommandExecutor(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        } else {
            final Player player = (Player) sender;
            PlayerInfo playerInfo = PlayerInfo.activePlayers.get(player.getName());
            playerInfo.getSlotRestockInformation().clear();
            playerInfo.setCurrentKit(null);
            sender.sendMessage("Successfully cleared the current kit!");
            return true;
        }
    }
}
