package net.peligon.PeligonEnchants.libaries;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class Wrapper extends Enchantment {

    private final String name;
    private final int maxLvl;

    public Wrapper(String namespace, String name, int lvl) {
        super(NamespacedKey.minecraft(namespace));
        this.name = name;
        this.maxLvl = lvl;
    }

    /**
     * Gets the unique name of this enchantment
     *
     * @return Unique name
     * @deprecated enchantments are badly named, use {@link #getKey()}.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Gets the maximum level that this Enchantment may become.
     *
     * @return Maximum level of the Enchantment
     */
    @Override
    public int getMaxLevel() {
        return this.maxLvl;
    }

    /**
     * Gets the level that this Enchantment should start at
     *
     * @return Starting level of the Enchantment
     */
    @Override
    public int getStartLevel() {
        return 0;
    }

    /**
     * Gets the type of {@link ItemStack} that may fit this Enchantment.
     *
     * @return Target type of the Enchantment
     */
    @Override
    public EnchantmentTarget getItemTarget() {
        return null;
    }

    /**
     * Checks if this enchantment is a treasure enchantment.
     * <br>
     * Treasure enchantments can only be received via looting, trading, or
     * fishing.
     *
     * @return true if the enchantment is a treasure enchantment
     */
    @Override
    public boolean isTreasure() {
        return false;
    }

    /**
     * Checks if this enchantment is a cursed enchantment
     * <br>
     * Cursed enchantments are found the same way treasure enchantments are
     *
     * @return true if the enchantment is cursed
     * @deprecated cursed enchantments are no longer special. Will return true
     * only for {@link Enchantment#BINDING_CURSE} and
     * {@link Enchantment#VANISHING_CURSE}.
     */
    @Override
    public boolean isCursed() {
        return false;
    }

    /**
     * Check if this enchantment conflicts with another enchantment.
     *
     * @param other The enchantment to check against
     * @return True if there is a conflict.
     */
    @Override
    public boolean conflictsWith(Enchantment other) {
        return false;
    }

    /**
     * Checks if this Enchantment may be applied to the given {@link
     * ItemStack}.
     * <p>
     * This does not check if it conflicts with any enchantments already
     * applied to the item.
     *
     * @param item Item to test
     * @return True if the enchantment may be applied, otherwise False
     */
    @Override
    public boolean canEnchantItem(ItemStack item) {
        return false;
    }
}
