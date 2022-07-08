package net.peligon.EnhancedStorage.commands;

import net.peligon.EnhancedStorage.Main;
import net.peligon.EnhancedStorage.libaries.CustomConfig;
import net.peligon.EnhancedStorage.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.PermissionAttachmentInfo;

@SuppressWarnings("ALL")
public class cmdPlayerVault implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("playervault")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player) sender;
            if (player.hasPermission("Peligon.EnhancedStorage.PlayerVault") || player.hasPermission("Peligon.EnhancedStorage.*")) {
                if (args.length < 1) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-number")));
                    return true;
                }
                int number;
                try {
                    number = Integer.parseInt(args[0]);
                    if (number < 1) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-number")));
                        return true;
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-number")));
                    return true;
                }

                int maxVaults = 0;
                if (player.hasPermission("Peligon.EnhancedStorage.PlayerVault.Bypass") || player.hasPermission("Peligon.EnhancedStorage.*")) {
                    maxVaults = 999999999;
                } else {
                    for (PermissionAttachmentInfo permission : player.getEffectivePermissions()) {
                        if (permission.getPermission().startsWith("Peligon.EnhancedStorage.PlayerVault.")) {
                            int vaultNumber = Integer.parseInt(permission.getPermission().split("\\.")[3]);
                            if (vaultNumber > maxVaults) {
                                maxVaults = vaultNumber;
                            }
                        }
                    }
                }

                if (number > maxVaults) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("player-vault-locked")));
                    return true;
                }

                CustomConfig rawVault = new CustomConfig(plugin, "playerVaults/" + player.getUniqueId().toString() + "/" + number, false);
                Inventory vault = Bukkit.createInventory(player, 54, Utils.chatColor(plugin.getConfig().getString("player-vault-title").replaceAll("%number%", String.valueOf(number))));

                if (rawVault.getConfig().contains("items")) {
                    for (String key : rawVault.getConfig().getConfigurationSection("items").getKeys(false)) {
                        vault.setItem(Integer.parseInt(key), rawVault.getConfig().getItemStack("items." + key));
                    }
                }

                player.openInventory(vault);
            } else {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                return true;
            }
        }
        return false;
    }

}
