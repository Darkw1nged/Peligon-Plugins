package net.peligon.PeligonPolls.menus;

import net.peligon.PeligonPolls.Main;
import net.peligon.PeligonPolls.libaries.Menu;
import net.peligon.PeligonPolls.libaries.Utils;
import net.peligon.PeligonPolls.libaries.Poll;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class menuPolls implements Menu {

    private final Main plugin = Main.getInstance;
    private final Player player;
    private final Inventory inventory;

    public menuPolls(Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(this, plugin.getConfig().getInt("polls-inventory.size"),
                Utils.chatColor(plugin.getConfig().getString("polls-inventory.title")));

        int pos = 0;
        for (String key : plugin.getConfig().getConfigurationSection("polls-inventory.contents").getKeys(false)) {
            if (!plugin.getConfig().contains("polls-inventory.contents." + key + ".item") && plugin.getConfig().getString("polls-inventory.contents." + key + ".type").equalsIgnoreCase("poll")) {
                if (Utils.polls.isEmpty() || Utils.polls.size() == pos) {
                    ItemStack item = new ItemStack(Material.AIR);
                    if (plugin.getConfig().getInt("polls-inventory.contents." + key + ".slot") == -1) {
                        for (int i = 0; i < plugin.getConfig().getInt("polls-inventory.size"); i++) {
                            inventory.setItem(i, item);
                        }
                    } else {
                        inventory.setItem(plugin.getConfig().getInt("polls-inventory.contents." + key + ".slot") - 1, item);
                    }
                    continue;
                }
                ItemStack item = new ItemStack(Material.getMaterial(plugin.getConfig().getString("polls-inventory.poll-gui-item.item").toUpperCase()));
                if (plugin.getConfig().contains("polls-inventory.poll-gui-item.amount")) {
                    item.setAmount(plugin.getConfig().getInt("polls-inventory.poll-gui-item.amount"));
                }

                ItemMeta meta = item.getItemMeta();
                Poll pollInfo = Utils.polls.get(pos);

                meta.setDisplayName(Utils.chatColor(plugin.getConfig().getString("polls-inventory.poll-gui-item.name")
                        .replaceAll("%title%", pollInfo.getTitle())));

                if (plugin.getConfig().contains("polls-inventory.poll-gui-item.lore")) {
                    List<String> lore = new ArrayList<>();
                    for (String line : plugin.getConfig().getStringList("polls-inventory.poll-gui-item.lore")) {

                        if (line.contains("%description%")) {
                            for (String s : getDescription(pollInfo.getDescription().trim())) {
                                lore.add(Utils.chatColor(s));
                            }
                            line = line.replaceAll("%description%", "");
                        }

                        lore.add(Utils.chatColor(line)
                                .replaceAll("%author%", pollInfo.getAuthor())
                                .replaceAll("%created%", pollInfo.getCreated().getDayOfMonth() + "-" + pollInfo.getCreated().getMonthValue() + "-" + pollInfo.getCreated().getYear())
                                .replaceAll("%upVote%", "" + pollInfo.getUpVotes())
                                .replaceAll("%downVote%", "" + pollInfo.getDownVotes())
                        );
                    }

                    meta.setLore(lore);
                }

                if (plugin.getConfig().contains("polls-inventory.poll-gui-item.item-flags")) {
                    for (String flag : plugin.getConfig().getStringList("polls-inventory.poll-gui-item.item-flags")) {
                        meta.addItemFlags(ItemFlag.valueOf(flag.toUpperCase()));
                    }
                }

                item.setItemMeta(meta);

                if (plugin.getConfig().getInt("polls-inventory.contents." + key + ".slot") == -1) {
                    for (int i=0; i<plugin.getConfig().getInt("polls-inventory.size"); i++) {
                        inventory.setItem(i, item);
                    }
                } else {
                    inventory.setItem(plugin.getConfig().getInt("polls-inventory.contents." + key + ".slot") - 1, item);
                }
                pos++;
            } else {
                ItemStack item = new ItemStack(Material.getMaterial(plugin.getConfig().getString("polls-inventory.contents." + key + ".item").toUpperCase()));
                if (plugin.getConfig().contains("polls-inventory.contents." + key + ".amount")) {
                    item.setAmount(plugin.getConfig().getInt("polls-inventory.contents." + key + ".amount"));
                }

                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(Utils.chatColor(plugin.getConfig().getString("polls-inventory.contents." + key + ".name")));
                if (plugin.getConfig().contains("polls-inventory.contents." + key + ".lore")) {
                    meta.setLore(Utils.getConvertedLore(plugin.getConfig(), "polls-inventory.contents." + key));
                }

                if (plugin.getConfig().contains("polls-inventory.contents." + key + ".item-flags")) {
                    for (String flag : plugin.getConfig().getStringList("polls-inventory.contents." + key + ".item-flags")) {
                        meta.addItemFlags(ItemFlag.valueOf(flag.toUpperCase()));
                    }
                }

                item.setItemMeta(meta);

                if (plugin.getConfig().getInt("polls-inventory.contents." + key + ".slot") == -1) {
                    for (int i=0; i<plugin.getConfig().getInt("polls-inventory.size"); i++) {
                        inventory.setItem(i, item);
                    }
                } else {
                    inventory.setItem(plugin.getConfig().getInt("polls-inventory.contents." + key + ".slot") - 1, item);
                }
            }
        }
    }

    public void onClick(Main plugin, Player player, int slot, ClickType type) { }

    public void onOpen(Main plugin, Player player) { }

    public void onClose(Main plugin, Player player) { }

    public Inventory getInventory() {
        return this.inventory;
    }

    private List<String> getDescription(String description) {
        int wordsPerLine = plugin.getConfig().getInt("polls-inventory.poll-gui-item.description-word-per-line");
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
