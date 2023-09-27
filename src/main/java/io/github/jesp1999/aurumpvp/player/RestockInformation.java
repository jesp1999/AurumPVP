package io.github.jesp1999.aurumpvp.player;

import org.bukkit.inventory.ItemStack;

public class RestockInformation {
    private final String slot;
    private final ItemStack itemStack;
    private final int cooldown;
    private final int maxStackSize;

    public RestockInformation(String slot, ItemStack itemStack, int cooldown, int maxStackSize) {
        this.slot = slot;
        this.itemStack = itemStack;
        this.cooldown = cooldown;
        this.maxStackSize = maxStackSize;
    }

    public String getSlot() {
        return this.slot;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public int getCooldown() {
        return this.cooldown;
    }

    public int getMaxStackSize() {
        return this.maxStackSize;
    }
}
