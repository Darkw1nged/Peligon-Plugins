package net.peligon.Playtime.menus;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.peligon.Playtime.Main;
import net.peligon.Playtime.libaries.Utils;
import net.peligon.Playtime.libaries.struts.Menu;
import net.peligon.Playtime.menus.inventoryItems.PeligonPluginPlaceholders;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.C;

public class menuPeligonPlugins extends Menu {

    private final Main plugin = Main.getInstance;
    public menuPeligonPlugins(Player player) {
        super(player);
    }

    @Override
    public String getMenuName() {
        return "&ePeligon Plugins";
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        switch (event.getSlot()) {
            case 10:
                if (event.isLeftClick() && plugin.getServer().getPluginManager().getPlugin("PeligonAuthenticator") == null) {
                    TextComponent message = new TextComponent(Utils.chatColor("&ePeligon >> &a&l&nResource Link"));
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/peligon-authentication.100384/"));
                    player.spigot().sendMessage(message);
                } else if (event.isRightClick()) {
                    TextComponent message = new TextComponent(Utils.chatColor("&ePeligon >> &a&l&nDocumentation Link"));
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/Darkw1nged/Peligon-Plugins/wiki/Peligon-Authentication-:-Commands-and-Permissions"));
                    player.spigot().sendMessage(message);
                }
                break;
            case 12:
                // not released
                break;
            case 14:
                if (event.isLeftClick() && plugin.getServer().getPluginManager().getPlugin("PeligonEconomy") == null) {
                    TextComponent message = new TextComponent(Utils.chatColor("&ePeligon >> &a&l&nResource Link"));
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/peligon-economy.100259/"));
                    player.spigot().sendMessage(message);
                } else if (event.isLeftClick()) {
                    plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "pelecon");
                } else if (event.isRightClick()) {
                    TextComponent message = new TextComponent(Utils.chatColor("&ePeligon >> &a&l&nDocumentation Link"));
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/Darkw1nged/Peligon-Plugins/wiki/Peligon-Economy-:-Commands-and-Permissions"));
                    player.spigot().sendMessage(message);
                }
                break;
            case 16:
                // not released
                break;
            case 20:
                if (plugin.getServer().getPluginManager().getPlugin("PeligonEnhancedStorage") == null) {
                    TextComponent message = new TextComponent(Utils.chatColor("&ePeligon >> &a&l&nResource Link"));
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/peligon-enhancedstorage.103322/"));
                    player.spigot().sendMessage(message);
                } else if (event.isLeftClick()) {
                    plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "pelstorage");
                } else if (event.isRightClick()) {
                    TextComponent message = new TextComponent(Utils.chatColor("&ePeligon >> &a&l&nDocumentation Link"));
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/Darkw1nged/Peligon-Plugins/wiki/Peligon-EnhancedStorage-:-Commands-and-Permissions"));
                    player.spigot().sendMessage(message);
                }
                break;
            case 22:
                if (plugin.getServer().getPluginManager().getPlugin("PeligonLifeSteal") == null) {
                    TextComponent message = new TextComponent(Utils.chatColor("&ePeligon >> &a&l&nResource Link"));
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/peligon-lifesteal.100900/"));
                    player.spigot().sendMessage(message);
                } else if (event.isLeftClick()) {
                    plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "pellives");
                } else if (event.isRightClick()) {
                    TextComponent message = new TextComponent(Utils.chatColor("&ePeligon >> &a&l&nDocumentation Link"));
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/Darkw1nged/Peligon-Plugins/wiki/Peligon-LifeSteal-:-Commands-and-Permissions"));
                    player.spigot().sendMessage(message);
                }
                break;
            case 24:
                if (plugin.getServer().getPluginManager().getPlugin("PeligonPlaytime") == null) {
                    TextComponent message = new TextComponent(Utils.chatColor("&ePeligon >> &a&l&nResource Link"));
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/peligon-playtime.101707/"));
                    player.spigot().sendMessage(message);
                } else if (event.isLeftClick()) {
                    plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "pelplaytime");
                } else if (event.isRightClick()) {
                    TextComponent message = new TextComponent(Utils.chatColor("&ePeligon >> &a&l&nDocumentation Link"));
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/Darkw1nged/Peligon-Plugins/wiki/Peligon-Playtime-:-Commands-and-Permissions"));
                    player.spigot().sendMessage(message);
                }
                break;
            case 28:
                if (plugin.getServer().getPluginManager().getPlugin("PeligonPolls") == null) {
                    TextComponent message = new TextComponent(Utils.chatColor("&ePeligon >> &a&l&nResource Link"));
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/peligon-polls.100200/"));
                    player.spigot().sendMessage(message);
                } else if (event.isLeftClick()) {
                    plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "pelpoll");
                } else if (event.isRightClick()) {
                    TextComponent message = new TextComponent(Utils.chatColor("&ePeligon >> &a&l&nDocumentation Link"));
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/Darkw1nged/Peligon-Plugins/wiki/Peligon-Polls-:-Commands-and-Permissions"));
                    player.spigot().sendMessage(message);
                }
                break;
            case 30:
                // not released
                break;
            case 32:
                // not released
                break;
            case 34:
                // ignore
                break;
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack background = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = background.getItemMeta();
        meta.setDisplayName(Utils.chatColor("&7"));
        background.setItemMeta(meta);

        for (int i=0; i<inventory.getSize(); i++) {
            inventory.setItem(i, background);
        }

        inventory.setItem(10, PeligonPluginPlaceholders.peligonAuthentication());
        inventory.setItem(12, PeligonPluginPlaceholders.peligonCore());
        inventory.setItem(14, PeligonPluginPlaceholders.peligonEconomy());
        inventory.setItem(16, PeligonPluginPlaceholders.peligonEnchants());
        inventory.setItem(20, PeligonPluginPlaceholders.peligonEnhancedStorage());
        inventory.setItem(22, PeligonPluginPlaceholders.peligonLifeSteal());
        inventory.setItem(24, PeligonPluginPlaceholders.peligonPlaytime());
        inventory.setItem(28, PeligonPluginPlaceholders.peligonPolls());
        inventory.setItem(30, PeligonPluginPlaceholders.peligonStaff());
        inventory.setItem(32, PeligonPluginPlaceholders.peligonTeams());
        inventory.setItem(34, new ItemStack(Material.AIR));

        // TODO - Mini plugins:
        // TODO -   -> Mini Peligon AutoPickup ['Material.HOPPER']
        // TODO -   -> Mini Peligon AutoSmelt ['Material.FURNACE']
        // TODO -   -> Mini Peligon BlockedCommands ['Material.BARRIER']
        // TODO -   -> Mini Peligon ChatColor ['Material.RED_DYE']
        // TODO -   -> Mini Peligon ChatFilter ['Material.BARRIER']
        // TODO -   -> Mini Peligon ClearChat ['Material.TNT']
        // TODO -   -> Mini Peligon Disposal ['Material.LAVA_BUCKET']
        // TODO -   -> Mini Peligon Homes ['Material.RED_BED']
        // TODO -   -> Mini Peligon InstantRespawn ['Material.SPLASH_POTION']
        // TODO -   -> Mini Peligon JoinLeaveMessage ['Material.NAME_TAG']
        // TODO -   -> Mini Peligon ServerInformation ['Material.OAK_SIGN']
        // TODO -   -> Mini Peligon Autosell ['Material.GOLD_NUGGET']
        // TODO -   -> Mini Peligon ChestSell ['Material.GOLD_NUGGET']
        // TODO -   -> Mini Peligon BankNotes ['Material.PAPER']
        // TODO -   -> Mini Peligon EXPBottle ['Material.EXPERIENCE_BOTTLE']
        // TODO -   -> Mini Peligon Announcements ['Material.BOOK']
        // TODO -   -> Mini Peligon AutoCondense ['Material.IRON_BLOCK']
        // TODO -   -> Mini Peligon AutoMessage ['Material.BOOK']
        // TODO -   -> Mini Peligon AutoReplant ['Material.WHEAT_SEEDS']
        // TODO -   -> Mini Peligon DeathMessages ['Material.SKELETON_SKULL']
        // TODO -   -> Mini Peligon OmniTool ['Material.DIAMOND_AXE']
        // TODO -   -> Mini Peligon OnePlayerSleep ['Material.RED_BED']
        // TODO -   -> Mini Peligon Spawn ['Material.OAK_DOOR']
        // TODO -   -> Mini Peligon VeinMiner ['Material.IRON_PICKAXE']
        // TODO -   -> Mini Peligon Warps ['Material.ENDER_PEARL']
    }


}
