package net.peligon.LifeSteal.libaries.items;

import net.peligon.LifeSteal.Main;
import net.peligon.LifeSteal.libaries.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class RevivalBeacons {

    private final Main plugin = Main.getInstance;

    public ItemStack revivalBeaconTier1() {
        ItemStack item = new ItemStack(Material.BEACON);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor("&f&lRevive Beacon [Tier I]"));
        List<String> lore = new ArrayList<>();
        lore.add(Utils.chatColor("&7Place this beacon down to revive an"));
        lore.add(Utils.chatColor("&7eliminated player. You must protect the"));
        lore.add(Utils.chatColor("&7beacon during the resurrection process!"));
        lore.add("");
        lore.add(Utils.chatColor("&f&lInformation"));
        lore.add(Utils.chatColor(" &f&l▎ &7&l Revival Time: &e120 seconds"));
        lore.add(Utils.chatColor(" &f&l▎ &7&l Revival Cost: &e$3,000"));
        lore.add(Utils.chatColor(" &f&l▎ &7&l Cooldown: &e2 hours"));
        lore.add(Utils.chatColor(" &f&l▎ &7&l durability: &e5 breaks"));
        lore.add("");
        lore.add(Utils.chatColor("&c&lWarning: &8Location will broadcast upon usage!"));
        lore.add(Utils.chatColor("&8Place down to activate"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack revivalBeaconTier2() {
        ItemStack item = new ItemStack(Material.BEACON);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor("&a&lRevive Beacon [Tier II]"));
        List<String> lore = new ArrayList<>();
        lore.add(Utils.chatColor("&7Place this beacon down to revive an"));
        lore.add(Utils.chatColor("&7eliminated player. You must protect the"));
        lore.add(Utils.chatColor("&7beacon during the resurrection process!"));
        lore.add("");
        lore.add(Utils.chatColor("&a&lInformation"));
        lore.add(Utils.chatColor(" &a&l▎ &7&l Revival Time: &e90 seconds"));
        lore.add(Utils.chatColor(" &a&l▎ &7&l Revival Cost: &e$5,000"));
        lore.add(Utils.chatColor(" &a&l▎ &7&l Cooldown: &e2.5 hours"));
        lore.add(Utils.chatColor(" &a&l▎ &7&l durability: &e5 breaks"));
        lore.add("");
        lore.add(Utils.chatColor("&c&lWarning: &8Location will broadcast upon usage!"));
        lore.add(Utils.chatColor("&8Place down to activate"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack revivalBeaconTier3() {
        ItemStack item = new ItemStack(Material.BEACON);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor("&e&lRevive Beacon [Tier III]"));
        List<String> lore = new ArrayList<>();
        lore.add(Utils.chatColor("&7Place this beacon down to revive an"));
        lore.add(Utils.chatColor("&7eliminated player. You must protect the"));
        lore.add(Utils.chatColor("&7beacon during the resurrection process!"));
        lore.add("");
        lore.add(Utils.chatColor("&6&lInformation"));
        lore.add(Utils.chatColor(" &6&l▎ &7&l Revival Time: &e60 seconds"));
        lore.add(Utils.chatColor(" &6&l▎ &7&l Revival Cost: &e$10,000"));
        lore.add(Utils.chatColor(" &6&l▎ &7&l Cooldown: &e3 hours"));
        lore.add(Utils.chatColor(" &6&l▎ &7&l durability: &e5 breaks"));
        lore.add("");
        lore.add(Utils.chatColor("&c&lWarning: &8Location will broadcast upon usage!"));
        lore.add(Utils.chatColor("&8Place down to activate"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack revivalBeaconTierSpecial() {
        ItemStack item = new ItemStack(Material.BEACON);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor("&5&lRevive Beacon [Special]"));
        List<String> lore = new ArrayList<>();
        lore.add(Utils.chatColor("&7Place this beacon down to revive an"));
        lore.add(Utils.chatColor("&7eliminated player. You must protect the"));
        lore.add(Utils.chatColor("&7beacon during the resurrection process!"));
        lore.add("");
        lore.add(Utils.chatColor("&5&lInformation"));
        lore.add(Utils.chatColor(" &5&l▎ &7&l Revival Time: &e5 minutes"));
        lore.add(Utils.chatColor(" &5&l▎ &7&l Revival Cost: &e$7,500"));
        lore.add(Utils.chatColor(" &5&l▎ &7&l Cooldown: &e6 hours"));
        lore.add(Utils.chatColor(" &5&l▎ &7&l durability: &e1 break"));
        lore.add("");
        lore.add(Utils.chatColor("&5&lSpecial Effect: &7No location broadcast upon usage!"));
        lore.add(Utils.chatColor("&8Place down to activate"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }


}
