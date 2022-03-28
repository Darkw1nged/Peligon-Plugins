package net.peligon.PeligonPrison.menu;

import net.peligon.PeligonPrison.Main;
import net.peligon.PeligonPrison.libaries.Utils;
import net.peligon.PeligonPrison.struts.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

public class menuBackpack implements Menu {

    private final Main plugin = Main.getInstance;
    private final Player player;
    private final Inventory inventory;

    public menuBackpack(Player player) {
        this.player = player;

        int size = 9;
        if (player.hasPermission("Peligon.Prison.Backpack.Size.54")) {
            size = 54;
        } else if (player.hasPermission("Peligon.Prison.Backpack.Size.45")) {
            size = 45;
        } else if (player.hasPermission("Peligon.Prison.Backpack.Size.36")) {
            size = 36;
        } else if (player.hasPermission("Peligon.Prison.Backpack.Size.27")) {
            size = 27;
        } else if (player.hasPermission("Peligon.Prison.Backpack.Size.18")) {
            size = 18;
        }

        this.inventory = Bukkit.createInventory(this, size,
                Utils.chatColor(plugin.getConfig().getString("Backpack.Inventory.title")));
    }

    public void onClick(Main plugin, Player player, int slot, ClickType type) {
    }

    public void onOpen(Main plugin, Player player) {
        this.inventory.setContents(plugin.backpackManager.getBackpack(player));
    }

    public void onClose(Main plugin, Player player) {
        if (!plugin.backpackManager.hasData(player)) {
            plugin.backpackManager.createBackpack(player, this.inventory.getContents());
            return;
        }
        plugin.backpackManager.setBackpack(player, this.inventory.getContents());
    }

    public Inventory getInventory() {
        return this.inventory;
    }
}