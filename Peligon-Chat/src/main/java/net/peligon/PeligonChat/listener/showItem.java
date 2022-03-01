package net.peligon.PeligonChat.listener;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.peligon.PeligonChat.Main;
import net.peligon.PeligonChat.libaries.ReflectionUtil;
import net.peligon.PeligonChat.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;

public class showItem implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) throws InvocationTargetException, IllegalAccessException {
        if (!plugin.getConfig().getBoolean("show-item.enabled", true)) return;

        Player player = event.getPlayer();

        if (plugin.getConfig().getBoolean("show-item.requires-permission", true)) {
            if (player.hasPermission("Peligon.Chat.showItem") || player.hasPermission("Peligon.Chat.*")) {
                if (player.getInventory().getItemInMainHand().getType() == Material.AIR) return;
                if (!event.getMessage().contains("[item]")) return;

                String itemJson = convertItemStackToJson(player.getInventory().getItemInMainHand());
                BaseComponent[] hoverEventComponents = new BaseComponent[]{
                        new TextComponent(itemJson)
                };

                TextComponent itemName = new TextComponent(Utils.chatColor(
                        plugin.getConfig().getString("show-item.show-in-chat").replaceAll("%item_name%",
                                player.getInventory().getItemInMainHand().getItemMeta().getDisplayName())));
                itemName.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, hoverEventComponents));

                event.setMessage(event.getMessage().replaceAll("[item]", String.valueOf(itemName)));
            }
        }
    }

    public String convertItemStackToJson(ItemStack itemStack) {
        // ItemStack methods to get a net.minecraft.server.ItemStack object for serialization
        Class<?> craftItemStackClazz = ReflectionUtil.getOBCClass("inventory.CraftItemStack");
        Method asNMSCopyMethod = ReflectionUtil.getMethod(craftItemStackClazz, "asNMSCopy", ItemStack.class);

        // NMS Method to serialize a net.minecraft.server.ItemStack to a valid Json string
        Class<?> nmsItemStackClazz = ReflectionUtil.getNMSClass("ItemStack");
        Class<?> nbtTagCompoundClazz = ReflectionUtil.getNMSClass("NBTTagCompound");
        Method saveNmsItemStackMethod = ReflectionUtil.getMethod(nmsItemStackClazz, "save", nbtTagCompoundClazz);

        Object nmsNbtTagCompoundObj; // This will just be an empty NBTTagCompound instance to invoke the saveNms method
        Object nmsItemStackObj; // This is the net.minecraft.server.ItemStack object received from the asNMSCopy method
        Object itemAsJsonObject; // This is the net.minecraft.server.ItemStack after being put through saveNmsItem method

        try {
            nmsNbtTagCompoundObj = nbtTagCompoundClazz.newInstance();
            nmsItemStackObj = asNMSCopyMethod.invoke(null, itemStack);
            itemAsJsonObject = saveNmsItemStackMethod.invoke(nmsItemStackObj, nmsNbtTagCompoundObj);
        } catch (Throwable t) {
            Bukkit.getLogger().log(Level.SEVERE, "failed to serialize itemstack to nms item", t);
            return null;
        }

        // Return a string representation of the serialized object
        return itemAsJsonObject.toString();
    }

}
