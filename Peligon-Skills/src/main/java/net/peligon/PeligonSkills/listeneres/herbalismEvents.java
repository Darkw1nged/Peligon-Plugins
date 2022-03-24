package net.peligon.PeligonSkills.listeneres;

import net.peligon.PeligonSkills.Main;
import net.peligon.PeligonSkills.libaries.Utils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class herbalismEvents implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (plugin.getConfig().getBoolean("Skills.herbalism.enabled", true)) {
            if (plugin.getConfig().getStringList("Skills.herbalism.disabled-worlds").contains(event.getBlock().getWorld().getName())) return;
            Player player = event.getPlayer();
            Block block = event.getBlock();

            switch (block.getType()) {
                case AZALEA:
                case BAMBOO:
                case BEETROOTS:
                case BIG_DRIPLEAF:
                case CACTUS:
                case CARROTS:
                case CAVE_VINES:
                case CHORUS_FLOWER:
                case CHORUS_PLANT:
                case COCOA:
                case DEAD_BUSH:
                case FERN:
                case DANDELION:
                case POPPY:
                case BLUE_ORCHID:
                case ALLIUM:
                case AZURE_BLUET:
                case ORANGE_TULIP:
                case PINK_TULIP:
                case RED_TULIP:
                case WHITE_TULIP:
                case OXEYE_DAISY:
                case CORNFLOWER:
                case LILY_OF_THE_VALLEY:
                case WITHER_ROSE:
                case SUNFLOWER:
                case LILAC:
                case ROSE_BUSH:
                case PEONY:
                case GRASS:
                case TALL_GRASS:
                case HANGING_ROOTS:
                case LILY_PAD:
                case MELON:
                case MELON_STEM:
                case MOSS_BLOCK:
                case MOSS_CARPET:
                case POTATOES:
                case PUMPKIN:
                case PUMPKIN_STEM:
                case SEAGRASS:
                case SMALL_DRIPLEAF:
                case SPORE_BLOSSOM:
                case SUGAR_CANE:
                case SWEET_BERRY_BUSH:
                case WHEAT:
                case BEETROOT:
                case CARROT:
                case POTATO:
                case BAMBOO_SAPLING:
                case COCOA_BEANS:
                case SWEET_BERRIES:
                case MUSHROOM_STEM:
                case BROWN_MUSHROOM:
                case RED_MUSHROOM:
                case BROWN_MUSHROOM_BLOCK:
                case RED_MUSHROOM_BLOCK:
                case KELP:
                case KELP_PLANT:
                case SEA_PICKLE:
                case NETHER_WART:
                case CRIMSON_FUNGUS:
                case WARPED_FUNGUS:
                case GLOW_BERRIES:
                    int max_level = plugin.getConfig().getInt("Skills.herbalism.maximum-level");
                    int needed = Utils.neededExperience(plugin.Herbalism.getLevel(player));
                    int minimum = plugin.getConfig().getInt("Skills.herbalism.earnable-experience.minimum");
                    int maximum = plugin.getConfig().getInt("Skills.herbalism.earnable-experience.maximum");
                    int amount = (int) (Math.floor(Math.random() * maximum) + minimum);
                    plugin.Herbalism.addExperience(player, amount);

                    if (plugin.Herbalism.getExperience(player) >= needed) {
                        if (max_level == -1) {
                            plugin.Herbalism.removeExperience(player, needed);
                            plugin.Herbalism.addLevel(player, 1);
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("level-up")));
                        } else if (plugin.Herbalism.getLevel(player) != max_level) {
                            plugin.Herbalism.removeExperience(player, needed);
                            plugin.Herbalism.addLevel(player, 1);
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("level-up")));
                        }
                    }
            }
        }
    }
}
