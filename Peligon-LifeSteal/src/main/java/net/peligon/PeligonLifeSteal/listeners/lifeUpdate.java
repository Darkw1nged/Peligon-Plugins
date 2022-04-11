package net.peligon.PeligonLifeSteal.listeners;

import net.peligon.PeligonLifeSteal.Main;
import net.peligon.PeligonLifeSteal.libaries.Utils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class lifeUpdate implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = event.getEntity().getKiller();

        if (!plugin.lives.hasData(player)) return;

        if (plugin.getConfig().getBoolean("Lose-Lives.players-only", false)) {
            if (killer != null && plugin.lives.hasData(killer)) {
                plugin.lives.addLives(killer, 1);
            }
        }
        for (String key : plugin.getConfig().getStringList("Lose-Lives.blacklisted-mobs")) {
            if (event.getEntity().getKiller().getName().equals(key.toUpperCase())) return;
        }
        if (plugin.lives.getLives(player) != 0)
            plugin.lives.removeLives(player, 1);

        if (plugin.getConfig().getBoolean("Commands-on-Death.enabled", false)) {
            if (plugin.getConfig().getInt("Commands-on-Death.execute-at-lives") == plugin.lives.getLives(player)) {
                for (String command : plugin.getConfig().getStringList("Commands-on-Death.commands")) {
                    command = command.replaceAll("%player%", player.getName())
                            .replaceAll("%lives%", "" + plugin.lives.getLives(player));
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), Utils.chatColor(command));
                }
            }

        }

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
        }

        if (plugin.lives.getLives(player) > 0) {
            event.setDeathMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("death-screen-message.lives")
                    .replaceAll("%lives%", "" + plugin.lives.getLives(player))));
        } else {
            event.setDeathMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("death-screen-message.no-lives")
                    .replaceAll("%lives%", "" + plugin.lives.getLives(player))));
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
        } else {
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

}
