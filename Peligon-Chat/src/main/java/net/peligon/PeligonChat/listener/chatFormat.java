package net.peligon.PeligonChat.listener;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.peligon.PeligonChat.Main;
import net.peligon.PeligonChat.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import javax.xml.soap.Text;

public class chatFormat implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
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

        TextComponent newMessage = new TextComponent(plugin.getConfig().getString("chat-display-format", "%player%: %message%")
                .replaceAll("%player%", player.getName())
                .replaceAll("%message%", message)
                .replaceAll("%prefix%", prefix)
                .replaceAll("%suffix%", suffix));

        if (plugin.getConfig().getBoolean("player-hover-information.enabled", true)) {
            ComponentBuilder builder = new ComponentBuilder("");
            int pos = 0;
            for (String line : plugin.getConfig().getStringList("player-hover-information.format")) {
                line = line.replaceAll("%player%", player.getName())
                        .replaceAll("%rank%", group)
                        .replaceAll("%prefix%", prefix)
                        .replaceAll("%suffix%", suffix)
                        .replaceAll("%balance%", "" + balance)
                        .replaceAll("%balance_formatted%", Utils.chatColor("%amount%", balance))
                        .replaceAll("%kills%", "" + player.getStatistic(Statistic.PLAYER_KILLS))
                        .replaceAll("%deaths%", "" + player.getStatistic(Statistic.DEATHS))
                        .replaceAll("%world%", player.getWorld().getName());

                if (plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
                    line = PlaceholderAPI.setPlaceholders(event.getPlayer(), line);
                }

                pos++;
                if (pos == plugin.getConfig().getStringList("player-hover-information.format").size()) {
                    builder.append(Utils.chatColor(line));
                } else {
                    builder.append(Utils.chatColor(line + "\n"));
                }
            }
            newMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, builder.create()));

            for (Player target : Bukkit.getOnlinePlayers()) {
                target.spigot().sendMessage(newMessage);
            }
            event.setCancelled(true);
            return;
        }

        String format = plugin.getConfig().getString("chat-display-format", "%player%: %message%")
                .replaceAll("%player%", player.getName())
                .replaceAll("%message%", message)
                .replaceAll("%prefix%", prefix)
                .replaceAll("%suffix%", suffix);

        event.setFormat(format);
    }

}
