package net.Peligon.PeligonCore.commands;

import net.Peligon.PeligonCore.Main;
import net.Peligon.PeligonCore.libaries.CustomConfig;
import net.Peligon.PeligonCore.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class cmdTeleportBlock implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("teleportblock")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player) sender;
            if (player.hasPermission("Peligon.Core.Teleport.Block") || player.hasPermission("Peligon.Core.*")) {
                if (args.length == 0) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                    return true;
                }
                CustomConfig config = new CustomConfig(plugin, player.getUniqueId().toString(), "data");
                YamlConfiguration data = config.getConfig();

                if (data.getStringList("blocked-players.teleport").contains(target.getUniqueId().toString())) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("player-already-blocked").replaceAll("%player%", target.getName())));
                    return true;
                }
                data.getStringList("blocked-players.teleport").add(target.getUniqueId().toString());
                data.set("blocked-players.teleport", data.getStringList("blocked-players.teleport"));
                config.saveConfig();

                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                        plugin.fileMessage.getConfig().getString("player-blocked").replaceAll("%player%", target.getName())));
            } else {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
