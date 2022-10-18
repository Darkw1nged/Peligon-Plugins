package net.peligon.PeligonPolls.menus;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.peligon.PeligonPolls.Main;
import net.peligon.PeligonPolls.libaries.Utils;
import net.peligon.PeligonPolls.libaries.struts.PaginatedMenu;
import net.peligon.PeligonPolls.libaries.struts.Poll;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class menuPolls extends PaginatedMenu {

    private final Main plugin = Main.getInstance;
    private List<Poll> poll_Position;

    public menuPolls(Player player) {
        super(player);
        poll_Position = new ArrayList<>();
    }

    @Override
    public String getMenuName() {
        return plugin.getConfig().getString("polls-inventory.title");
    }

    @Override
    public int getSlots() {
        if (Utils.polls.isEmpty()) return 9;
        if (Utils.polls.size() > 36) {
            return 54;
        } else if (Utils.polls.size() > 27) {
            return 45;
        } else if (Utils.polls.size() > 18) {
            return 36;
        }  else if (Utils.polls.size() > 9) {
            return 27;
        } else if (Utils.polls.size() >= 1) {
            return 18;
        }
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        if (item.equals(CustomItems.closeButton())) {
            player.closeInventory();
        } else if (item.equals(CustomItems.refreshButton())) {
            player.closeInventory();
            new menuPolls(player).open();
        } else if (item.equals(CustomItems.nextPage())) {
            if (!((index + 1) >= Utils.polls.size())) {
                page = page + 1;
            }
            super.open();
        } else if (item.equals(CustomItems.previousPage())) {
            if (page != 0) {
                page = page - 1;
            }
            super.open();
        } else if (item.equals(CustomItems.background())) {
            event.setCancelled(true);
        } else {
            Utils.polls_temp.put(player.getUniqueId(), event.getSlot() * (page + 1));
            Utils.poll_ID.put(player.getUniqueId(), poll_Position.get(event.getSlot()));

            TextComponent yesComponent = new TextComponent(Utils.chatColor("&a&l [ YES ] "));
            TextComponent noComponent = new TextComponent(Utils.chatColor("&c&l [ NO ]"));
            TextComponent cancelComponent = new TextComponent(Utils.chatColor("&4&l [ CANCEL ]"));

            yesComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/poll vote " + poll_Position.get(event.getSlot()).getMessageID() + " yes"));
            noComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/poll vote " + poll_Position.get(event.getSlot()).getMessageID() + " no"));
            cancelComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/poll vote cancel"));

            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                    plugin.fileMessage.getConfig().getString("vote-status")));
            player.spigot().sendMessage(yesComponent, noComponent, cancelComponent);

            player.closeInventory();
        }
    }

    @Override
    public void setMenuItems() {
        for (int i = 0; i < getMaxItemsPerPage(); i++) {
            index = getMaxItemsPerPage() * page + i;
            if (index >= Utils.polls.size()) break;
            if (Utils.polls.get(index) != null) {
                poll_Position.add(Utils.polls.get(index));
                inventory.setItem(i, CustomItems.pollItem(Utils.polls.get(index)));
            }
        }

        inventory.setItem(getLines() == 1 ? 0 : (9 * getLines()) - 9, CustomItems.background());
        inventory.setItem(getLines() == 1 ? 1 : (9 * getLines()) - 8, CustomItems.background());
        inventory.setItem(getLines() == 1 ? 2 : (9 * getLines()) - 7, page == 0 ? CustomItems.background() : CustomItems.previousPage());
        inventory.setItem(getLines() == 1 ? 3 : (9 * getLines()) - 6, CustomItems.background());
        inventory.setItem(getLines() == 1 ? 4 : (9 * getLines()) - 5, CustomItems.closeButton());
        inventory.setItem(getLines() == 1 ? 5 : (9 * getLines()) - 4, CustomItems.background());
        inventory.setItem(getLines() == 1 ? 6 : (9 * getLines()) - 3, !((index + 1) >= Utils.polls.size()) ? CustomItems.nextPage() : CustomItems.background());
        inventory.setItem(getLines() == 1 ? 7 : (9 * getLines()) - 2, CustomItems.background());
        inventory.setItem(getLines() == 1 ? 8 : (9 * getLines()) - 1, CustomItems.refreshButton());
    }

    private int getLines() {
        if (this.inventory.getSize() == 54) {
            return 6;
        } else if (this.inventory.getSize() == 45) {
            return 5;
        } else if (this.inventory.getSize() == 36) {
            return 4;
        } else if (this.inventory.getSize() == 27) {
            return 3;
        } else if (this.inventory.getSize() == 18) {
            return 2;
        }
        return 1;
    }

}
