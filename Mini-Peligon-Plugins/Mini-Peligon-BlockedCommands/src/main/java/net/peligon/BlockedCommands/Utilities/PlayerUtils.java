package net.peligon.BlockedCommands.Utilities;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerUtils {

    /**
     * This will get the player's UUID.
     *
     * @param player The player to get the UUID of.
     * @return The player's UUID.
     */
    public UUID getPlayerUUID(OfflinePlayer player) {
        return player.getUniqueId();
    }

    /**
     * Get the direction that the player is facing.
     *
     * @param player The player to get the direction of.
     * @return The direction the player is facing.
     */
    public String getPlayerDirection(Player player) {
        String direction = "W";
        double rotation = (player.getLocation().getYaw() - 90) % 360;
        if (rotation < 0) rotation += 360.0;

        if (0 <= rotation && rotation < 22.5) {
            return "W";
        } else if (22.5 <= rotation && rotation < 67.5) {
            return "NW";
        } else if (67.5 <= rotation && rotation < 112.5) {
            return "N";
        } else if (112.5 <= rotation && rotation < 157.5) {
            return "NE";
        } else if (157.5 <= rotation && rotation < 202.5) {
            return "E";
        } else if (202.5 <= rotation && rotation < 247.5) {
            return "SE";
        } else if (247.5 <= rotation && rotation < 292.5) {
            return "S";
        } else if (292.5 <= rotation && rotation < 337.5) {
            return "SW";
        } else if (337.5 <= rotation && rotation < 360.0) {
            return "W";
        }
        return direction;
    }

    /**
     * Check if the player has enough space for items inside their inventory.
     *
     * @param player The player to check.
     * @param targetItem The item to check for.
     * @return True if the player has enough space, false if not.
     */
    public boolean hasSpace(Player player, ItemStack targetItem) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) continue;
            if (item.hasItemMeta() && item.getItemMeta().hasDisplayName() &&
                    targetItem.hasItemMeta() && targetItem.getItemMeta().hasDisplayName()) {
                if (item.getItemMeta().getDisplayName().equals(targetItem.getItemMeta().getDisplayName())) {
                    if (item.getType() == targetItem.getType()) {
                        if (item.getAmount() != item.getMaxStackSize()) {
                            item.setAmount(item.getAmount() + targetItem.getAmount());
                            return true;
                        }
                    }
                }
            } else {
                if (item.getType() == targetItem.getType()) {
                    if (item.getAmount() != item.getMaxStackSize()) {
                        item.setAmount(item.getAmount() + targetItem.getAmount());
                        return true;
                    }
                }
            }
        }
        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(targetItem);
            return true;
        }
        return false;
    }

    /**
     * Get all empty slots in the player's inventory.
     *
     * @param player The player to get the empty slots of.
     * @return A list of empty slots.
     */
    public List<Integer> getEmptySlots(Player player) {
        List<Integer> emptySlots = new ArrayList<>();
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            if (player.getInventory().getItem(i) == null) {
                emptySlots.add(i);
            }
        }
        return emptySlots;
    }

    /**
     * Get the EXP needed to level up.
     *
     * @param level The level to get the EXP of.
     * @return The EXP needed to level up.
     */
    public int getExpToLevelUp(int level) {
        return level <= 15 ? 2 * level + 7 : level <= 30 ? 5 * level - 38 : 9 * level - 158;
    }

    /**
     * Get the EXP at the current level.
     *
     * @param level The level to get the EXP of.
     * @return The EXP at the current level.
     */
    public int getExpAtLevel(int level) {
        return level <= 16 ? (int) (Math.pow(level, 2) + 6 * level) : level <= 31 ? (int) (2.5 * Math.pow(level, 2) - 40.5 * level + 360) : (int) (4.5 * Math.pow(level, 2) - 162.5 * level + 2220);
    }

    /**
     * Get the EXP of the player.
     *
     * @param player The player to get the EXP of.
     * @return The EXP of the player.
     */
    public int getPlayerExp(Player player) {
        int exp = 0;
        int level = player.getLevel();

        exp += getExpAtLevel(level);
        exp += Math.round(getExpToLevelUp(level) * player.getExp());
        return exp;
    }

    public void removePlayerExp(Player player, int exp){
        // Get player's current exp
        int currentExp = getPlayerExp(player);

        // Reset player's current exp to 0
        player.setExp(0);
        player.setLevel(0);

        // Give the player their exp back, with the difference
        int newExp = currentExp - exp;
        player.giveExp(newExp);
    }

}
