package net.peligon.PeligonEnchants.Events;

import net.peligon.PeligonEnchants.Main;
import net.peligon.PeligonEnchants.libaries.CustomConfig;
import net.peligon.PeligonEnchants.libaries.CustomEnchants;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;


public class encAutoSell implements Listener {

    public static Main plugin = Main.getInstance;

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack hand = player.getInventory().getItemInMainHand();

        if (plugin.getEconomy() == null) return;
        if (hand.getType() == Material.AIR) return;
        if (!hand.hasItemMeta()) return;
        if (!hand.getItemMeta().hasEnchant(CustomEnchants.AUTOSELL)) return;
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;
        if (event.getBlock().getState() instanceof Container) return;

        Plugin targetPlugin = null;
        if (Bukkit.getServer().getPluginManager().getPlugin("Essentials") != null) {
            targetPlugin = Bukkit.getServer().getPluginManager().getPlugin("Essentials");
        } else if (Bukkit.getServer().getPluginManager().getPlugin("PeligonEconomy") != null) {
            targetPlugin = Bukkit.getServer().getPluginManager().getPlugin("PeligonEconomy");
        }

        if (targetPlugin == null) return;
        CustomConfig config = new CustomConfig(targetPlugin, "worth", true);

        double amount = 0;
        for (int i=0; i<player.getInventory().getSize(); i++) {
            amount = getAmount(player, amount, i, config.getConfig());
        }

        plugin.getEconomy().depositPlayer(player, amount);
    }

    private double getAmount(Player player, double amount, int i, YamlConfiguration configuration) {
        try {
            ItemStack item = player.getInventory().getItem(i);
            if (configuration.contains("worth." + item.getType().name().toUpperCase())) {
                amount += configuration.getDouble("worth." + item.getType().name().toUpperCase()) * item.getAmount();
                item.setAmount(0);
            }
        } catch (Exception ignored) { }
        return amount;
    }

}
