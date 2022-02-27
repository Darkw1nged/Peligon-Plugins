package net.peligon.PeligonChat.listener;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.peligon.PeligonChat.Main;
import net.peligon.PeligonChat.libaries.Utils;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class userViewer implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (!plugin.getConfig().getBoolean("player-hover-information.enabled", true)) return;

        Player player = event.getPlayer();
        TextComponent name = new TextComponent(player.getName());
        String prefix = "";
        String suffix = "";
        String group = "";
        double balance = 0.0;

        if (plugin.getChat() != null) {
            prefix = plugin.getChat().getPlayerPrefix(player);
            suffix = plugin.getChat().getPlayerSuffix(player);
            group = plugin.getChat().getPrimaryGroup(player);
        }

        if (plugin.getEconomy() != null) {
            balance = plugin.getEconomy().getBalance(player);
        }

        ComponentBuilder builder = new ComponentBuilder("");
        for (String line : plugin.getConfig().getStringList("player-hover-information.format")) {
            line = line.replaceAll("%player%", player.getName())
                    .replaceAll("%rank%", group)
                    .replaceAll("%prefix%", prefix)
                    .replaceAll("%suffix%", suffix)
                    .replaceAll("%balance%", "" + balance)
                    .replaceAll("%balance_formatted%", Utils.chatColor("", balance))
                    .replaceAll("%kills%", "" + player.getStatistic(Statistic.PLAYER_KILLS))
                    .replaceAll("%deaths%", "" + player.getStatistic(Statistic.DEATHS))
                    .replaceAll("%world%", player.getWorld().getName());

            if (plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null)
                line = PlaceholderAPI.setPlaceholders(event.getPlayer(), line);

            builder.append(Utils.chatColor(line));
        }

        name.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, builder.create()));
    }


}