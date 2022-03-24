package net.peligon.PeligonStats.listeneres;

import net.peligon.PeligonStats.Main;
import net.peligon.PeligonStats.libaries.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceExtractEvent;

public class smeltingEvents implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onSmelt(FurnaceExtractEvent event) {
        if (plugin.getConfig().getBoolean("Skills.smelting.enabled", true)) {
            if (plugin.getConfig().getStringList("Skills.smelting.disabled-worlds").contains(event.getBlock().getWorld().getName())) return;
            Player player = event.getPlayer();

            int max_level = plugin.getConfig().getInt("Skills.smelting.maximum-level");
            int needed = Utils.neededExperience(plugin.Smelting.getLevel(player));
            int minimum = plugin.getConfig().getInt("Skills.smelting.earnable-experience.minimum");
            int maximum = plugin.getConfig().getInt("Skills.smelting.earnable-experience.maximum");
            int amount = (int) (Math.floor(Math.random() * maximum) + minimum);

            plugin.Smelting.addExperience(player, amount);

            if (plugin.Smelting.getExperience(player) >= needed) {
                if (max_level == -1) {
                    plugin.Smelting.removeExperience(player, needed);
                    plugin.Smelting.addLevel(player, 1);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("level-up")));
                } else if (plugin.Smelting.getLevel(player) != max_level) {
                    plugin.Smelting.removeExperience(player, needed);
                    plugin.Smelting.addLevel(player, 1);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("level-up")));
                }
            }
        }

    }

}
