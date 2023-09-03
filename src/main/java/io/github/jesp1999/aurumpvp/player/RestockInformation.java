package io.github.jesp1999.aurumpvp.player;

import org.bukkit.inventory.ItemStack;

public class RestockInformation {
    private final String slot;
    private final ItemStack itemStack;
    private final int maxStackSize;
    private final int cooldown;

    public RestockInformation(String slot, ItemStack itemStack, int maxStackSize, int cooldown) {
        this.slot = slot;
        this.itemStack = itemStack;
        this.maxStackSize = maxStackSize;
        this.cooldown = cooldown;
    }

    public String getSlot() {
        return this.slot;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public int getMaxStackSize() {
        return this.maxStackSize;
    }

    public int getCooldown() {
        return this.cooldown;
    }
}
