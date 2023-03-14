package net.peligon.Autosell;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Main extends JavaPlugin implements CommandExecutor {

    private List<UUID> autosell = new ArrayList<>();

    public void onEnable() {
        getCommand("autosell").setExecutor(this);
    }

    public void onDisable() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("autosell")) {
            if (!(sender instanceof Player)) {

            }
        }
        return false;
    }


}
