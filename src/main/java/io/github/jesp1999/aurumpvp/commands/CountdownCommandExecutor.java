package io.github.jesp1999.aurumpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class CountdownCommandExecutor implements CommandExecutor {
    private final JavaPlugin plugin;

    public CountdownCommandExecutor(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Is run whenever the countdown command is run
     *
     * @param numOfSeconds How long the countdown is
     */
    private void countDown(int numOfSeconds) {
        this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
            int counter = numOfSeconds;

            public void run() {
                if (counter >= 0) {
                    Bukkit.broadcastMessage(Integer.toString(counter));
                    counter--;
                }
            }
        }, 0L, 20L);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length > 1) {
            commandSender.sendMessage("Too many arguments");
            return false;
        }
        if (strings.length < 1) {
            commandSender.sendMessage("Too few arguments");
            return false;
        }
        if (!(strings[0].chars().allMatch(Character::isDigit))) {
            commandSender.sendMessage("First argument must be an integer");
            return false;
        }
        countDown(Integer.parseInt(strings[0]));
        return true;
    }
}
