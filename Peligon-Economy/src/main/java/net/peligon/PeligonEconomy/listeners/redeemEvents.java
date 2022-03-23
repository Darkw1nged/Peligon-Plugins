package net.peligon.PeligonEconomy.listeners;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class redeemEvents implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void Interact(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack hand = player.getInventory().getItemInHand();
        if (hand.getType() == Material.AIR) return;

        ItemStack money_note = new ItemStack(Material.getMaterial(plugin.getConfig().getString("Items.withdraw-cash.item").toUpperCase()));
        ItemMeta money_meta = money_note.getItemMeta();
        money_meta.setDisplayName(Utils.chatColor(plugin.getConfig().getString("Items.withdraw-cash.name")));
        money_meta.setLore(Utils.getConvertedLore(plugin.getConfig(), "Items.withdraw-cash"));
        money_note.setItemMeta(money_meta);

        ItemStack experience_bottle = new ItemStack(Material.getMaterial(plugin.getConfig().getString("Items.withdraw-experience.item").toUpperCase()));
        ItemMeta experience_meta = money_note.getItemMeta();
        experience_meta.setDisplayName(Utils.chatColor(plugin.getConfig().getString("Items.withdraw-experience.name")));
        experience_meta.setLore(Utils.getConvertedLore(plugin.getConfig(), "Items.withdraw-experience"));
        experience_bottle.setItemMeta(experience_meta);

        ItemStack sellwand = new ItemStack(Material.getMaterial(plugin.getConfig().getString("Items.sell-wand.item").toUpperCase()));
        ItemMeta sellwand_meta = money_note.getItemMeta();
        sellwand_meta.setDisplayName(Utils.chatColor(plugin.getConfig().getString("Items.sell-wand.name")));
        sellwand_meta.setLore(Utils.getConvertedLore(plugin.getConfig(), "Items.sell-wand"));
        sellwand.setItemMeta(sellwand_meta);

        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (compareItems(hand, money_note)) {
                int line_number = 0;
                for (String line : plugin.getConfig().getStringList("Items.withdraw-cash.lore")) {
                    if (line.contains("%amount%")) break;
                    line_number++;
                }

                double amount;
                try {
                    String temp = hand.getItemMeta().getLore().get(line_number).replaceAll("\\D+", "");
                    temp = temp.replaceAll(String.valueOf(temp.charAt(temp.indexOf("&") + 1)), "");

                    amount = Double.parseDouble(temp);
                } catch (Exception e) {
                    return;
                }

                hand.setAmount(hand.getAmount() - 1);
                plugin.Economy.addAccount(player, amount);

            } else if (compareItems(hand, experience_bottle)) {
                int line_number = 0;
                for (String line : plugin.getConfig().getStringList("Items.withdraw-experience.lore")) {
                    if (line.contains("%amount%")) break;
                    line_number++;
                }

                int amount;
                try {
                    String temp = hand.getItemMeta().getLore().get(line_number).replaceAll("\\D+", "");
                    temp = temp.replaceAll(String.valueOf(temp.charAt(temp.indexOf("&") + 1)), "");

                    amount = Integer.parseInt(temp);
                } catch (Exception e) {
                    return;
                }

                hand.setAmount(hand.getAmount() - 1);
                player.setTotalExperience(player.getTotalExperience() + amount);
            }
        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (compareItems(hand, sellwand)) {
                Block block = event.getClickedBlock();
                if (block.getType() == Material.CHEST || block.getType().equals(Material.TRAPPED_CHEST)) {
                    event.setCancelled(true);

                    int line_number = 0;
                    for (String line : plugin.getConfig().getStringList("Items.sell-wand.lore")) {
                        if (line.contains("%uses%")) break;
                        line_number++;
                    }

                    Chest chest = (Chest) event.getClickedBlock().getState();
                    double amount = 0;
                    if (chest.getInventory().getSize() == 54) {
                        for (int i = 0; i <= 54; i++) {
                            amount = getAmount(chest, amount, i);
                        }
                    } else if (chest.getInventory().getSize() == 27) {
                        for (int i = 0; i <= 27; i++) {
                            amount = getAmount(chest, amount, i);
                        }
                    }

                    if (amount <= 0) {
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-items")));
                        return;
                    }

                    int uses;
                    try {
                        String temp = hand.getItemMeta().getLore().get(line_number).replaceAll("\\D+", "");
                        temp = temp.replaceAll(String.valueOf(temp.charAt(temp.indexOf("&") + 1)), "");

                        uses = Integer.parseInt(temp) - 1;

                        ItemMeta meta = hand.getItemMeta();
                        List<String> lore = new ArrayList<>();
                        for (String value : plugin.getConfig().getStringList("Items.sell-wand.lore")) {
                            lore.add(Utils.chatColor(value)
                                    .replaceAll("%uses%", ""
                                            + uses));
                        }
                        meta.setLore(lore);
                        hand.setItemMeta(meta);

                    } catch (Exception e) {
                        return;
                    }

                    player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                            plugin.fileMessage.getConfig().getString("sold-items"), amount));
                    plugin.Economy.addAccount(player, amount);
                }
            } else if (compareItems(hand, money_note)) {
                int line_number = 0;
                for (String line : plugin.getConfig().getStringList("Items.withdraw-cash.lore")) {
                    if (line.contains("%amount%")) break;
                    line_number++;
                }

                double amount;
                try {
                    String temp = hand.getItemMeta().getLore().get(line_number).replaceAll("\\D+", "");
                    temp = temp.replaceAll(String.valueOf(temp.charAt(temp.indexOf("&") + 1)), "");

                    amount = Double.parseDouble(temp);
                } catch (Exception e) {
                    return;
                }

                hand.setAmount(hand.getAmount() - 1);
                plugin.Economy.addAccount(player, amount);

            } else if (compareItems(hand, experience_bottle)) {
                int line_number = 0;
                for (String line : plugin.getConfig().getStringList("Items.withdraw-experience.lore")) {
                    if (line.contains("%amount%")) break;
                    line_number++;
                }

                int amount;
                try {
                    String temp = hand.getItemMeta().getLore().get(line_number).replaceAll("\\D+", "");
                    temp = temp.replaceAll(String.valueOf(temp.charAt(temp.indexOf("&") + 1)), "");

                    amount = Integer.parseInt(temp);
                } catch (Exception e) {
                    return;
                }

                hand.setAmount(hand.getAmount() - 1);
                player.setTotalExperience(player.getTotalExperience() + amount);
            }
            return;
        }
    }

    private double getAmount(Chest chest, double amount, int i) {
        try {
            ItemStack item = chest.getInventory().getItem(i);
            if (plugin.fileWorth.getConfig().contains("worth." + item.getType().name().toUpperCase())) {
                amount += plugin.fileWorth.getConfig().getDouble("worth." + item.getType().name().toUpperCase()) * item.getAmount();
            }
            item.setAmount(0);
        } catch (Exception ignored) { }
        return amount;
    }

    private boolean compareItems(ItemStack item, ItemStack compare) {
        return item.getItemMeta().getDisplayName().equalsIgnoreCase(compare.getItemMeta().getDisplayName()) &&
                item.getType().equals(compare.getType()) &&
                item.getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ATTRIBUTES)
                && item.getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ENCHANTS);
    }

}
