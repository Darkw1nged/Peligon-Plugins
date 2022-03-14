package net.peligon.PeligonTNTTag.libariesV2;

public class Utilss {

    public static Utilss getInstance;
    private final ChatUtil chatUtil;
    private final InventoryUtil inventoryUtil;

    public Utilss() {
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
