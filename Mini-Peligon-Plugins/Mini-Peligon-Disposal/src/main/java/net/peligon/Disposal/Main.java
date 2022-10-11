package net.peligon.Disposal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements CommandExecutor, Listener {

    public void onEnable() {
        getCommand("disposal").setExecutor(this);
        getServer().getPluginManager().registerEvents(this, this);
    }

    public void onDisable() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("disposal")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.console")));
                return true;
            }
            Player player = (Player) sender;
            if (player.hasPermission("Peligon.Disposal.use") || player.hasPermission("Peligon.Disposal.*")) {
                player.openInventory(Bukkit.createInventory(player,
                        getConfig().getInt("inventory.size"),
                        ChatColor.translateAlternateColorCodes('&', getConfig().getString("inventory.title"))));
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.no-permission")));
            }

        }
        return false;
    }

    @EventHandler
    public void onCreate(SignChangeEvent event) {
        Player player = event.getPlayer();
        if (getConfig().getBoolean("disposal-sign", true)) {
            if (player.hasPermission("Peligon.Disposal.createSign") || player.hasPermission("Peligon.Disposal.*")) {
                String line0 = event.getLine(0);
                if (line0 == null)
                    return;
                if (line0.equalsIgnoreCase("[disposal]") || line0.equalsIgnoreCase("[bin]") ||
                        line0.equalsIgnoreCase("[trash]") || line0.equalsIgnoreCase("[trashcan]")) {
                    event.setLine(0, ChatColor.translateAlternateColorCodes('&', getConfig().getString("Sign.line-1")));
                    event.setLine(1, ChatColor.translateAlternateColorCodes('&', getConfig().getString("Sign.line-2")));
                    event.setLine(2, ChatColor.translateAlternateColorCodes('&', getConfig().getString("Sign.line-3")));
                    event.setLine(3, ChatColor.translateAlternateColorCodes('&', getConfig().getString("Sign.line-4")));
                }
            }
        }
    }

    @EventHandler
    public void SignInteract(PlayerInteractEvent event) {
        if (getConfig().getBoolean("disposal-sign", true)) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Player player = event.getPlayer();
                if (player.hasPermission("Peligon.Disposal.useSign") || player.hasPermission("Peligon.Disposal.*")) {
                    Block block = event.getClickedBlock();
                    if (block == null) return;
                    BlockState blockState = block.getState();
                    if (!(blockState instanceof org.bukkit.block.Sign)) return;

                    if (isSign(block)) {
                        Sign sign = (Sign) block.getState();
                        String line0 = sign.getLine(0);
                        if (line0.contains(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Sign.line-1")))) {
                            player.performCommand("disposal");
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.no-permission")));
                }
            }
        }
    }

    public static boolean isSign(Block block) {
        switch (block.getType()) {
            case SPRUCE_SIGN:
            case ACACIA_SIGN:
            case BIRCH_SIGN:
            case CRIMSON_SIGN:
            case DARK_OAK_SIGN:
            case JUNGLE_SIGN:
            case OAK_SIGN:
            case WARPED_SIGN:
            case SPRUCE_WALL_SIGN:
            case ACACIA_WALL_SIGN:
            case BIRCH_WALL_SIGN:
            case CRIMSON_WALL_SIGN:
            case DARK_OAK_WALL_SIGN:
            case JUNGLE_WALL_SIGN:
            case OAK_WALL_SIGN:
            case WARPED_WALL_SIGN:
                return true;
        }
        return false;
    }
}
