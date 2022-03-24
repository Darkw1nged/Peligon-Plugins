package net.peligon.PeligonSkills.listeneres;

import net.peligon.PeligonSkills.Main;
import net.peligon.PeligonSkills.libaries.Utils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class excavationEvents implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (plugin.getConfig().getBoolean("Skills.excavation.enabled", true)) {
            if (plugin.getConfig().getStringList("Skills.excavation.disabled-worlds").contains(event.getBlock().getWorld().getName())) return;
            Player player = event.getPlayer();
            ItemStack item = player.getInventory().getItemInMainHand();
            Block block = event.getBlock();

            if (item.getType().name().toLowerCase().endsWith("_shovel")) {
                switch (block.getType()) {
                    case DIRT:
                    case GRASS_BLOCK:
                    case FARMLAND:
                    case COARSE_DIRT:
                    case DIRT_PATH:
                    case ROOTED_DIRT:
                    case SAND:
                    case GRAVEL:
                    case SOUL_SAND:
                    case SOUL_SOIL:
                        int max_level = plugin.getConfig().getInt("Skills.excavation.maximum-level");
                        int needed = Utils.neededExperience(plugin.Excavation.getLevel(player));
                        int minimum = plugin.getConfig().getInt("Skills.excavation.earnable-experience.minimum");
                        int maximum = plugin.getConfig().getInt("Skills.excavation.earnable-experience.maximum");
                        int amount = (int) (Math.floor(Math.random() * maximum) + minimum);
                        plugin.Excavation.addExperience(player, amount);

                        if (plugin.Excavation.getExperience(player) >= needed) {
                            if (max_level == -1) {
                                plugin.Excavation.removeExperience(player, needed);
                                plugin.Excavation.addLevel(player, 1);
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("level-up")));
                            } else if (plugin.Excavation.getLevel(player) != max_level) {
                                plugin.Excavation.removeExperience(player, needed);
                                plugin.Excavation.addLevel(player, 1);
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("level-up")));
                            }
                        }
                }
            }
        }
    }

}
