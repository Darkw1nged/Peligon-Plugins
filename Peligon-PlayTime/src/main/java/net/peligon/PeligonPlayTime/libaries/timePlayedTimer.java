package net.peligon.PeligonPlayTime.libaries;

import net.peligon.PeligonPlayTime.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class timePlayedTimer extends BukkitRunnable {

    private final Main plugin = Main.getInstance;

    public timePlayedTimer() {}

    @Override
    public void run() {
        for (Player online : Bukkit.getOnlinePlayers()) {
            for (String reward : plugin.getConfig().getConfigurationSection("rewards").getKeys(false)) {
                if (plugin.getConfig().getConfigurationSection("rewards." + reward + ".claimed") != null &&
                        plugin.getConfig().getConfigurationSection("rewards." + reward + ".claimed").contains(online.getUniqueId().toString())) {

                    int timesClaimed = plugin.getConfig().getConfigurationSection("rewards." + reward + ".claimed").getInt(online.getUniqueId().toString());
                    long seconds = plugin.playerTime.getTimePlayed(online) / 1000;
                    if ((long) plugin.getConfig().getInt("rewards." + reward + ".time") * timesClaimed >= seconds) {
                        online.sendMessage(plugin.getConfig().getString(Utils.chatColor("rewards." + reward + ".message")));
                        for (String command : plugin.getConfig().getStringList("rewards." + reward + ".commands")) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", online.getName()));
                        }
                    }
                    plugin.getConfig().getConfigurationSection("rewards." + reward + ".claimed").set(online.getUniqueId().toString(), timesClaimed + 1);
                    continue;
                }
                long seconds = plugin.playerTime.getTimePlayed(online) / 1000;
                if ((long) plugin.getConfig().getInt("rewards." + reward + ".time") >= seconds) {
                    online.sendMessage(plugin.getConfig().getString(Utils.chatColor("rewards." + reward + ".message")));
                    for (String command : plugin.getConfig().getStringList("rewards." + reward + ".commands")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", online.getName()));
                    }
                }
                plugin.getConfig().getConfigurationSection("rewards." + reward + ".claimed").set(online.getUniqueId().toString(), 1);
            }
        }
    }
}
