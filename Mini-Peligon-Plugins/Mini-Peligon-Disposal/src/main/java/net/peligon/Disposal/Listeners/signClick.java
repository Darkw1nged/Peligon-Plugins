package net.peligon.Disposal.Listeners;

import net.peligon.Disposal.Main;
import net.peligon.Disposal.Utilities.Lists.Permissions;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class signClick implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onCreate(SignChangeEvent event) {
        Player player = event.getPlayer();
        if (plugin.getConfig().getBoolean("disposal-sign", true)) {
            if (player.hasPermission(Permissions.create_sign_permission.getPermission()) || player.hasPermission(Permissions.global_permission.getPermission())) {
                String line0 = event.getLine(0);
                if (line0 == null)
                    return;
                if (line0.equalsIgnoreCase("[disposal]") || line0.equalsIgnoreCase("[bin]") ||
                        line0.equalsIgnoreCase("[trash]") || line0.equalsIgnoreCase("[trashcan]")) {
                    event.setLine(0, plugin.utils.chatColor(plugin.getConfig().getString("Sign.line-1")));
                    event.setLine(1, plugin.utils.chatColor(plugin.getConfig().getString("Sign.line-2")));
                    event.setLine(2, plugin.utils.chatColor(plugin.getConfig().getString("Sign.line-3")));
                    event.setLine(3, plugin.utils.chatColor(plugin.getConfig().getString("Sign.line-4")));
                }
            }
        }
    }

    @EventHandler
    public void SignInteract(PlayerInteractEvent event) {
        if (plugin.getConfig().getBoolean("disposal-sign", true)) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Player player = event.getPlayer();
                Block block = event.getClickedBlock();
                if (block == null) return;
                BlockState blockState = block.getState();
                if (!(blockState instanceof org.bukkit.block.Sign)) return;

                if (isSign(block)) {
                    Sign sign = (Sign)block.getState();
                    String line0 = sign.getLine(0);
                    if (line0.contains(plugin.utils.chatColor(plugin.getConfig().getString("Sign.line-1")))) {
                        player.performCommand("disposal");
                    }
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
