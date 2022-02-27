package net.peligon.PeligonChat.listener;

import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.peligon.PeligonChat.Main;
import net.peligon.PeligonChat.libaries.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class showItem implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (!plugin.getConfig().getBoolean("show-item.enabled", true)) return;

        Player player = event.getPlayer();

        if (plugin.getConfig().getBoolean("show-item.requires-permission", true)) {
            if (player.hasPermission("Peligon.Chat.showItem") || player.hasPermission("Peligon.Chat.*")) {
                if (player.getInventory().getItemInMainHand().getType() == Material.AIR) return;

                TextComponent itemName = new TextComponent(Utils.chatColor(
                        plugin.getConfig().getString("show-item.show-in-chat").replaceAll("%item_name%",
                                player.getInventory().getItemInMainHand().getItemMeta().getDisplayName())));
                itemName.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM));

                event.setMessage(event.getMessage().replaceAll("[item]", String.valueOf(itemName)));
            }
        }
    }

}
