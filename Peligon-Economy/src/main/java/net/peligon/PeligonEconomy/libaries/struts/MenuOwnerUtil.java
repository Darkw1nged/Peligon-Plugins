package net.peligon.PeligonEconomy.libaries.struts;

import org.bukkit.entity.Player;

public class MenuOwnerUtil {

    private Player owner;

    public MenuOwnerUtil(Player player) {
        this.owner = player;
    }

    public Player getOwner() {
        return owner;
    }

}
