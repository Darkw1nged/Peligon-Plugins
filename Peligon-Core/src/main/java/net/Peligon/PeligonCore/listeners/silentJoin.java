package net.peligon.PeligonCore.listeners;

import net.peligon.PeligonCore.Main;
import net.peligon.PeligonCore.libaries.CustomConfig;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class silentJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        CustomConfig config = new CustomConfig(Main.getInstance, event.getPlayer().getUniqueId().toString(), "data");
        YamlConfiguration data = config.getConfig();
        if (data.getBoolean("silentJoin") || event.getPlayer().hasPermission("Peligon.Core.SilentJoin")) {
            event.setJoinMessage(null);
        }
    }

}
