package net.peligon.LifeSteal.commands;

import net.peligon.LifeSteal.Main;
import net.peligon.LifeSteal.libaries.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdCombatTag implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("combattag")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player) sender;
            if (sender.hasPermission("Peligon.LifeSteal.CombatTag") || sender.hasPermission("Peligon.LifeSteal.*")) {
                if (Utils.combatTag.containsKey(player.getUniqueId())) {
                    long timeLeft = ((Utils.combatTag.get(player.getUniqueId()) / 1000) + plugin.getConfig().getInt("combat-tag.duration")) - (System.currentTimeMillis() / 1000);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("combat-tag").replaceAll("%time%", String.valueOf(timeLeft))));
                    return true;
                }
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-combat")));
            } else {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
