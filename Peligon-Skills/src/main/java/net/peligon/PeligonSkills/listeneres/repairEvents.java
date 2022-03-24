package net.peligon.PeligonSkills.listeneres;

import net.peligon.PeligonSkills.Main;
import net.peligon.PeligonSkills.libaries.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;

public class repairEvents implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onRepair(PrepareAnvilEvent event) {
        if (plugin.getConfig().getBoolean("Skills.repair.enabled", true)) {
            Player player = (Player) event.getInventory().getHolder();
            if (player == null) return;
            if (plugin.getConfig().getStringList("Skills.repair.disabled-worlds").contains(player.getWorld().getName())) return;

            int max_level = plugin.getConfig().getInt("Skills.repair.maximum-level");
            int needed = Utils.neededExperience(plugin.Repair.getLevel(player));
            int minimum = plugin.getConfig().getInt("Skills.repair.earnable-experience.minimum");
            int maximum = plugin.getConfig().getInt("Skills.repair.earnable-experience.maximum");
            int amount = (int) (Math.floor(Math.random() * maximum) + minimum);
            plugin.Repair.addExperience(player, amount);

            if (plugin.Repair.getExperience(player) >= needed) {
                if (max_level == -1) {
                    plugin.Repair.removeExperience(player, needed);
                    plugin.Repair.addLevel(player, 1);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("level-up")));
                } else if (plugin.Repair.getLevel(player) != max_level) {
                    plugin.Repair.removeExperience(player, needed);
                    plugin.Repair.addLevel(player, 1);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("level-up")));
                }
            }
        }

    }


}
