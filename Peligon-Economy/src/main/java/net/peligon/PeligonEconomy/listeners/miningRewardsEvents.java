package net.peligon.PeligonEconomy.listeners;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;


public class miningRewardsEvents implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if (plugin.getConfig().getBoolean("Mining-Rewards.enabled", false)) {
            if (plugin.getConfig().getStringList("Mining-Rewards.disabled-worlds").contains(block.getWorld().getName())) return;
            if (plugin.getConfig().getStringList("Mining-Rewards.disabled-plants").contains(block.getType().name().toLowerCase())) return;
            int random = new Random().nextInt(100);

            switch (block.getType()) {
                case STONE:
                case MOSSY_COBBLESTONE:
                case COBBLESTONE:
                case GRANITE:
                case ANDESITE:
                case DIORITE:
                case SANDSTONE:
                case RED_SANDSTONE:
                case PRISMARINE:
                case PRISMARINE_BRICKS:
                case DARK_PRISMARINE:
                case STONE_BRICKS:
                case MOSSY_STONE_BRICKS:
                case CRACKED_STONE_BRICKS:
                case COAL_ORE:
                case COPPER_ORE:
                case DEEPSLATE_COAL_ORE:
                case DEEPSLATE_COPPER_ORE:
                case DEEPSLATE_DIAMOND_ORE:
                case DEEPSLATE_EMERALD_ORE:
                case DEEPSLATE_GOLD_ORE:
                case DEEPSLATE_IRON_ORE:
                case DEEPSLATE_LAPIS_ORE:
                case DEEPSLATE_REDSTONE_ORE:
                case DIAMOND_ORE:
                case EMERALD_ORE:
                case GOLD_ORE:
                case IRON_ORE:
                case LAPIS_ORE:
                case NETHER_GOLD_ORE:
                case NETHER_QUARTZ_ORE:
                case REDSTONE_ORE:
                case ANCIENT_DEBRIS:
                    for (String key : plugin.getConfig().getConfigurationSection("Mining-Rewards.money").getKeys(false)) {
                        if (random >= plugin.getConfig().getInt("Mining-Rewards.money." + key + ".chance.minimum") &&
                                random <= plugin.getConfig().getInt("Mining-Rewards.money." + key + ".chance.maximum")) {

                            String name = "&2$%amount%".replaceAll("%amount%", "" + plugin.getConfig().getInt("Mining-Rewards.money." + key + ".amount"));
                            Utils.moveUpHologram(name, block.getLocation(), 2);
                            break;
                        }
                    }
            }
        }
    }

}
