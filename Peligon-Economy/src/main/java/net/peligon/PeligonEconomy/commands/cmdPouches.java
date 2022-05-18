package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class cmdPouches implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pouches")) {
            if (sender.hasPermission("Peligon.Economy.Pouches.Give") || sender.hasPermission("Peligon.Economy.*")) {
                if (args.length < 2) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("pouches-usage")));
                    return true;
                }
                if (args[0].equalsIgnoreCase("give")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        if (sender instanceof Player) {
                            target = (Player) sender;
                        } else {
                            sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found")));
                            return true;
                        }
                    }
                    if (target.getInventory().firstEmpty() == -1) {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-enough-space")));
                        return true;
                    }
                    String type = args[2];
                    if (type.equalsIgnoreCase("money")) {
                        String name = args[3];
                        if (plugin.filePouches.getConfig().contains("Money." + name)) {
                            ItemStack item = new ItemStack(Material.getMaterial(plugin.filePouches.getConfig().getString("Money." + name + ".item", "ENDER_CHEST")));

                            ItemMeta meta = item.getItemMeta();
                            meta.setDisplayName(Utils.chatColor(plugin.filePouches.getConfig().getString("Money." + name + ".name")));
                            meta.setLore(Utils.getConvertedLore(plugin.filePouches.getConfig(), "Money." + name));

                            PersistentDataContainer container = meta.getPersistentDataContainer();
                            container.set(new NamespacedKey(plugin, "plugin"), PersistentDataType.STRING, "PeligonEconomy");
                            container.set(new NamespacedKey(plugin, "type"), PersistentDataType.STRING, "money");
                            if (plugin.filePouches.getConfig().contains("Money." + name + ".permission-required")) {
                                container.set(new NamespacedKey(plugin, "hasPermission"), PersistentDataType.STRING, plugin.filePouches.getConfig().getString("Money." + name + ".permission-required"));
                            }
                            container.set(new NamespacedKey(plugin, "minimum"), PersistentDataType.DOUBLE, plugin.filePouches.getConfig().getDouble("Money." + name + ".values.minimum"));
                            container.set(new NamespacedKey(plugin, "maximum"), PersistentDataType.DOUBLE, plugin.filePouches.getConfig().getDouble("Money." + name + ".values.maximum"));

                            item.setItemMeta(meta);

                            if (args.length == 5) {
                                int amount = Integer.parseInt(args[4]);
                                item.setAmount(Math.min(amount, 64));
                            }

                            target.getInventory().addItem(item);
                            return true;
                        }
                    } else if (type.equalsIgnoreCase("experience") || type.equalsIgnoreCase("exp")) {
                        String name = args[3];
                        if (plugin.filePouches.getConfig().contains("Experience." + name)) {
                            ItemStack item = new ItemStack(Material.getMaterial(plugin.filePouches.getConfig().getString("Experience." + name + ".item", "ARMOR_STAND")));

                            ItemMeta meta = item.getItemMeta();
                            meta.setDisplayName(Utils.chatColor(plugin.filePouches.getConfig().getString("Experience." + name + ".name")));
                            meta.setLore(Utils.getConvertedLore(plugin.filePouches.getConfig(), "Experience." + name));

                            PersistentDataContainer container = meta.getPersistentDataContainer();
                            container.set(new NamespacedKey(plugin, "plugin"), PersistentDataType.STRING, "PeligonEconomy");
                            container.set(new NamespacedKey(plugin, "type"), PersistentDataType.STRING, "experience");
                            if (plugin.filePouches.getConfig().contains("Experience." + name + ".permission-required")) {
                                container.set(new NamespacedKey(plugin, "hasPermission"), PersistentDataType.STRING, plugin.filePouches.getConfig().getString("Experience." + name + ".permission-required"));
                            }
                            container.set(new NamespacedKey(plugin, "minimum"), PersistentDataType.INTEGER, plugin.filePouches.getConfig().getInt("Experience." + name + ".values.minimum"));
                            container.set(new NamespacedKey(plugin, "maximum"), PersistentDataType.INTEGER, plugin.filePouches.getConfig().getInt("Experience." + name + ".values.maximum"));

                            item.setItemMeta(meta);

                            if (args.length == 5) {
                                int amount = Integer.parseInt(args[4]);
                                item.setAmount(Math.min(amount, 64));
                            }

                            target.getInventory().addItem(item);
                            return true;
                        }
                    }
                }
            } else {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
