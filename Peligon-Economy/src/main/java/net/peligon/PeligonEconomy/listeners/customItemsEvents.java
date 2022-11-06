package net.peligon.PeligonEconomy.listeners;

import com.comphenix.protocol.wrappers.EnumWrappers;
import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import net.peligon.PeligonEconomy.libaries.playerUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class customItemsEvents implements Listener {

    // Getting the instance of the main class.
    private final Main plugin = Main.getInstance;

    @EventHandler
    public void moneyNoteClaimed(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        // Check if the player is right-clicking with the item in their main hand.
        if (event.getHand() != null && event.getHand().equals(EquipmentSlot.OFF_HAND)) return;

        // Check if itemInMainHand equals air. If so return.
        if (itemInMainHand.getType().equals(Material.AIR)) return;

        // Return if itemInMainHand does not have PersistentDataContainer
        if (!itemInMainHand.hasItemMeta() || !itemInMainHand.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "money-note"), PersistentDataType.STRING)) return;

        // Checking if item contains amount in PersistentDataContainer
        if (!itemInMainHand.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "amount"), PersistentDataType.DOUBLE)) return;

        // Getting the amount of money from the item.
        double amount = itemInMainHand.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "amount"), PersistentDataType.DOUBLE);

        // Check if the player has data.
        if (!playerUtils.hasData(player)) {
            return;
        }

        // Adding the money to the player's bank account.
        playerUtils.setBankBalance(player, playerUtils.getBankBalance(player) + amount);

        // Remove 1 from the item amount
        itemInMainHand.setAmount(itemInMainHand.getAmount() - 1);
        // Send the player a confirmation message.
        player.sendMessage(Utils.chatColor(plugin.languageFile.getConfig().getString("prefix") +
                plugin.languageFile.getConfig().getString("money-note-claimed"), amount));
    }
}
