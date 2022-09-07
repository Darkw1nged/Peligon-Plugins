package net.peligon.PeligonEconomy.libaries.struts;

import org.bukkit.entity.Player;

import java.util.UUID;

public class MenuOwnerUtil {

    private final Player owner;
    private final UUID ownerUUID;


    public MenuOwnerUtil(Player player) {
        this.owner = player;
        this.ownerUUID = player.getUniqueId();
    }

    public Player getOwner() {
        return owner;
    }
    public UUID getOwnerUUID() { return ownerUUID; }

}
