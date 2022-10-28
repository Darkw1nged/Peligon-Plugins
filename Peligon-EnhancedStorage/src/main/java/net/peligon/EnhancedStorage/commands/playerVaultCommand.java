package net.peligon.EnhancedStorage.commands;

import net.peligon.EnhancedStorage.Main;
import net.peligon.EnhancedStorage.libaries.Utils;
import net.peligon.EnhancedStorage.libaries.struts.playerVault;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

public class playerVaultCommand implements CommandExecutor {

    // Getting the main class.
    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("playervault")) {
            // If the command sender is not a player then send them a message and return.
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-console")));
                return true;
            }
            // We can safely assume that it is a player performing this command.
            Player player = (Player) sender;

            // We need to check the command and make sure all arguments are there.
            if (args.length < 1) {
                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("usage-vault")));
                return true;
            }

            int vaultNumber;
            // Try and parse the first argument to an int.
            // If it is not a valid number them send them a message and return.
            try {
                vaultNumber = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-invalid-number")));
                return true;
            }

            // Getting the maximum amount vaults
            int maximumVaultsAllowed = Integer.MAX_VALUE;

            // If the player has "Peligon.EnhancedStorage.*" then Integer.MAX_VALUE is the maximum number of vaults.
            if (!player.hasPermission("Peligon.EnhancedStorage.*")) {
                // Getting all the players permissions.
                for (PermissionAttachmentInfo permission : player.getEffectivePermissions()) {
                    if (permission.getPermission().startsWith("Peligon.EnhancedStorage.Vaults.")) {
                        // Updating the maximumVaultsAllowed with the permission vaults.
                        maximumVaultsAllowed = Integer.parseInt(permission.getPermission().split("\\.")[3]);
                    }
                }
            }

            // Check if vaultNumber is greater than maximumVaultsAllowed.
            // If it is then they do not have permission to open it.
            if (vaultNumber > maximumVaultsAllowed) {
                player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("error-no-permission")));
                return true;
            }

            // Creating and opening the vault.
            playerVault vault = new playerVault(vaultNumber, player.getUniqueId());
            vault.open(player);

            // Putting the player inside a temporary map checking for all open vaults.
            Utils.openVaults.put(player.getUniqueId(), vault);
        }
        return false;
    }

}
