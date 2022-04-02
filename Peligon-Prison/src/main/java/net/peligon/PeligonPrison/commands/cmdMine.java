package net.peligon.PeligonPrison.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.peligon.PeligonPrison.Main;
import net.peligon.PeligonPrison.libaries.Utils;
import net.peligon.PeligonPrison.struts.Mine;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class cmdMine implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("mine")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player) sender;
            if (player.hasPermission("Peligon.Prison.Mine") || player.hasPermission("Peligon.Prison.*")) {
                if (args.length == 0) {
                    if (player.hasPermission("Peligon.Prison.Mine.Help") || player.hasPermission("Peligon.Prison.*")) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("help-menu-header")));
                        for (String s : plugin.fileMessage.getConfig().getStringList("mine-help-menu")) {
                            player.sendMessage(Utils.chatColor(s));
                        }
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("help-menu-footer")));
                    } else {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                    }
                    return true;
                }
                switch (args[0].toLowerCase()) {
                    case "help":
                        if (player.hasPermission("Peligon.Prison.Mine.Help") || player.hasPermission("Peligon.Prison.*")) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("help-menu-header")));
                            for (String s : plugin.fileMessage.getConfig().getStringList("mine-help-menu")) {
                                player.sendMessage(Utils.chatColor(s));
                            }
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("help-menu-footer")));
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                        }
                        break;
                    case "info":
                        if (player.hasPermission("Peligon.Prison.Mine.Info") || player.hasPermission("Peligon.Prison.*")) {
                            if (args.length == 2) {
                                Mine mine = plugin.minesManager.getMine(args[1]);
                                if (mine == null) {
                                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("mine-not-found")));
                                    return true;
                                }
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("mine-info-header")));
                                for (String s : plugin.fileMessage.getConfig().getStringList("mine-info-menu")) {
                                    player.sendMessage(Utils.chatColor(s)
                                            .replaceAll("%name%", mine.getName())
                                            .replaceAll("%lastreset%", mine.getLastReset() + "")
                                            .replaceAll("%nextreset%", mine.getResetTime() + "")
                                            .replaceAll("%spawn%", mine.getSpawnLocation().getWorld().getName() + " " + mine.getSpawnLocation().getBlockX() + " " + mine.getSpawnLocation().getBlockY() + " " + mine.getSpawnLocation().getBlockZ())
                                            .replaceAll("%private%", mine.hasPermission() ? "Yes" : "No")
                                            .replaceAll("%permission%", mine.getPermission()));
                                }
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("mine-info-footer")));
                            } else {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("mine-not-found")));
                            }
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                        }
                        break;
                    case "create":
                        if (player.hasPermission("Peligon.Prison.Mine.Create") || player.hasPermission("Peligon.Prison.*")) {
                            if (args.length != 2) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-name")));
                                return true;
                            }
                            if (Utils.isOnlyLetters(args[1])) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-name")));
                                return true;
                            }
                            Utils.mineCreationInProgress.put(player.getUniqueId(), args[1]);
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("mine-creation").replaceAll("%mine%", args[1])));
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("mine-creation-info")));
                            plugin.itemManager.addItem(player);
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                        }
                        break;
                    case "confirm":
                        if (player.hasPermission("Peligon.Prison.Mine.Create") || player.hasPermission("Peligon.Prison.*")) {
                            if (Utils.mineCreationInProgress.containsKey(player.getUniqueId())) {
                                if (Utils.mineCreationCornerOne.containsKey(player.getUniqueId()) && Utils.mineCreationCornerTwo.containsKey(player.getUniqueId())) {
                                    if (Utils.mineCreationCornerOne.get(player.getUniqueId()).getWorld().equals(Utils.mineCreationCornerTwo.get(player.getUniqueId()).getWorld())) {
                                        if (Utils.mineCreationCornerOne.get(player.getUniqueId()).distance(Utils.mineCreationCornerTwo.get(player.getUniqueId())) > 1) {
                                            Mine mine = new Mine(Utils.mineCreationInProgress.get(player.getUniqueId()), Utils.mineCreationCornerOne.get(player.getUniqueId()), Utils.mineCreationCornerTwo.get(player.getUniqueId()));
                                            Utils.mines.add(mine);
                                            Utils.mineCreationInProgress.remove(player.getUniqueId());
                                            Utils.mineCreationCornerOne.remove(player.getUniqueId());
                                            Utils.mineCreationCornerTwo.remove(player.getUniqueId());
                                            plugin.itemManager.removeItem(player);
                                            player.sendMessage(plugin.fileMessage.getConfig().getString("prefix") +
                                                    Utils.chatColor(plugin.fileMessage.getConfig().getString("mine-created").replaceAll("%mine%", mine.getName())));
                                        } else {
                                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("too-small-mine")));
                                        }
                                    }
                                } else {
                                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("select-area")));
                                }
                            }
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                        }
                    case "cancel":
                        if (player.hasPermission("Peligon.Prison.Mine.Create") || player.hasPermission("Peligon.Prison.*")) {
                            if (Utils.mineCreationInProgress.containsKey(player.getUniqueId())) {
                                Utils.mineCreationInProgress.remove(player.getUniqueId());
                                Utils.mineCreationCornerOne.remove(player.getUniqueId());
                                Utils.mineCreationCornerTwo.remove(player.getUniqueId());
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                        plugin.fileMessage.getConfig().getString("mine-creation-cancelled")));
                            } else {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-in-mine-creation")));
                            }
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                        }
                    case "delete":
                        if (player.hasPermission("Peligon.Prison.Mine.Delete") || player.hasPermission("Peligon.Prison.*")) {
                            if (args.length != 2) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-name")));
                                return true;
                            }
                            Mine mine = plugin.minesManager.getMine(args[1]);
                            if (mine == null) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("mine-not-found")));
                                return true;
                            }
                            plugin.minesManager.removeMine(mine.getName());
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("mine-deleted").replaceAll("%mine%", args[1])));
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                        }
                        break;
                    case "list":
                        if (player.hasPermission("Peligon.Prison.Mine.List") || player.hasPermission("Peligon.Prison.*")) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("mine-list-header")));
                            for (Mine mine : plugin.minesManager.getMines()) {
                                TextComponent original = new TextComponent(
                                        Utils.chatColor(plugin.fileMessage.getConfig().getString("mine-list-item")
                                                .replaceAll("%mine%", mine.getName())
                                                .replaceAll("%resets%", plugin.minesManager.getTimeUntilReset(mine.getName()))));

                                TextComponent edit = new TextComponent(Utils.chatColor(" &7[&aEdit&7]"));
                                edit.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mine edit"));

                                player.spigot().sendMessage(original, edit);
                            }
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                        }
                        break;
                    case "edit":
                        if (player.hasPermission("Peligon.Prison.Mine.Edit") || player.hasPermission("Peligon.Prison.*")) {
                            if (args.length < 2) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("mine-edit-usage")));
                                return true;
                            }
                            Mine mine = plugin.minesManager.getMine(args[1]);
                            if (mine == null) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("mine-not-found")));
                                return true;
                            }
                            if (args[2].equalsIgnoreCase("name")) {
                                if (args.length != 3) {
                                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-name")));
                                    return true;
                                }
                                if (Utils.isOnlyLetters(args[2])) {
                                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-name")));
                                    return true;
                                }
                                plugin.minesManager.getMine(args[1]).setName(args[2]);
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                        plugin.fileMessage.getConfig().getString("mine-edit").replaceAll("%mine%", args[2])));
                            } else if (args[2].equalsIgnoreCase("area")) {
                                Utils.mineCreationInProgress.put(player.getUniqueId(), args[1]);
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                        plugin.fileMessage.getConfig().getString("mine-creation-info")));
                                plugin.itemManager.addItem(player);

                            } else if (args[2].equalsIgnoreCase("spawn")) {
                                Location location = player.getLocation();
                                plugin.minesManager.getMine(args[1]).setSpawnLocation(location);
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                        plugin.fileMessage.getConfig().getString("mine-edit").replaceAll("%mine%", args[1])));
                            } else if (args[2].equalsIgnoreCase("blocks")) {
                                if (args.length != 3) {
                                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-blocks")));
                                    return true;
                                }

                                List<Material> blocks = new ArrayList<>();
                                for (String block : args[2].split(",")) {
                                    Material material = Material.getMaterial(block);
                                    if (material == null || !material.isBlock()) continue;
                                    if (plugin.minesManager.getMine(args[1]).getBlocks().contains(material) || blocks.contains(material)) continue;
                                    blocks.add(material);
                                }
                                plugin.minesManager.getMine(args[1]).setBlocks(blocks);
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                        plugin.fileMessage.getConfig().getString("mine-edit").replaceAll("%mine%", args[1])));
                            } else {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("mine-edit-usage")));
                            }
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                        }
                        break;
                    case "reset":
                        if (player.hasPermission("Peligon.Prison.Mine.Reset") || player.hasPermission("Peligon.Prison.*")) {
                            if (args.length != 2) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("specify-name")));
                                return true;
                            }
                            Mine mine = plugin.minesManager.getMine(args[1]);
                            if (mine == null) {
                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("mine-not-found")));
                                return true;
                            }
                            plugin.minesManager.resetMine(mine.getName());
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("mine-reset").replaceAll("%mine%", mine.getName())));
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                        }
                        break;
                    case "resetall":
                        if (player.hasPermission("Peligon.Prison.Mine.ResetAll") || player.hasPermission("Peligon.Prison.*")) {
                            plugin.minesManager.resetAllMines();
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                    plugin.fileMessage.getConfig().getString("mine-reset-all")));
                        } else {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                        }
                        break;
                }
            } else {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
