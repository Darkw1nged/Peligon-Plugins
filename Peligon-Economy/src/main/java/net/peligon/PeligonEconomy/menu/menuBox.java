package net.peligon.PeligonEconomy.menu;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.CustomConfig;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.managers.Menu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class menuBox implements Menu {

    private final Main plugin = Main.getInstance;
    private final Player player;
    private Inventory inventory;
    private Map<UUID, Integer> playerPage = new HashMap<>();

    public menuBox(Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(this, 18,
                Utils.chatColor(plugin.fileBoxGUI.getConfig().getString("inventory.title")));
        List<ItemStack> items = new ArrayList<>();
        Map<Integer, List<ItemStack>> contents = new HashMap<>();

        CustomConfig config = new CustomConfig(plugin, player.getUniqueId().toString(), "data/gifts");
        YamlConfiguration data = config.getConfig();

        int page = 1;
        if (data.contains("items")) {
            playerPage.put(player.getUniqueId(), page);

            if (!data.getConfigurationSection("items").getKeys(false).isEmpty()) {
                for (String key : data.getConfigurationSection("items").getKeys(false)) {
                    items.add(data.getItemStack("items." + key));
                    if (items.size() >= 45) {
                        contents.put(page, items);
                        page++;
                        items = new ArrayList<>();
                    }
                }
            }
            contents.put(page, items);

            if (items.size() > 36 && items.size() <= 45 || page > 1) {
                this.inventory = Bukkit.createInventory(this, 54,
                        Utils.chatColor(plugin.fileBoxGUI.getConfig().getString("inventory.title")));
            } else if (items.size() > 27 && items.size() <= 36) {
                this.inventory = Bukkit.createInventory(this, 45,
                        Utils.chatColor(plugin.fileBoxGUI.getConfig().getString("inventory.title")));
            } else if (items.size() > 18 && items.size() <= 27) {
                this.inventory = Bukkit.createInventory(this, 36,
                        Utils.chatColor(plugin.fileBoxGUI.getConfig().getString("inventory.title")));
            } else if (items.size() > 9 && items.size() <= 18) {
                this.inventory = Bukkit.createInventory(this, 27,
                        Utils.chatColor(plugin.fileBoxGUI.getConfig().getString("inventory.title")));
            }

            for (int i = 0; i < contents.size(); i++) {
                if (playerPage.containsKey(player.getUniqueId())) {
                    if (playerPage.get(player.getUniqueId()) == i + 1) {
                        for (ItemStack item : contents.get(i + 1)) {
                            this.inventory.addItem(item);
                        }
                    }
                }
            }

        }

        for (String key : plugin.fileBoxGUI.getConfig().getConfigurationSection("inventory.contents").getKeys(false)) {

            ItemStack item = new ItemStack(Material.getMaterial(plugin.fileBoxGUI.getConfig().getString("inventory.contents." + key + ".item").toUpperCase()));
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Utils.chatColor(plugin.fileBoxGUI.getConfig().getString("inventory.contents." + key + ".name")));

            if (plugin.fileBoxGUI.getConfig().contains("inventory.contents." + key + ".lore")) {
                List<String> lore = new ArrayList<>();
                for (String line : plugin.fileBoxGUI.getConfig().getStringList("inventory.contents." + key + ".lore")) {
                    lore.add(Utils.chatColor(line));
                }
                meta.setLore(lore);
            }

            if (plugin.fileBoxGUI.getConfig().contains("inventory.contents." + key + ".item-flags")) {
                for (String flag : plugin.fileBoxGUI.getConfig().getStringList("inventory.contents." + key + ".item-flags")) {
                    meta.addItemFlags(ItemFlag.valueOf(flag));
                }
            }

            item.setItemMeta(meta);

            if (plugin.fileBoxGUI.getConfig().contains("inventory.contents." + key + ".event") && plugin.fileBoxGUI.getConfig().getString("inventory.contents." + key + ".event").equalsIgnoreCase("NextPage")) {
                if (page == 1 || playerPage.get(player.getUniqueId()) == page) continue;
            }

            if (plugin.fileBoxGUI.getConfig().contains("inventory.contents." + key + ".event") && plugin.fileBoxGUI.getConfig().getString("inventory.contents." + key + ".event").equalsIgnoreCase("PreviousPage")) {
                if (page == 1 || playerPage.get(player.getUniqueId()) == 1) continue;
            }

            if (plugin.fileBoxGUI.getConfig().getInt("inventory.contents." + key + ".slot") == -1) {
                if (this.inventory.getSize() == 54) {
                    for (int i = 45; i < 54; i++) {
                        this.inventory.setItem(i, item);
                    }
                } else if (this.inventory.getSize() == 45) {
                    for (int i = 36; i < 45; i++) {
                        this.inventory.setItem(i, item);
                    }
                } else if (this.inventory.getSize() == 36) {
                    for (int i = 27; i < 36; i++) {
                        this.inventory.setItem(i, item);
                    }
                } else if (this.inventory.getSize() == 27) {
                    for (int i = 18; i < 27; i++) {
                        this.inventory.setItem(i, item);
                    }
                } else if (this.inventory.getSize() == 18) {
                    for (int i = 9; i < 18; i++) {
                        this.inventory.setItem(i, item);
                    }
                }
            } else {
                if (this.inventory.getSize() == 54) {
                    this.inventory.setItem(44 + plugin.fileBoxGUI.getConfig().getInt("inventory.contents." + key + ".slot"), item);
                } else if (this.inventory.getSize() == 45) {
                    for (int i = 36; i < 45; i++) {
                        this.inventory.setItem(35 + plugin.fileBoxGUI.getConfig().getInt("inventory.contents." + key + ".slot"), item);
                    }
                } else if (this.inventory.getSize() == 36) {
                    for (int i = 27; i < 36; i++) {
                        this.inventory.setItem(26 + plugin.fileBoxGUI.getConfig().getInt("inventory.contents." + key + ".slot"), item);
                    }
                } else if (this.inventory.getSize() == 27) {
                    for (int i = 18; i < 27; i++) {
                        this.inventory.setItem(17 + plugin.fileBoxGUI.getConfig().getInt("inventory.contents." + key + ".slot"), item);
                    }
                } else if (this.inventory.getSize() == 18) {
                    for (int i = 9; i < 18; i++) {
                        this.inventory.setItem(8 + plugin.fileBoxGUI.getConfig().getInt("inventory.contents." + key + ".slot"), item);
                    }
                }
            }
        }
    }

    public void onClick(Main plugin, Player player, int slot, ClickType type) {
    }

    public void onOpen(Main plugin, Player player) {
    }

    public void onClose(Main plugin, Player player) {
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public Map<UUID, Integer> getPlayerPage() {
        return this.playerPage;
    }
}
