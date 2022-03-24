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

public class lifeUpdate implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = event.getEntity().getKiller();

        if (!plugin.lives.hasData(player)) return;
        if (plugin.getConfig().getBoolean("Basic-Settings.players-only", false)) {
            if (killer != null && plugin.lives.hasData(killer)) {
                plugin.lives.addLives(killer, 1);
            }
            if (plugin.lives.getLives(player) != 0)
                plugin.lives.removeLives(player, 1);
        } else {
            if (plugin.lives.getLives(player) != 0)
                plugin.lives.removeLives(player, 1);
        }

        if (plugin.getConfig().getBoolean("Basic-Settings.command-on-death.enabled", false)) {
            for (String command : plugin.getConfig().getStringList("Basic-Settings.command-on-death.commands")) {
                command = command.replaceAll("%player%", player.getName())
                        .replaceAll("%lives%", "" + plugin.lives.getLives(player));
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), Utils.chatColor(command));
            }
        }

        if (plugin.lives.getLives(player) <= 0) {
            if (plugin.getConfig().getBoolean("No-Lives.ban-player", false)) {
                BanList bans = Bukkit.getBanList(BanList.Type.NAME);
                bans.addBan(player.getName(), Utils.chatColor(plugin.getConfig().getString("No-Lives.ban-message")), null, null);
                player.kickPlayer(Utils.chatColor(plugin.getConfig().getString("No-Lives.ban-message")));
            }
            if (plugin.getConfig().getBoolean("No-Lives.kick-player", false)) {
                player.kickPlayer(Utils.chatColor(plugin.getConfig().getString("No-Lives.kick-message")));
            }
            if (plugin.getConfig().getBoolean("No-Lives.change-gamemode", false)) {
                player.setGameMode(GameMode.valueOf(plugin.getConfig().getString("No-Lives.gamemode").toUpperCase()));
            }
        }
    }

}
