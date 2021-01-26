package io.github.jesp1999.aurumpvp.confighandler;

/**
 * Used to store the necessary constants for the JSONHandler
 * @author Ian McDowell
 */
public abstract class JSONConstants {
	public static final String KIT_FILENAME = "kits.json";
	protected static final String KIT_NAME = "kitName";
	protected static final String KIT_CATEGORY = "kitCategory";
	protected static final String KIT_INVENTORY = "inventory";
	protected static final String ITEM_SLOT = "slot";
	protected static final String ITEM_NAME = "itemName";
	protected static final String ITEM_DISPLAY_NAME = "displayName";
	protected static final String ITEM_DAMAGE = "damage";
	protected static final String ITEM_COLOR = "color";
	protected static final String ITEM_AMOUNT = "amount";
	protected static final String ITEM_ENCHANTMENTS = "enchantments";
	protected static final String ITEM_LORE = "lore";
	protected static final String ITEM_CUSTOM_EFFECTS = "customEffects";
	protected static final String ITEM_BANNER_ART = "bannerArt";
    protected static final boolean OVERWRITE_SAME_EFFECT_TYPE = true;
    protected static final boolean EXCEED_ENCHANTMENT_LEVEL_CAP = true;
}
