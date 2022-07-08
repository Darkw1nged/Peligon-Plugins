package net.peligon.LifeSteal.libaries.timer;

import net.peligon.LifeSteal.Main;
import net.peligon.LifeSteal.libaries.Utils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class combatTagTimer extends BukkitRunnable {

    private final Main plugin = Main.getInstance;

    @Override
    public void run() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (!Utils.combatTag.isEmpty() && Utils.combatTag.containsKey(player.getUniqueId())) {
                long timeLeft = ((Utils.combatTag.get(player.getUniqueId()) / 1000) + plugin.getConfig().getInt("combat-tag.duration")) - (System.currentTimeMillis() / 1000);
                if (timeLeft > 0) return;
                Utils.combatTag.remove(player.getUniqueId());
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("combat-tag-expired")));
            }
        }
    }
}
