package net.peligon.PeligonEconomy.commands;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.rmi.CORBA.Util;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class cmdWithdraw implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("withdraw")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("console")));
                return true;
            }
            Player player = (Player)sender;
            if (player.hasPermission("Peligon.Economy.Withdraw") || player.hasPermission("Peligon.Economy.*")) {
                if (args.length < 1) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("withdraw-usage")));
                    return true;
                }
                double amount;
                // ---- [ Getting the amount ] ----
                try {
                    amount = Double.parseDouble(args[0]);
                } catch (Exception e) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                    return true;
                }
                if (amount < 0) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("invalid-amount")));
                    return true;
                }

                // ---- [ Checking accounts of players ] ----
                if (!plugin.Economy.hasAccount(player)) {
                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("account-error")
                            .replaceAll("%player%", player.getName())
                            .replaceAll("%target%", player.getName())));
                    return true;
                }
                if (!plugin.Economy.hasEnoughCash(player, amount)) {
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("not-enough-money")));
                    return true;
                }

                NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
                String converted = nf.format(amount);

                ItemStack item = new ItemStack(Material.getMaterial(plugin.getConfig().getString("Items.withdraw-cash.item").toUpperCase()));
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(Utils.chatColor(plugin.getConfig().getString("Items.withdraw-cash.name")));

                List<String> lore = new ArrayList<>();
                for (String value : plugin.getConfig().getStringList("Items.withdraw-cash.lore")) {
                    lore.add(Utils.chatColor(value)
                            .replaceAll("%amount%", "" + converted)
                            .replaceAll("%player%", "" + player.getName())
                            .replaceAll("%display_player%", "" + player.getDisplayName())
                            .replaceAll("%transaction%", "" + UUID.randomUUID().toString().split("-")[0] + "-" + UUID.randomUUID().toString().split("-")[3]));
                }
                meta.setLore(lore);
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                item.setItemMeta(meta);

                // ---- [ Adding item to inventory ] ----
                player.getInventory().addItem(item);

                // ---- [ Removing money ] ----
                plugin.Economy.RemoveAccount(player, amount);
            } else {
                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
                return true;
            }
        }
        return false;
    }

}
