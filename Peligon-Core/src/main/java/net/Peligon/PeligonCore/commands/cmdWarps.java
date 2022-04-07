package net.Peligon.PeligonCore.commands;

import net.Peligon.PeligonCore.Main;
import net.Peligon.PeligonCore.libaries.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class cmdWarps implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("warps")) {
            if (sender.hasPermission("Peligon.Core.Warps") || sender.hasPermission("Peligon.Core.*")) {
                StringBuilder list = new StringBuilder();
                File folder = new File(plugin.getDataFolder() + File.separator + "warps");
                if (!folder.exists() || folder.listFiles().length == 0) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-warps-found")));
                    return true;
                }
                for (File file : folder.listFiles()) {
                    FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                    list.append(configuration.getString("name"));
                }
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("warps-list").replace("%list%", list.toString())));
            } else {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
