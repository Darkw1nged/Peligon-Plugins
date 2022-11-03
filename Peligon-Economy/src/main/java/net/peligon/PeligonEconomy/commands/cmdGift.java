package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.storage.CustomConfig;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class cmdGift implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    // already-claimed: "&cError! You must wait a day before claiming again."
    // cannot-gift-yourself: "&cError! You cannot gift yourself."

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gift")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("console")));
                return true;
            }
            Player player = (Player) sender;
            if (player.hasPermission("Peligon.Economy.Gift") || player.hasPermission("Peligon.Economy.*")) {
                if (args.length == 0) {
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("specify-player")));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("no-player-found")));
                    return true;
                }
                if (target.getName().equalsIgnoreCase(player.getName())) {
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("cannot-gift-yourself")));
                    return true;
                }
                if (player.getInventory().getItemInHand().getType() == Material.AIR) {
                    player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("no-item-in-hand")));
                    return true;
                }

                CustomConfig config = new CustomConfig(plugin, target.getUniqueId().toString(), "data/gifts");
                YamlConfiguration data = config.getConfig();

                int pos = 1;
                if (data.getConfigurationSection("items") != null && !data.getConfigurationSection("items").getKeys(false).isEmpty()) {
                    pos += data.getConfigurationSection("items").getKeys(false).size();
                }

                data.set("items." + pos, player.getInventory().getItemInHand());
                config.saveConfig();

                player.getInventory().setItemInHand(new ItemStack(Material.AIR));

                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                        plugin.languageFile.getConfig().getString("gift-sent").replace("%player%", target.getName())));

            } else {
                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
