package net.peligon.LifeSteal.listeners;

import net.peligon.LifeSteal.Main;
import net.peligon.LifeSteal.libaries.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class deathPenalty implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (plugin.getEconomy() == null) return;
        if (plugin.getConfig().getStringList("Events").contains("death-penalty")) {
           if (plugin.getConfig().getBoolean("Death-Penalty.only-players", false)) {
               if (player.getKiller() != null) {
                   if (plugin.getEconomy().has(player, plugin.getConfig().getDouble("Death-Penalty.amount", 100.0))) {
                       plugin.getEconomy().withdrawPlayer(player, plugin.getConfig().getDouble("Death-Penalty.amount", 100.0));
                       player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                               plugin.fileMessage.getConfig().getString("death-penalty"), plugin.getConfig().getDouble("Death-Penalty.amount", 100.0)));
                   }
               }
           } else {
               if (plugin.getEconomy().has(player, plugin.getConfig().getDouble("Death-Penalty.amount", 100.0))) {
                   plugin.getEconomy().withdrawPlayer(player, plugin.getConfig().getDouble("Death-Penalty.amount", 100.0));
                   player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                           plugin.fileMessage.getConfig().getString("death-penalty"), plugin.getConfig().getDouble("Death-Penalty.amount", 100.0)));
               }
           }
        }
    }

}
