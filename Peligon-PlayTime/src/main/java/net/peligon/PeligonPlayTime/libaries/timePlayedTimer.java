package net.peligon.PeligonPlayTime.libaries;

import net.peligon.PeligonPlayTime.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

public class timePlayedTimer extends BukkitRunnable {

    private final Main plugin = Main.getInstance;

    public timePlayedTimer() { }

    @Override
    public void run() {
        for (Player online : Bukkit.getOnlinePlayers()) {
            plugin.playerTime.addTime(online);
            for (String reward : plugin.getConfig().getConfigurationSection("rewards").getKeys(false)) {
                long seconds = plugin.playerTime.getTimePlayed(online) / 1000;

                if (plugin.getConfig().contains("rewards." + reward + ".claimed." + online.getUniqueId())) {
                    int timesClaimed = plugin.getConfig().getInt("rewards." + reward + ".claimed." + online.getUniqueId());
                    if (seconds >= (long) plugin.getConfig().getInt("rewards." + reward + ".time") * timesClaimed) {
                        online.sendMessage(Utils.chatColor(plugin.getConfig().getString(Utils.chatColor("rewards." + reward + ".message"))));
                        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                        scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
                            @Override
                            public void run() {
                                int pos = 0;
                                for (; pos < plugin.getConfig().getStringList("rewards." + reward + ".commands").size(); pos++) {
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), plugin.getConfig().getStringList("rewards." + reward + ".commands").get(pos).replace("%player%", online.getName()));
                                }
                                if (pos == plugin.getConfig().getStringList("rewards." + reward + ".coomands").size()) {
                                    scheduler.cancelTask(getTaskId());
                                }
                            }
                        }, 10L);
                        plugin.getConfig().set("rewards." + reward + ".claimed." + online.getUniqueId(), timesClaimed + 1);
                        plugin.saveConfig();
                    }
                    continue;
                }
                if (seconds >= (long) plugin.getConfig().getInt("rewards." + reward + ".time")) {
                    online.sendMessage(Utils.chatColor(plugin.getConfig().getString(Utils.chatColor("rewards." + reward + ".message"))));

                    BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                    scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            int pos = 0;
                            for (; pos < plugin.getConfig().getStringList("rewards." + reward + ".commands").size(); pos++) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), plugin.getConfig().getStringList("rewards." + reward + ".commands").get(pos).replace("%player%", online.getName()));
                            }
                            if (pos == plugin.getConfig().getStringList("rewards." + reward + ".coomands").size()) {
                                scheduler.cancelTask(getTaskId());
                            }
                        }
                    }, 10L);
                    plugin.getConfig().set("rewards." + reward + ".claimed." + online.getUniqueId(), 1);
                    plugin.saveConfig();
                }
            }
        }
    }

}
