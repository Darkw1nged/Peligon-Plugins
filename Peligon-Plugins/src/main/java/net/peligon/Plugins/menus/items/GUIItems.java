package net.peligon.Plugins.menus.items;

import net.peligon.Plugins.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GUIItems {
    public static ItemStack peligonAuthentication() {
        ItemStack item = new ItemStack(Material.IRON_DOOR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor("&e&lPeligon Authentication"));

        List<String> lore = new ArrayList<>();
        lore.add(Utils.chatColor("&8Protects players account with"));
        lore.add(Utils.chatColor("&bGoogle Authenticator&8."));
        lore.add("");
        lore.add(Utils.chatColor("&c&lInformation"));
        lore.add(Utils.chatColor(" &c&l▎ &fVersion: &e1.0"));
        lore.add(Utils.chatColor(" &c&l▎ &fHas support: &aYes"));
        lore.add("");
        lore.add(Utils.chatColor("&8[Right-Click] &efor documentation."));

        if (Bukkit.getServer().getPluginManager().getPlugin("PeligonAuthenticator") == null) {
            lore.add(Utils.chatColor("&8[Left-Click] &efor resource link."));
        } else {
            lore.add(Utils.chatColor("&8[Left-Click] &eto reload plugin."));
        }
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack peligonCore() {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor("&e&lPeligon Core"));

        List<String> lore = new ArrayList<>();
        lore.add(Utils.chatColor("&c&lNOT RELEASED!"));
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack peligonEconomy() {
        ItemStack item = new ItemStack(Material.GOLD_NUGGET);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor("&e&lPeligon Economy"));

        List<String> lore = new ArrayList<>();
        lore.add(Utils.chatColor("&8An economy that every"));
        lore.add(Utils.chatColor("&8server owner needs."));
        lore.add("");
        lore.add(Utils.chatColor("&c&lInformation"));
        lore.add(Utils.chatColor(" &c&l▎ &fVersion: &e1.1"));
        lore.add(Utils.chatColor(" &c&l▎ &fHas support: &aYes"));
        lore.add("");
        lore.add(Utils.chatColor("&8[Right-Click] &efor documentation."));

        if (Bukkit.getServer().getPluginManager().getPlugin("PeligonEconomy") == null) {
            lore.add(Utils.chatColor("&8[Left-Click] &efor resource link."));
        } else {
            lore.add(Utils.chatColor("&8[Left-Click] &eto reload plugin."));
        }
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack peligonEnchants() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor("&e&lPeligon Enchants"));

        List<String> lore = new ArrayList<>();
        lore.add(Utils.chatColor("&c&lNOT RELEASED!"));
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack peligonEnhancedStorage() {
        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor("&e&lPeligon EnhancedStorage"));

        List<String> lore = new ArrayList<>();
        lore.add(Utils.chatColor("&8Adds additional storage options"));
        lore.add(Utils.chatColor("&8to your server."));
        lore.add("");
        lore.add(Utils.chatColor("&c&lInformation"));
        lore.add(Utils.chatColor(" &c&l▎ &fVersion: &e1.0"));
        lore.add(Utils.chatColor(" &c&l▎ &fHas support: &aYes"));
        lore.add("");
        lore.add(Utils.chatColor("&8[Right-Click] &efor documentation."));

        if (Bukkit.getServer().getPluginManager().getPlugin("PeligonEnhancedStorage") == null) {
            lore.add(Utils.chatColor("&8[Left-Click] &efor resource link."));
        } else {
            lore.add(Utils.chatColor("&8[Left-Click] &eto reload plugin."));
        }
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack peligonLifeSteal() {
        ItemStack item = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor("&e&lPeligon LifeSteal"));

        List<String> lore = new ArrayList<>();
        lore.add(Utils.chatColor("&8Gives players a limited number"));
        lore.add(Utils.chatColor("&8of lives."));
        lore.add("");
        lore.add(Utils.chatColor("&c&lInformation"));
        lore.add(Utils.chatColor(" &c&l▎ &fVersion: &e2.0"));
        lore.add(Utils.chatColor(" &c&l▎ &fHas support: &aYes"));
        lore.add("");
        lore.add(Utils.chatColor("&8[Right-Click] &efor documentation."));

        if (Bukkit.getServer().getPluginManager().getPlugin("PeligonLifeSteal") == null) {
            lore.add(Utils.chatColor("&8[Left-Click] &efor resource link."));
        } else {
            lore.add(Utils.chatColor("&8[Left-Click] &eto reload plugin."));
        }
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack peligonPlaytime() {
        ItemStack item = new ItemStack(Material.CLOCK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor("&e&lPeligon Playtime"));

        List<String> lore = new ArrayList<>();
        lore.add(Utils.chatColor("&8Allow players to earn rewards"));
        lore.add(Utils.chatColor("&8for playing on the server."));
        lore.add("");
        lore.add(Utils.chatColor("&c&lInformation"));
        lore.add(Utils.chatColor(" &c&l▎ &fVersion: &e1.3.1"));
        lore.add(Utils.chatColor(" &c&l▎ &fHas support: &aYes"));
        lore.add("");
        lore.add(Utils.chatColor("&8[Right-Click] &efor documentation."));

        if (Bukkit.getServer().getPluginManager().getPlugin("PeligonPlaytime") == null) {
            lore.add(Utils.chatColor("&8[Left-Click] &efor resource link."));
        } else {
            lore.add(Utils.chatColor("&8[Left-Click] &eto reload plugin."));
        }
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack peligonPolls() {
        ItemStack item = new ItemStack(Material.GREEN_WOOL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor("&e&lPeligon Polls"));

        List<String> lore = new ArrayList<>();
        lore.add(Utils.chatColor("&8This will connect polls inside"));
        lore.add(Utils.chatColor("&8your game to discord."));
        lore.add("");
        lore.add(Utils.chatColor("&c&lInformation"));
        lore.add(Utils.chatColor(" &c&l▎ &fVersion: &e1.1"));
        lore.add(Utils.chatColor(" &c&l▎ &fHas support: &aYes"));
        lore.add("");
        lore.add(Utils.chatColor("&8[Right-Click] &efor documentation."));

        if (Bukkit.getServer().getPluginManager().getPlugin("PeligonPolls") == null) {
            lore.add(Utils.chatColor("&8[Left-Click] &efor resource link."));
        } else {
            lore.add(Utils.chatColor("&8[Left-Click] &eto reload plugin."));
        }
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack peligonStaff() {
        ItemStack item = new ItemStack(Material.IRON_BARS);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor("&e&lPeligon Staff"));

        List<String> lore = new ArrayList<>();
        lore.add(Utils.chatColor("&c&lNOT RELEASED!"));
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack peligonTeams() {
        ItemStack item = new ItemStack(Material.SHIELD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor("&e&lPeligon Teams"));

        List<String> lore = new ArrayList<>();
        lore.add(Utils.chatColor("&c&lNOT RELEASED!"));
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }



}
