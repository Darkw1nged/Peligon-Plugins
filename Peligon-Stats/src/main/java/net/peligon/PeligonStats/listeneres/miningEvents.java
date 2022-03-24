package net.peligon.PeligonStats.listeneres;

import net.peligon.PeligonStats.Main;
import net.peligon.PeligonStats.libaries.Utils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class miningEvents implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (plugin.getConfig().getBoolean("Skills.mining.enabled", true)) {
            if (plugin.getConfig().getStringList("Skills.mining.disabled-worlds").contains(event.getBlock().getWorld().getName())) return;
            Player player = event.getPlayer();
            ItemStack item = player.getInventory().getItemInMainHand();
            Block block = event.getBlock();

            if (item.getType().name().toLowerCase().endsWith("_pickaxe")) {
                switch (block.getType()) {
                    case COAL_ORE:
                    case DEEPSLATE_COAL_ORE:
                    case COPPER_ORE:
                    case DEEPSLATE_COPPER_ORE:
                    case IRON_ORE:
                    case DEEPSLATE_IRON_ORE:
                    case LAPIS_ORE:
                    case DEEPSLATE_LAPIS_ORE:
                    case REDSTONE_ORE:
                    case DEEPSLATE_REDSTONE_ORE:
                    case GOLD_ORE:
                    case DEEPSLATE_GOLD_ORE:
                    case DIAMOND_ORE:
                    case DEEPSLATE_DIAMOND_ORE:
                    case EMERALD_ORE:
                    case DEEPSLATE_EMERALD_ORE:
                    case NETHER_GOLD_ORE:
                    case NETHER_QUARTZ_ORE:
                    case ANCIENT_DEBRIS:
                        int max_level = plugin.getConfig().getInt("Skills.mining.maximum-level");
                        int needed = Utils.neededExperience(plugin.Mining.getLevel(player));
                        int minimum = plugin.getConfig().getInt("Skills.mining.earnable-experience.minimum");
                        int maximum = plugin.getConfig().getInt("Skills.mining.earnable-experience.maximum");
                        int amount = (int) (Math.floor(Math.random() * maximum) + minimum);
                        plugin.Mining.addExperience(player, amount);

                        if (plugin.Mining.getExperience(player) >= needed) {
                            if (max_level == -1) {
                                plugin.Mining.removeExperience(player, needed);
                                plugin.Mining.addLevel(player, 1);
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("level-up")));
                            } else if (plugin.Mining.getLevel(player) != max_level) {
                                plugin.Mining.removeExperience(player, needed);
                                plugin.Mining.addLevel(player, 1);
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("level-up")));
                            }
                        }
                }
            }
        }
    }
}
