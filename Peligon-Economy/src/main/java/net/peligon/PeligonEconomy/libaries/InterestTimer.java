package net.peligon.PeligonEconomy.libaries;

import net.peligon.PeligonEconomy.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class InterestTimer extends BukkitRunnable {

    private final Main plugin = Main.getInstance;
    public InterestTimer() {}

    @Override
    public void run() {
        if (Utils.InterestTimer == Utils.RawInterestTimer) {
            for (UUID uuid : plugin.Economy.getAllBanks().keySet()) {
                double amount = plugin.Economy.getBank(uuid);
                double toAdd;
                if (amount >= plugin.fileATM.getConfig().getInt("Options.interest.cash")) {
                    toAdd =  plugin.fileATM.getConfig().getInt("Options.interest.cash") * (plugin.fileATM.getConfig().getInt("Options.interest.percentage") / 100.0f);
                } else {
                    toAdd = amount * (plugin.fileATM.getConfig().getInt("Options.interest.percentage") / 100.0f);
                }

                plugin.Economy.AddBankAccount(uuid, toAdd);
                Player player = Bukkit.getPlayer(uuid);
                if (player == null) return;
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("interest-added"), toAdd));

                Utils.InterestTimer = Utils.RawInterestTimer;
            }
        } else {
            Utils.InterestTimer += 2;
        }
    }
}
