package net.peligon.PeligonSkyblock.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.peligon.PeligonSkyblock.Main;
import net.peligon.PeligonSkyblock.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class cmdIsland implements CommandExecutor {

    private final Main plugin = Main.getInstance;
    private Map<Player, Player> invitation = new HashMap<>();
    private Map<UUID, Long> requestTimeout = new HashMap<>();
    private final int timeout = plugin.getConfig().getInt("Invitation-timeout");

    // "&cIncorrect Command Usage: &f/island [invite|upgrade|manage|setting] [player]"

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("island")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player)sender;
            if (args.length < 1) {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("island-usage")));
                return true;
            }
            switch (args[0].toLowerCase()) {
                case "create":
                    if (plugin.islandManager.hasIsland(player)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("already-got-island")));
                        return true;
                    }
                    plugin.islandManager.createIsland(player);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("created-island")));
                    return true;
                case "delete":
                    if (!plugin.islandManager.hasIsland(player)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-got-island")));
                        return true;
                    }
                    plugin.islandManager.deleteIsland(player);
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("deleted-island")));
                    return true;
                case "invite":
                    if (args[1].equalsIgnoreCase("accept")) {
                        if (!requestTimeout.isEmpty() && requestTimeout.containsKey(player.getUniqueId())) {
                            long timeLeft = ((requestTimeout.get(player.getUniqueId()) / 1000) + timeout) - (System.currentTimeMillis() / 1000);
                            if (timeLeft > 0) break;
                            requestTimeout.remove(player.getUniqueId());
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-pending-invites")));
                            return true;
                        }
                        plugin.islandManager.addIslandMember(invitation.get(player), player);
                        invitation.remove(player);
                        requestTimeout.remove(player.getUniqueId());

                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("island-invite-accepted")));
                        return true;
                    } else if (args[1].equalsIgnoreCase("deny")) {
                        if (!requestTimeout.isEmpty() && requestTimeout.containsKey(player.getUniqueId())) {
                            requestTimeout.remove(player.getUniqueId());
                            invitation.remove(player);
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("island-invite-denied")));
                        }
                        return true;
                    }

                    if (!plugin.islandManager.hasIsland(player)) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-got-island")));
                        return true;
                    }
                    if (args.length != 2) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-player")));
                        return true;
                    }

                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")
                                .replaceAll("%player%", args[1])
                                .replaceAll("%target%", args[1])));
                        return true;
                    }
                    invitation.put(target, player);

                    target.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("island-invite"))
                            .replaceAll("%player%", player.getName())
                            .replaceAll("%target%", player.getName()));

                    TextComponent yesComponent = new TextComponent(Utils.chatColor("&a&l[ ACCEPT ] "));
                    TextComponent noComponent = new TextComponent(Utils.chatColor(" &C&l[ DENY ]"));

                    yesComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/island invite accept"));
                    noComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/island invite deny"));

                    target.spigot().sendMessage(yesComponent, noComponent);
                    requestTimeout.put(player.getUniqueId(), System.currentTimeMillis());

                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("island-invite-sent"))
                            .replaceAll("%player%", target.getName())
                            .replaceAll("%target%", target.getName()));
            }
            return true;
        }
        return false;
    }

}
