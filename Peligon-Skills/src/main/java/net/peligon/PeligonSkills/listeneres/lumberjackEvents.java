package net.peligon.PeligonSkills.listeneres;

import net.peligon.PeligonSkills.Main;
import net.peligon.PeligonSkills.libaries.Utils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class lumberjackEvents implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (plugin.getConfig().getBoolean("Skills.lumberjack.enabled", true)) {
            if (plugin.getConfig().getStringList("Skills.lumberjack.disabled-worlds").contains(event.getBlock().getWorld().getName())) return;
            Player player = event.getPlayer();
            Block block = event.getBlock();

            switch (block.getType()) {
                case OAK_LEAVES:
                case OAK_LOG:
                case OAK_WOOD:
                case STRIPPED_OAK_LOG:
                case STRIPPED_OAK_WOOD:
                case BIRCH_LEAVES:
                case BIRCH_LOG:
                case BIRCH_WOOD:
                case STRIPPED_BIRCH_LOG:
                case STRIPPED_BIRCH_WOOD:
                case SPRUCE_LEAVES:
                case SPRUCE_LOG:
                case SPRUCE_WOOD:
                case STRIPPED_SPRUCE_LOG:
                case STRIPPED_SPRUCE_WOOD:
                case JUNGLE_LEAVES:
                case JUNGLE_LOG:
                case JUNGLE_WOOD:
                case STRIPPED_JUNGLE_LOG:
                case STRIPPED_JUNGLE_WOOD:
                case ACACIA_LEAVES:
                case ACACIA_LOG:
                case ACACIA_WOOD:
                case STRIPPED_ACACIA_LOG:
                case STRIPPED_ACACIA_WOOD:
                case DARK_OAK_LEAVES:
                case DARK_OAK_LOG:
                case DARK_OAK_WOOD:
                case STRIPPED_DARK_OAK_LOG:
                case STRIPPED_DARK_OAK_WOOD:
                case WARPED_WART_BLOCK:
                case WARPED_STEM:
                case WARPED_HYPHAE:
                case STRIPPED_WARPED_STEM:
                case STRIPPED_WARPED_HYPHAE:
                case NETHER_WART_BLOCK:
                case CRIMSON_STEM:
                case CRIMSON_HYPHAE:
                case STRIPPED_CRIMSON_STEM:
                case STRIPPED_CRIMSON_HYPHAE:
                    int max_level = plugin.getConfig().getInt("Skills.lumberjack.maximum-level");
                    int needed = Utils.neededExperience(plugin.Lumberjack.getLevel(player));
                    int minimum = plugin.getConfig().getInt("Skills.lumberjack.earnable-experience.minimum");
                    int maximum = plugin.getConfig().getInt("Skills.lumberjack.earnable-experience.maximum");
                    int amount = (int) (Math.floor(Math.random() * maximum) + minimum);
                    plugin.Lumberjack.addExperience(player, amount);

                    if (plugin.Lumberjack.getExperience(player) >= needed) {
                        if (max_level == -1) {
                            plugin.Lumberjack.removeExperience(player, needed);
                            plugin.Lumberjack.addLevel(player, 1);
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("level-up")));
                        } else if (plugin.Lumberjack.getLevel(player) != max_level) {
                            plugin.Lumberjack.removeExperience(player, needed);
                            plugin.Lumberjack.addLevel(player, 1);
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("level-up")));
                        }
                    }
            }
        }
    }

}
