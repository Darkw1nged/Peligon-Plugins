package net.peligon.PeligonTNTTag.libaries;

import net.peligon.PeligonTNTTag.libaries.util.ChatUtil;
import net.peligon.PeligonTNTTag.libaries.util.InventoryUtil;

public class Utils {

    public static Utils getInstance;
    private final ChatUtil chatUtil;
    private final InventoryUtil inventoryUtil;

    public Utils() {
        getInstance = this;
        chatUtil = new ChatUtil();
        inventoryUtil = new InventoryUtil();
    }

    public ChatUtil getChatUtil() {
        return chatUtil;
    }

    public InventoryUtil getInventoryUtil() {
        return inventoryUtil;
    }
}
