package net.peligon.PeligonPolls.menus;

import net.peligon.PeligonPolls.Main;
import net.peligon.PeligonPolls.libaries.Utils;
import net.peligon.PeligonPolls.libaries.struts.Poll;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CustomItems {

    private static final Main plugin = Main.getInstance;

    public static ItemStack background() {
        ItemStack item = new ItemStack(Material.valueOf(plugin.fileCustomItems.getConfig().getString("background.material")));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor(plugin.fileCustomItems.getConfig().getString("background.name")));
        meta.setLore(Utils.getConvertedLore(plugin.fileCustomItems.getConfig(), "background"));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack closeButton() {
        ItemStack item = new ItemStack(Material.valueOf(plugin.fileCustomItems.getConfig().getString("close-button.material")));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor(plugin.fileCustomItems.getConfig().getString("close-button.name")));
        meta.setLore(Utils.getConvertedLore(plugin.fileCustomItems.getConfig(), "close-button"));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack refreshButton() {
        ItemStack item = new ItemStack(Material.valueOf(plugin.fileCustomItems.getConfig().getString("refresh-button.material")));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor(plugin.fileCustomItems.getConfig().getString("refresh-button.name")));
        meta.setLore(Utils.getConvertedLore(plugin.fileCustomItems.getConfig(), "refresh-button"));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack nextPage() {
        ItemStack item = new ItemStack(Material.valueOf(plugin.fileCustomItems.getConfig().getString("next-page-button.material")));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor(plugin.fileCustomItems.getConfig().getString("next-page-button.name")));
        meta.setLore(Utils.getConvertedLore(plugin.fileCustomItems.getConfig(), "next-page-button"));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack previousPage() {
        ItemStack item = new ItemStack(Material.valueOf(plugin.fileCustomItems.getConfig().getString("previous-page-button.material")));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor(plugin.fileCustomItems.getConfig().getString("previous-page-button.name")));
        meta.setLore(Utils.getConvertedLore(plugin.fileCustomItems.getConfig(), "previous-page-button"));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack pollItem(Poll poll) {
        ItemStack item = new ItemStack(Material.valueOf(plugin.getConfig().getString("polls-placeholder.material")));
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Utils.chatColor(plugin.getConfig().getString("polls-placeholder.name").replaceAll("%title%", poll.getTitle())));
        List<String> lore = new ArrayList<>();
        for (String line : plugin.getConfig().getStringList("polls-placeholder.lore")) {

            if (line.contains("%description%")) {
                for (String s : getDescription(poll.getDescription().trim())) {
                    lore.add(Utils.chatColor(s));
                }
                line = line.replaceAll("%description%", "");
            }

            lore.add(Utils.chatColor(line)
                    .replaceAll("%author%", poll.getAuthor())
                    .replaceAll("%created%", poll.getCreated().getDayOfMonth() + "-" + poll.getCreated().getMonthValue() + "-" + poll.getCreated().getYear())
                    .replaceAll("%upVote%", "" + poll.getUpVotes())
                    .replaceAll("%downVote%", "" + poll.getDownVotes())
            );
        }
        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

    private static List<String> getDescription(String description) {
        int wordsPerLine = plugin.getConfig().getInt("polls-placeholder.description-word-per-line");
        String[] words = description.split(" ");
        StringBuilder line = new StringBuilder("&f");

        List<String> lore = new ArrayList<>();
        for (String word : words) {
            if (word.contains(" ")) continue;

            line.append(word).append(" ");
            if (line.toString().split(" ").length == wordsPerLine) {
                if (!lore.isEmpty() && (lore.get(0).length() + line.length()) >= lore.get(0).length()) continue;
                lore.add(Utils.chatColor(line.toString()));
                line = new StringBuilder("&f");
            }
        }

        if (!line.toString().equals("&7")) {
            lore.add(Utils.chatColor(line.toString()));
        }

        return lore;
    }

}
