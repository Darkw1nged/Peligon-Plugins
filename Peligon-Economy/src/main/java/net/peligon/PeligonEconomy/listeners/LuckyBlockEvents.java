package net.peligon.PeligonEconomy.listeners;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class LuckyBlockEvents implements Listener {

    private final Main plugin = Main.getInstance;
    private Map<UUID, Map<Location, Block>> foundBlocks = new HashMap<>();

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        org.bukkit.block.Block block = event.getBlock();
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;

        if (plugin.getConfig().getBoolean("Lucky-Blocks.enabled")) {
            if (plugin.getConfig().getStringList("Lucky-Blocks.disabled-worlds").contains(block.getWorld().getName())) return;
            if (plugin.getConfig().getStringList("Lucky-Blocks.disabled-plants").contains(block.getType().name().toLowerCase())) return;
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
                    for (String key : plugin.getConfig().getConfigurationSection("Lucky-Blocks.blocks").getKeys(false)) {
                        if (random >= plugin.getConfig().getInt("Lucky-Blocks.blocks." + key + ".chance.minimum") &&
                                random <= plugin.getConfig().getInt("Lucky-Blocks.blocks." + key + ".chance.maximum")) {

                            event.getBlock().setType(Material.getMaterial(plugin.getConfig().getString("Lucky-Blocks.blocks." + key + ".material")));

                            Map<Location, Block> cache = new HashMap<>();
                            cache.put(block.getLocation(), event.getBlock());

                            foundBlocks.put(event.getPlayer().getUniqueId(), cache);
                            break;
                        }
                    }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        Block block = event.getClickedBlock();
        if (block == null) return;

        if (plugin.getConfig().getBoolean("Lucky-Blocks.enabled")) {
            if (plugin.getConfig().getStringList("Lucky-Blocks.disabled-worlds").contains(event.getPlayer().getWorld().getName())) return;
            if (plugin.getConfig().getStringList("Lucky-Blocks.disabled-plants").contains(block.getType().name().toLowerCase())) return;

            if (foundBlocks.containsKey(event.getPlayer().getUniqueId())) {
                if (foundBlocks.get(event.getPlayer().getUniqueId()).containsKey(block.getLocation())) {
                    event.setCancelled(true);

                    String found = "";
                    for (String key : plugin.getConfig().getStringList("Lucky-Blocks.blocks")) {
                        if (plugin.getConfig().getString("Lucky-Blocks.blocks." + key + ".type").equalsIgnoreCase(block.getType().name())) {
                            found = key;
                            break;
                        }
                    }

                    for (String command : plugin.getConfig().getStringList("Lucky-Blocks.blocks." + found + ".commands")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", event.getPlayer().getName()));
                    }
                    block.setType(Material.AIR);
                }
            }
        }
    }

}
