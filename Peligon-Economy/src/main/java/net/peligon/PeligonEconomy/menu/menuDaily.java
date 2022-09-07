package net.peligon.PeligonEconomy.menu;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.libaries.struts.Menu;
import net.peligon.PeligonEconomy.libaries.struts.MenuOwnerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class menuDaily extends Menu {

    private final Main plugin = Main.getInstance;
    public menuDaily(MenuOwnerUtil menuOwnerUtil) {
        super(menuOwnerUtil);
    }

    @Override
    public String getMenuName() {
        return Utils.chatColor(plugin.fileDailyReward.getConfig().getString("inventory.title"));
    }

    @Override
    public int getSlots() {
        return Math.min(plugin.fileDailyReward.getConfig().getInt("inventory.size"), 54);
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        for (String key : plugin.fileDailyReward.getConfig().getConfigurationSection("inventory.contents").getKeys(false)) {
            if (item.getType().equals(Material.getMaterial(plugin.fileDailyReward.getConfig().getString("inventory.contents." + key + ".item").toUpperCase()))
                    && item.getItemMeta().getDisplayName().equals(Utils.chatColor(plugin.fileDailyReward.getConfig().getString("inventory.contents." + key + ".name")))) {
                if (plugin.fileDailyReward.getConfig().contains("inventory.contents." + key + ".event")) {
                    switch (plugin.fileDailyReward.getConfig().getString("inventory.contents." + key + ".event").toLowerCase()) {
                        case "item":
                            if (Utils.canClaim(player)) {
                                for (String command : plugin.fileDailyReward.getConfig().getStringList("inventory.contents." + key + ".commands")) {
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
                                    Utils.dailyCooldown.put(player.getUniqueId(), System.currentTimeMillis());
                                }
                            } else {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("daily-already-claimed")));
                            }
                            event.setCancelled(true);
                            return;
                        case "close":
                            player.closeInventory();
                            event.setCancelled(true);
                            return;
                    }
                } else {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @Override
    public void setMenuItems() {
        for (String key : plugin.fileDailyReward.getConfig().getConfigurationSection("inventory.contents").getKeys(false)) {
            ItemStack item = new ItemStack(Material.getMaterial(plugin.fileDailyReward.getConfig().getString("inventory.contents." + key + ".item").toUpperCase()));
            if (plugin.fileDailyReward.getConfig().contains("inventory.contents." + key + ".amount")) {
                item.setAmount(plugin.fileDailyReward.getConfig().getInt("inventory.contents." + key + ".amount"));
            }

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Utils.chatColor(plugin.fileDailyReward.getConfig().getString("inventory.contents." + key + ".name")));
            if (plugin.fileDailyReward.getConfig().contains("inventory.contents." + key + ".lore")) {
                ArrayList<String> lore = new ArrayList<>();
                for (String line : plugin.fileDailyReward.getConfig().getStringList("inventory.contents." + key + ".lore")) {
                    lore.add(Utils.chatColor(line));
                }
                meta.setLore(lore);
            }

            if (plugin.fileDailyReward.getConfig().contains("inventory.contents." + key + ".item-flags")) {
                for (String flag : plugin.fileDailyReward.getConfig().getStringList("inventory.contents." + key + ".item-flags")) {
                    meta.addItemFlags(ItemFlag.valueOf(flag.toUpperCase()));
                }
            }
            item.setItemMeta(meta);

            if (plugin.fileDailyReward.getConfig().getInt("inventory.contents." + key + ".slot") == -1) {
                for (int i = 0; i < plugin.fileDailyReward.getConfig().getInt("inventory.size"); i++) {
                    inventory.setItem(i, item);
                }
            } else {
                inventory.setItem(plugin.fileDailyReward.getConfig().getInt("inventory.contents." + key + ".slot") - 1, item);
            }
        }
    }

}