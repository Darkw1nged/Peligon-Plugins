package net.peligon.LifeSteal.listeners;

import net.peligon.LifeSteal.Main;
import net.peligon.LifeSteal.libaries.Utils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class combatTag implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (plugin.getConfig().getStringList("Events").contains("combat-log")) {
                if (event.getDamager() instanceof Player) {
                    if (!Utils.combatTag.containsKey(player.getUniqueId())) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("enter-combat").replaceAll("%player%", event.getDamager().getName())));
                    }
                    Utils.combatTag.put(player.getUniqueId(), System.currentTimeMillis());

                    if (!Utils.combatTag.containsKey(event.getDamager().getUniqueId())) {
                        event.getDamager().sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("enter-combat").replaceAll("%player%", player.getName())));
                    }
                    Utils.combatTag.put(event.getDamager().getUniqueId(), System.currentTimeMillis());
                }
            }
        }
    }

    @EventHandler
    public void processCommand(PlayerCommandPreprocessEvent event) {
        if (plugin.getConfig().getStringList("Events").contains("combat-log")) {
            if (Utils.combatTag.containsKey(event.getPlayer().getUniqueId())) {
                for (String command : plugin.getConfig().getStringList("combat-tag.blocked-commands")) {
                    if (event.getMessage().equalsIgnoreCase(command)) {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("cannot-use-command")));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (plugin.getConfig().getStringList("Events").contains("combat-log")) {
            if (Utils.combatTag.containsKey(player.getUniqueId())) {
                player.damage(10000);
                Utils.combatTag.remove(player.getUniqueId());
            }
        }
    }

}
