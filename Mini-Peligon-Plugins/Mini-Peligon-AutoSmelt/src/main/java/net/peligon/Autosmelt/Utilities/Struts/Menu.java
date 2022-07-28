package net.peligon.Autosmelt.Utilities.Struts;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;

public interface Menu extends InventoryHolder {

    default void onClick(Plugin plugin, Player player, int slot, ClickType type) {}

    default void onOpen(Plugin plugin, Player player) {}

    default void onClose(Plugin plugin, Player player) {}

}
