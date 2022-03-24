package net.peligon.PeligonStats.listeneres;

import net.peligon.PeligonStats.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class accountSetup implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!plugin.Acrobatics.hasAccount(player)) {
            plugin.Acrobatics.createAccount(player, plugin.getConfig().getInt("Starting-Level"), plugin.getConfig().getInt("Starting-Experience"));
        }
        if (!plugin.Alchemy.hasAccount(player)) {
            plugin.Alchemy.createAccount(player, plugin.getConfig().getInt("Starting-Level"), plugin.getConfig().getInt("Starting-Experience"));
        }
        if (!plugin.Excavation.hasAccount(player)) {
            plugin.Excavation.createAccount(player, plugin.getConfig().getInt("Starting-Level"), plugin.getConfig().getInt("Starting-Experience"));
        }
        if (!plugin.Fishing.hasAccount(player)) {
            plugin.Fishing.createAccount(player, plugin.getConfig().getInt("Starting-Level"), plugin.getConfig().getInt("Starting-Experience"));
        }
        if (!plugin.Herbalism.hasAccount(player)) {
            plugin.Herbalism.createAccount(player, plugin.getConfig().getInt("Starting-Level"), plugin.getConfig().getInt("Starting-Experience"));
        }
        if (!plugin.Lumberjack.hasAccount(player)) {
            plugin.Lumberjack.createAccount(player, plugin.getConfig().getInt("Starting-Level"), plugin.getConfig().getInt("Starting-Experience"));
        }
        if (!plugin.Mining.hasAccount(player)) {
            plugin.Mining.createAccount(player, plugin.getConfig().getInt("Starting-Level"), plugin.getConfig().getInt("Starting-Experience"));
        }
        if (!plugin.Repair.hasAccount(player)) {
            plugin.Repair.createAccount(player, plugin.getConfig().getInt("Starting-Level"), plugin.getConfig().getInt("Starting-Experience"));
        }
        if (!plugin.Smelting.hasAccount(player)) {
            plugin.Smelting.createAccount(player, plugin.getConfig().getInt("Starting-Level"), plugin.getConfig().getInt("Starting-Experience"));
        }
    }

}
