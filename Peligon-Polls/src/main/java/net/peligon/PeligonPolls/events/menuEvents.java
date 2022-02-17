package net.peligon.PeligonPolls.events;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.peligon.PeligonPolls.Main;
import net.peligon.PeligonPolls.libaries.Menu;
import net.peligon.PeligonPolls.libaries.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class menuEvents implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof Menu)) return;
        ((Menu) holder).onClick(plugin, (Player) event.getWhoClicked(), event.getSlot(), event.getClick());

        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        String inventoryName = event.getView().getTitle();

        if (item == null || item.getType() == Material.AIR) return;

        if (inventoryName.equals(Utils.chatColor(plugin.getConfig().getString("polls-inventory.title")))) {
            for (String key : plugin.getConfig().getConfigurationSection("polls-inventory.contents").getKeys(false)) {
                if (plugin.getConfig().contains("polls-inventory.contents." + key + ".item")) {
                    if (item.getType().equals(Material.getMaterial(plugin.getConfig().getString("polls-inventory.contents." + key + ".item").toUpperCase())) &&
                            item.getItemMeta().getDisplayName().equals(Utils.chatColor(plugin.getConfig().getString("polls-inventory.contents." + key + ".name")))) {
                        if (plugin.getConfig().contains("polls-inventory.contents." + key + ".type")) {
                            if ("close".equals(plugin.getConfig().getString("polls-inventory.contents." + key + ".type").toLowerCase())) {
                                player.closeInventory();
                                event.setCancelled(true);
                                return;
                            }
                        } else {
                            event.setCancelled(true);
                            return;
                        }
                    }
                } else {
                    if (item.getType().equals(Material.getMaterial(plugin.getConfig().getString("polls-inventory.poll-gui-item.item").toUpperCase()))) {
                        if (plugin.getConfig().contains("polls-inventory.contents." + key + ".type")) {
                            if ("poll".equals(plugin.getConfig().getString("polls-inventory.contents." + key + ".type").toLowerCase())) {
                                player.closeInventory();

                                Utils.polls_temp.put(player.getUniqueId(), Utils.slotToArrayIndex(event.getSlot()));
                                Utils.poll_ID.put(player.getUniqueId(), Utils.polls.get(Utils.slotToArrayIndex(event.getSlot())));

                                TextComponent yesComponent = new TextComponent(Utils.chatColor("&a&l [ YES ] "));
                                TextComponent noComponent = new TextComponent(Utils.chatColor("&c&l [ NO ]"));
                                TextComponent cancelComponent = new TextComponent(Utils.chatColor("&4&l [ CANCEL ]"));

                                yesComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/poll vote " + Utils.poll_ID.get(player.getUniqueId()).getMessageID() + " yes"));
                                noComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/poll vote " + Utils.poll_ID.get(player.getUniqueId()).getMessageID() + " no"));
                                cancelComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/poll vote cancel"));

                                player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                        plugin.fileMessage.getConfig().getString("vote-status")));
                                player.spigot().sendMessage(yesComponent, noComponent, cancelComponent);

                                return;
                            }
                        } else {
                            event.setCancelled(true);
                            return;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    private void onOpen(InventoryOpenEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof Menu)
            ((Menu) holder).onOpen(plugin, (Player) event.getPlayer());
    }

    @EventHandler
    private void onClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof Menu)
            ((Menu) holder).onClose(plugin, (Player) event.getPlayer());
    }

}
