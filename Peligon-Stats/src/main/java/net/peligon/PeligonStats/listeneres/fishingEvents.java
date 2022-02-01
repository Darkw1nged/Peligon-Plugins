package net.peligon.PeligonStats.listeneres;

import net.peligon.PeligonStats.Main;
import net.peligon.PeligonStats.libaries.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class fishingEvents implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onBreak(PlayerFishEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType().equals(Material.FISHING_ROD)) {
            int max_level = plugin.getConfig().getInt("Settings.fishing.max-level");
            int needed = Utils.neededExperience(plugin.Fishing.getLevel(player));
            int minimum = plugin.getConfig().getInt("Settings.fishing.minimum-xp");
            int maximum = plugin.getConfig().getInt("Settings.fishing.maximum-xp");
            int amount = (int) (Math.floor(Math.random() * maximum) + minimum);

            plugin.Fishing.addExperience(player, amount);

            if (plugin.Fishing.getExperience(player) >= needed) {
                if (max_level == 0) {
                    plugin.Fishing.removeExperience(player, needed);
                    plugin.Fishing.addLevel(player, 1);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("level-up")));
                } else if (plugin.Fishing.getLevel(player) != max_level) {
                    plugin.Fishing.removeExperience(player, needed);
                    plugin.Fishing.addLevel(player, 1);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("level-up")));
                }
            }
        }
    }

}
