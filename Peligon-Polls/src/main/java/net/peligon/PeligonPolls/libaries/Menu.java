package net.peligon.PeligonPolls.libaries;

import net.peligon.PeligonPolls.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.InventoryHolder;

public interface Menu extends InventoryHolder {

    default void onClick(Main plugin, Player player, int slot, ClickType type) {}

    default void onOpen(Main plugin, Player player) {}

    default void onClose(Main plugin, Player player) {}

}