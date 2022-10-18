package net.peligon.ClearChat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements CommandExecutor {


    public void onEnable() {
        getCommand("disposal").setExecutor(this);
        log("[Peligon Mini] ClearChat has been enabled.");
    }

    public void onDisable() {
        log("[Peligon Mini] ClearChat has been disabled.");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("clearchat")) {
            if (sender.hasPermission("Peligon.ClearChat.Use")) {
                for (int i=0; i<getConfig().getInt("lines", 150); i++) {
                    Bukkit.broadcastMessage("");
                }
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.cleared-chat").replaceAll("%player%", sender.getName())));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.no-permission")));
            }

        }
        return false;
    }

    private static void log(String message) { System.out.println(message); }

}
