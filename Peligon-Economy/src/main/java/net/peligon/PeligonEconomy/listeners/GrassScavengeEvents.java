package net.peligon.PeligonEconomy.listeners;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;

public class GrassScavengeEvents implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if (plugin.getConfig().getBoolean("Grass-Scavenge.enabled", false)) {
            if (plugin.getConfig().getStringList("Grass-Scavenge.disabled-worlds").contains(block.getWorld().getName())) return;
            if (plugin.getConfig().getStringList("Grass-Scavenge.disabled-plants").contains(block.getType().name().toLowerCase())) return;
            int random = new Random().nextInt(100);

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
                    for (String key : plugin.getConfig().getConfigurationSection("Grass-Scavenge.money").getKeys(false)) {
                        if (random >= plugin.getConfig().getInt("Grass-Scavenge.money." + key + ".chance.minimum") &&
                                random <= plugin.getConfig().getInt("Grass-Scavenge.money." + key + ".chance.maximum")) {

                            String name = "&2$%amount%".replaceAll("%amount%", "" + plugin.getConfig().getInt("Grass-Scavenge.money." + key + ".amount"));
                            Utils.moveUpHologram(name, block.getLocation(), 2);
                            break;
                        }
                    }
            }
        }
    }

}
