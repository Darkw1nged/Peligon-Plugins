package net.peligon.PeligonAuthentication.libaries;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import net.peligon.PeligonAuthentication.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;
import java.util.*;

public class Utils {

    private static final Main plugin = Main.getInstance;

    // ---- [ Managing chat color within the plugin ] ----
    public static String chatColor(String s) { return ChatColor.translateAlternateColorCodes('&', s); }

    // ---- [ Managing chat color within the plugin | Supports Amount ] ----
    public static String chatColor(String s, Double amount) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        String converted = nf.format(amount);
        return ChatColor.translateAlternateColorCodes('&', s)
                .replaceAll("%amount%", converted);
    }

    // ---- [ Converting a lore to include colors ] ----
    public static List<String> getConvertedLore(FileConfiguration config, String path) {
        if (config == null) return null;
        List<String> oldList = config.getStringList(path + ".lore");
        List<String> newList = new ArrayList<>();
        for (String s : oldList)
            newList.add(ChatColor.translateAlternateColorCodes('&', s));
        return newList;
    }

    // ---- [ Available space ] ----
    public static boolean hasSpace(Player player, ItemStack targetItem) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) continue;
            if (item.getType() == targetItem.getType()) {
                if (item.getAmount() != item.getMaxStackSize()) {
                    item.setAmount(item.getAmount() + 1);
                    return true;
                }
            }
        }
        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(targetItem);
            return true;
        }
        return false;
    }

    // ---- [ Gets block under player ] ----
    public static Optional<Block> getNextBlockUnderPlayer(Player player) {
        Location loc = player.getLocation();
        Block block;
        while(loc.getY() >= 0) {
            loc.subtract(0, 0.5, 0);
            block = loc.getBlock();
            if(block.getType() != Material.AIR) {
                return Optional.of(block);
            }
        }
        return Optional.empty();
    }

    // ---- [ Gets players code ] ----
    public static boolean playerInputCode(Player player, int code) {
        boolean isValid = new GoogleAuthenticator().authorize(plugin.authentication.getToken(player), code);
        if (isValid) {
            neededAuthentication.remove(player.getUniqueId());
            return true;
        }
        return false;
    }

    // ---- [ Cached items ] ----
    public static List<UUID> neededAuthentication = new ArrayList<>();

}
