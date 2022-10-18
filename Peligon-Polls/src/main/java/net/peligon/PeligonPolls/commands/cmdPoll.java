package net.peligon.PeligonPolls.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.peligon.PeligonPolls.Main;
import net.peligon.PeligonPolls.libaries.Utils;
import net.peligon.PeligonPolls.libaries.struts.Poll;
import net.peligon.PeligonPolls.menus.menuPolls;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class cmdPoll implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("poll")) {
            if (args.length >= 1 && args[0].equalsIgnoreCase("create")) {
                if (args.length >= 3) {
                    if (sender.hasPermission("Peligon.Polls.create") || sender.hasPermission("Peligon.Polls.*")) {
                        String title = args[1]
                                .replaceAll("_", " ");
                        StringBuilder description = new StringBuilder();

                        for (String s : args) {
                            description.append(s).append(" ");
                        }
                        description = new StringBuilder(description.toString().replaceFirst(args[0] + " ", "")
                                .replaceFirst(args[1] + " ", ""));

                        LocalDateTime created = LocalDateTime.now();
                        Poll poll;
                        if (!(sender instanceof Player)) {
                            poll = new Poll(title, description.toString(), sender.getName(), UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf5"), 0, 0, created, new ArrayList<>());
                        } else {
                            Player player = (Player) sender;
                            poll = new Poll(title, description.toString(), player.getName(), player.getUniqueId(), 0, 0, created, new ArrayList<>());
                        }

                        Utils.sendPoll(title, description.toString(), 0, 0, poll);

                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("poll-created")));

                    } else {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                        return true;
                    }
                } else {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("polls-usage")));
                    return true;
                }
            } else if (args.length >= 1 && args[0].equalsIgnoreCase("get")) {
                if (sender.hasPermission("Peligon.Polls.get") || sender.hasPermission("Peligon.Polls.*")) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("admin-remove")));
                    for (Poll poll : Utils.polls) {
                       TextComponent component = new TextComponent(Utils.chatColor("&c&l" + poll.getMessageID()));
                       component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/poll remove " + poll.getMessageID()));

                       sender.spigot().sendMessage(component);
                    }

                } else {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                    return true;
                }
            } else if (args.length >= 1 && args[0].equalsIgnoreCase("remove")) {
                if (sender.hasPermission("Peligon.Polls.remove") || sender.hasPermission("Peligon.Polls.*")) {
                    if (args.length != 2) {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("poll-remove-usage")));
                        return true;
                    }
                    long id = Long.parseLong(args[1]);
                    Utils.polls.removeIf(poll -> poll.getMessageID() == id);
                    Utils.removeEmbed(String.valueOf(id));
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") + plugin.fileMessage.getConfig().getString("poll-removed")));

                } else {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                    return true;
                }
            } else if (args.length > 1 && args[0].equalsIgnoreCase("vote")) {
                if (!(sender instanceof Player)) return true;
                Player player = (Player)sender;
                if (!Utils.poll_ID.isEmpty() && Utils.poll_ID.containsKey(player.getUniqueId())) {
                    if (args[1].equalsIgnoreCase("cancel")) {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("vote-canceled")));
                        Utils.poll_ID.remove(player.getUniqueId());
                    } else if (args[1].equals(String.valueOf(Utils.poll_ID.get(player.getUniqueId()).getMessageID()))) {
                        if (Utils.poll_ID.get(player.getUniqueId()).getVotes() != null && Utils.poll_ID.get(player.getUniqueId()).alreadyVoted(player)) {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("already-voted")));
                            Utils.poll_ID.remove(player.getUniqueId());
                            return true;
                        }
                        if (args[2].equals("yes")) {
                            Utils.poll_ID.get(player.getUniqueId()).setUpVotes(Utils.poll_ID.get(player.getUniqueId()).getUpVotes() + 1);
                        } else {
                            Utils.poll_ID.get(player.getUniqueId()).setDownVotes(Utils.poll_ID.get(player.getUniqueId()).getDownVotes() + 1);
                        }
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("vote-casted")));

                        Utils.poll_ID.get(player.getUniqueId()).addVotes(player);
                        Utils.updatePoll(player);
                    }
                }
            } else {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                    return true;
                }
                if (sender.hasPermission("Peligon.Polls.view") || sender.hasPermission("Peligon.Polls.*")) {
                    Player player = (Player)sender;
                    new menuPolls(player).open();

                } else {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                }
            }
        }
        return false;
    }

}
