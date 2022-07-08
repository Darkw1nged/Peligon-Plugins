package net.peligon.PeligonAuthentication.listener;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import net.peligon.PeligonAuthentication.Main;
import net.peligon.PeligonAuthentication.libaries.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class accountSetup implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("Peligon.Authentication.required")) return;
        Location loc = player.getLocation();
        Location blockUnderPlayer = new Location(player.getWorld(), loc.getX(), loc.getY()-1, loc.getZ());

        if (blockUnderPlayer.getBlock().getType().equals(Material.AIR)) {
            Location oldLoc = Utils.getNextBlockUnderPlayer(player).get().getLocation();
            Location newLoc = new Location(oldLoc.getWorld(), oldLoc.getX(), oldLoc.getY() + 1, oldLoc.getZ());
            player.teleport(newLoc);
        }

        if (plugin.authentication.hasData(player)) {
            Utils.neededAuthentication.add(player.getUniqueId());
            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                    plugin.fileMessage.getConfig().getString("enter-code")));
            return;
        }
        GoogleAuthenticator auth = new GoogleAuthenticator();
        GoogleAuthenticatorKey key = auth.createCredentials();

        for (String message : plugin.fileMessage.getConfig().getStringList("new-code")) {
            player.sendMessage(Utils.chatColor(message).replaceAll("%key%", key.getKey()));
        }
        plugin.authentication.createData(player, key.getKey());
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (!Utils.neededAuthentication.contains(player.getUniqueId())) return;
        event.setCancelled(true);
        try {
            if (!Utils.playerInputCode(player, Integer.parseInt(message))) {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-code")));
                return;
            }
            Utils.neededAuthentication.remove(player.getUniqueId());
            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                    plugin.fileMessage.getConfig().getString("access-granted")));
        } catch (Exception e) {
            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-code")));
        }
    }

}
