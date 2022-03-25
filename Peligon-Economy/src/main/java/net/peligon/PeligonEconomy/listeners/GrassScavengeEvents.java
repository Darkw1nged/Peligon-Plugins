package net.peligon.PeligonEconomy.listeners;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
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
        if (plugin.getConfig().getBoolean("Grass-Scavenge.enabled", true)) {
            if (plugin.getConfig().getStringList("Grass-Scavenge.disabled-worlds").contains(block.getWorld().getName())) return;
            if (plugin.getConfig().getStringList("Grass-Scavenge.disabled-plants").contains(block.getType().name().toLowerCase())) return;
            int random = new Random().nextInt(100);

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
