package net.peligon.LifeSteal.listeners;

import net.peligon.LifeSteal.Main;
import net.peligon.LifeSteal.libaries.Utils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class lifeUpdate implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onDeath(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.getHealth() - event.getDamage() <= 0) {
                if (event instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;

                    if (plugin.getConfig().getStringList("Events").contains("players-only")) {
                        if (e.getDamager() instanceof Player && plugin.bounties.hasData((Player) e.getDamager())) {
                            Player damager = (Player) e.getDamager();
                            damager.getAttribute(Attribute.GENERIC_MAX_HEALTH)
                                    .setBaseValue(damager.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + 2);
                        }
                        player.getAttribute(Attribute.GENERIC_MAX_HEALTH)
                                .setBaseValue(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() - 2);
                        return;
                    }
                    if (!plugin.getConfig().getStringList("blacklisted-mobs").contains(e.getDamager().getType().name().toUpperCase())) {
                        if (player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() > 0) {
                            player.getAttribute(Attribute.GENERIC_MAX_HEALTH)
                                    .setBaseValue(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() - 2);
                        }
                    }
                    return;
                }
                if (plugin.getConfig().getStringList("Events").contains("players-only")) return;
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH)
                        .setBaseValue(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() - 2);
            }
        }
    }

    @EventHandler
    public void noLives(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() <= 0) {
            if (plugin.getConfig().getBoolean("After-Life.ban-user.enabled", true)) {
                BanList banList = Bukkit.getBanList(BanList.Type.NAME);
                banList.addBan(player.getName(), plugin.getConfig().getString("After-Life.ban-user.message", "You have died but there is still hope. Try and get someone to revive you."), null, null);
            }
            if (plugin.getConfig().getBoolean("After-Life.teleport-user.enabled", true)) {
                World world = Bukkit.getWorld(plugin.getConfig().getString("After-Life.teleport-user.world", "world"));
                player.teleport(world.getSpawnLocation());
                player.sendMessage(Utils.chatColor(plugin.getConfig().getString("After-Life.teleport-user.message", "You have died but there is still hope. Try and get someone to revive you.")));
            }
        }
    }

    @EventHandler
    public void executeCommands(PlayerDeathEvent event) {
        Player player = event.getEntity();
        double lives = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();

        if (plugin.getConfig().getStringList("Events").contains("commands-on-death")) {
            if (plugin.getConfig().getInt("Commands-on-Death.execute-at-lives") == lives) {
                for (String command : plugin.getConfig().getStringList("Commands-on-Death.commands")) {
                    command = command.replaceAll("%player%", player.getName())
                            .replaceAll("%lives%", "" + lives);
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), Utils.chatColor(command));
                }
            }

        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        double lives = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();


        if (plugin.getConfig().getBoolean("Player-Respawn.title.send-title", true)) {
            if (lives <= 0) {
                player.sendTitle(Utils.chatColor(plugin.getConfig().getString("Player-Respawn.title.no-lives-left.title")
                                .replaceAll("%lives%", "" + lives)
                                .replaceAll("%player%", player.getName())),
                        Utils.chatColor(plugin.getConfig().getString("Player-Respawn.title.no-lives-left.subtitle")
                                .replaceAll("%lives%", "" + lives)
                                .replaceAll("%player%", player.getName())),
                        plugin.getConfig().getInt("Player-Respawn.title.fade-in"),
                        plugin.getConfig().getInt("Player-Respawn.title.stay"),
                        plugin.getConfig().getInt("Player-Respawn.title.out"));
                return;
            }
            player.sendTitle(Utils.chatColor(plugin.getConfig().getString("Player-Respawn.title.has-lives-left.title")
                            .replaceAll("%lives%", "" + lives)
                            .replaceAll("%player%", player.getName())),
                    Utils.chatColor(plugin.getConfig().getString("Player-Respawn.title.has-lives-left.subtitle")
                            .replaceAll("%lives%", "" + lives)
                            .replaceAll("%player%", player.getName())),
                    plugin.getConfig().getInt("Player-Respawn.title.fade-in"),
                    plugin.getConfig().getInt("Player-Respawn.title.stay"),
                    plugin.getConfig().getInt("Player-Respawn.title.out"));
        }
    }

}
