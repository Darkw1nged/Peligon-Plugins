package net.peligon.PeligonEconomy.libaries.struts;

public abstract class PaginatedMenu extends Menu {

    protected boolean navigation;
    protected int page = 0;
    protected int maxItemsPerPage = !navigation ? 45 : 54; // Leaving 9 spaces at the bottom
    protected int index = 0;

    public PaginatedMenu(MenuOwnerUtil menuOwnerUtil) {
        super(menuOwnerUtil, false);
    }

    public PaginatedMenu(MenuOwnerUtil menuOwnerUtil, boolean hasNavigation) {
        super(menuOwnerUtil, hasNavigation);
    }

    public int getMaxItemsPerPage() {
        return maxItemsPerPage;
    }

}
