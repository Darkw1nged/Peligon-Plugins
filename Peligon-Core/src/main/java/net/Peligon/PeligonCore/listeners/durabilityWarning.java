package net.Peligon.PeligonCore.listeners;

import net.Peligon.PeligonCore.Main;
import net.Peligon.PeligonCore.libaries.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class durabilityWarning implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void toolDamage(PlayerItemDamageEvent event) {
        for (String percent : plugin.getConfig().getStringList("Warnings.Tool-Durability.percentage")) {
            if (event.getItem().getDurability() / event.getItem().getType().getMaxDurability() * 100 <= Integer.parseInt(percent)) {
                event.getPlayer().sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("durability-warning").replaceAll("%percent%", percent)));
                if (plugin.getConfig().getBoolean("Warnings.Tool-Durability.sound.enabled")) {
                    event.getPlayer().playSound(event.getPlayer().getLocation(), plugin.getConfig().getString("Warnings.Tool-Durability.sound.id"),
                            plugin.getConfig().getInt("Warnings.Tool-Durability.sound.volume"), plugin.getConfig().getInt("Warnings.Tool-Durability.sound.pitch"));
                }
            }
        }
    }

}
