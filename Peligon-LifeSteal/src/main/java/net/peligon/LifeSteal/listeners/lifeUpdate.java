package net.peligon.LifeSteal.listeners;

import net.peligon.LifeSteal.Main;
import net.peligon.LifeSteal.libaries.Utils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class lifeUpdate implements Listener {

    private final Main plugin = Main.getInstance;
    private boolean execute = false;

    @EventHandler
    public void onDeath(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (!plugin.lives.hasData(player)) return;

            if (player.getHealth() - event.getDamage() <= 0) {
                if (event instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;

                    if (plugin.getConfig().getBoolean("Lose-Lives.players-only")) {
                        if (e.getDamager() instanceof Player && plugin.lives.hasData((Player) e.getDamager())) {
                            Player damager = (Player) e.getDamager();
                            plugin.lives.addLives(damager, 1);
                        }
                        plugin.lives.removeLives(player, 1);
                        return;
                    }
                    if (!plugin.getConfig().getStringList("Lose-Lives.blacklisted-mobs").contains(e.getDamager().getType().name().toUpperCase())) {
                        if (plugin.lives.getLives(player) > 0)
                            plugin.lives.removeLives(player, 1);
                    }
                    return;
                }
                if (plugin.getConfig().getBoolean("Lose-Lives.players-only")) return;
                plugin.lives.removeLives(player, 1);
            }
        }
    }

    @EventHandler
    public void noLives(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (plugin.lives.getLives(player) <= 0) {
            if (plugin.getConfig().getBoolean("Out-of-Lives.ban-on-death", false)) {
                BanList bans = Bukkit.getBanList(BanList.Type.NAME);
                bans.addBan(player.getName(), Utils.chatColor(plugin.fileMessage.getConfig().getString("ban-message")), null, null);
                player.kickPlayer(Utils.chatColor(plugin.fileMessage.getConfig().getString("ban-message")));
            }
            if (plugin.getConfig().getBoolean("Out-of-Lives.kick-on-death", false)) {
                player.kickPlayer(Utils.chatColor(plugin.fileMessage.getConfig().getString("No-Lives.kick-message")));
            }
            if (plugin.getConfig().getBoolean("Out-of-Lives.change-gamemode-on-death", false)) {
                player.setGameMode(GameMode.valueOf(plugin.getConfig().getString("Out-of-Lives.gamemode").toUpperCase()));
            }
            if (plugin.getConfig().getBoolean("Out-of-Lives.teleport-player-world", false)) {
                World world = Bukkit.getWorld(plugin.getConfig().getString("Out-of-Lives.world"));
                if (world == null) return;
                player.teleport(world.getSpawnLocation());
            }
        }
    }

    @EventHandler
    public void executeCommands(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (plugin.getConfig().getBoolean("Commands-on-Death.enabled", false)) {
            if (plugin.getConfig().getInt("Commands-on-Death.execute-at-lives") == plugin.lives.getLives(player)) {
                for (String command : plugin.getConfig().getStringList("Commands-on-Death.commands")) {
                    command = command.replaceAll("%player%", player.getName())
                            .replaceAll("%lives%", "" + plugin.lives.getLives(player));
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), Utils.chatColor(command));
                }
            }

        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (!plugin.lives.hasData(player)) return;
        if (plugin.getConfig().getBoolean("Player-Respawn.title.send-title", true)) {
            if (plugin.lives.getLives(player) <= 0) {
                player.sendTitle(Utils.chatColor(plugin.getConfig().getString("Player-Respawn.title.no-lives-left.title")
                                .replaceAll("%lives%", "" + plugin.lives.getLives(player))
                                .replaceAll("%player%", player.getName())),
                        Utils.chatColor(plugin.getConfig().getString("Player-Respawn.title.no-lives-left.subtitle")
                                .replaceAll("%lives%", "" + plugin.lives.getLives(player))
                                .replaceAll("%player%", player.getName())),
                        plugin.getConfig().getInt("Player-Respawn.title.fade-in"),
                        plugin.getConfig().getInt("Player-Respawn.title.stay"),
                        plugin.getConfig().getInt("Player-Respawn.title.out"));
            }
            return;
        }
        player.sendTitle(Utils.chatColor(plugin.getConfig().getString("Player-Respawn.title.has-lives-left.title")
                        .replaceAll("%lives%", "" + plugin.lives.getLives(player))
                        .replaceAll("%player%", player.getName())),
                Utils.chatColor(plugin.getConfig().getString("Player-Respawn.title.has-lives-left.subtitle")
                        .replaceAll("%lives%", "" + plugin.lives.getLives(player))
                        .replaceAll("%player%", player.getName())),
                plugin.getConfig().getInt("Player-Respawn.title.fade-in"),
                plugin.getConfig().getInt("Player-Respawn.title.stay"),
                plugin.getConfig().getInt("Player-Respawn.title.out"));
    }

}
