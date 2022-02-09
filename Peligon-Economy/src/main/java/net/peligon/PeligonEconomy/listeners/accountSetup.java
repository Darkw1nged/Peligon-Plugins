package net.peligon.PeligonEconomy.listeners;

import net.peligon.PeligonEconomy.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class accountSetup implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void Setup(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!plugin.Economy.hasAccount(player)) {
            if (plugin.getConfig().getBoolean("Storage.banks", true)) {
                plugin.Economy.createAccount(player, plugin.getConfig().getDouble("Account-Setup.cash"), plugin.getConfig().getDouble("Account-Setup.bank"));
            } else {
                plugin.Economy.createAccount(player, plugin.getConfig().getDouble("Account-Setup.cash"));
            }
        }
    }


}
