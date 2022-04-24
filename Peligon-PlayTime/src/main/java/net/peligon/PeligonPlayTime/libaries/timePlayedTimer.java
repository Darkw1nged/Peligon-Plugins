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

           }
       }
    }
}
