package net.peligon.PeligonAuthentication.commands;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import net.peligon.PeligonAuthentication.Main;
import net.peligon.PeligonAuthentication.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdAuthReset implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("authentication")) {
            if (args.length != 2) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("reset-usage")));
                return true;
            }
            if (args[0].equalsIgnoreCase("reset")) {
                if (sender.hasPermission("Peligon.Authentication.reset")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")
                                .replaceAll("%player%", args[1])
                                .replaceAll("%target%", args[1])));
                        return true;
                    }
                    GoogleAuthenticator auth = new GoogleAuthenticator();
                    GoogleAuthenticatorKey key = auth.createCredentials();

                    plugin.authentication.setToken(target, key.getKey());
                    if (!target.isOnline()) return true;

                    for (String message : plugin.fileMessage.getConfig().getStringList("new-code")) {
                        target.sendMessage(Utils.chatColor(message).replaceAll("%key%", key.getKey()));
                    }

                } else {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                }
            } else {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("reset-usage")));
            }
        }
        return false;
    }
}
