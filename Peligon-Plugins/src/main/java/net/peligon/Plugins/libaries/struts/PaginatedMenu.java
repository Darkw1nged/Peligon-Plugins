package net.peligon.Plugins.libaries.struts;

import org.bukkit.entity.Player;

public abstract class PaginatedMenu extends Menu {

    protected boolean navigation;
    protected int page = 0;
    protected int maxItemsPerPage = !navigation ? 45 : 54; // Leaving 9 spaces at the bottom
    protected int index = 0;

    public PaginatedMenu(Player player) {
        super(player, false);
    }

    public PaginatedMenu(Player player, boolean hasNavigation) {
        super(player, hasNavigation);
    }

    public int getMaxItemsPerPage() {
        return maxItemsPerPage;
    }

}
