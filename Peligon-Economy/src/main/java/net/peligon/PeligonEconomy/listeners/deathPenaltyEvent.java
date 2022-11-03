package net.peligon.PeligonEconomy.listeners;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class deathPenaltyEvent implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (plugin.getConfig().getBoolean("Death-Penalty.enabled", true)) {
            double amountToRemove = plugin.getConfig().getDouble("Death-Penalty.amount", 100);
            if (plugin.Economy.hasEnoughCash(player, amountToRemove)) {
                plugin.Economy.removeAccount(player, amountToRemove);
                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                        plugin.languageFile.getConfig().getString("death-penalty-issued"), amountToRemove));
            } else if (plugin.Economy.hasEnoughBank(player, amountToRemove)) {
                plugin.Economy.removeBankAccount(player, amountToRemove);
                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                        plugin.languageFile.getConfig().getString("death-penalty-issued"), amountToRemove));
            }
        }
    }

}
