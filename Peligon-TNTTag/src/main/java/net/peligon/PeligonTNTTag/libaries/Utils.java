package net.peligon.PeligonTNTTag.libaries;

import net.peligon.PeligonTNTTag.Main;
import net.peligon.PeligonTNTTag.libaries.Instances.Game;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;
import java.util.List;
import java.util.*;

public class Utils {

    private static final Main plugin = Main.getInstance;

    // ---- [ Managing chat color within the plugin ] ----
    public static String chatColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
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

    // ---- [ Cached Items ] ----
    public static List<Game> gamesStarted = new ArrayList<>();
    public static List<Game> gamesWaiting = new ArrayList<>();

    public static void joinGame(Player player) {
        if (gamesWaiting.isEmpty()) {
            createGame().addPlayers(player);
        } else {
            for (Game game : gamesWaiting) {
                if (game.getPlayers().size() == game.getMaxPlayers()) continue;
                game.addPlayers(player);
            }
        }
    }

    public static Game createGame() {
        Game game = new Game(UUID.randomUUID().toString(), 10);
        gamesWaiting.add(game);
        return game;
    }
}
