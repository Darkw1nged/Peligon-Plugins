package net.peligon.PeligonEnchants.Events;

import com.jeff_media.armorequipevent.ArmorEquipEvent;
import net.peligon.PeligonEnchants.Main;
import net.peligon.PeligonEnchants.libaries.CustomEnchants;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class encAquatic implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onEquip(ArmorEquipEvent event) {
        if (plugin.getConfig().getBoolean("Enchantments.Aquatic.enabled", true)) {
            Player player = event.getPlayer();
            ItemStack new_armor = event.getNewArmorPiece();
            ItemStack old_armor = event.getOldArmorPiece();

            if (old_armor != null && old_armor.hasItemMeta() && old_armor.getItemMeta().hasEnchant(CustomEnchants.AQUATIC)) {
                player.removePotionEffect(PotionEffectType.WATER_BREATHING);
            }
            if (new_armor != null && new_armor.hasItemMeta() && new_armor.getType().name().toLowerCase().endsWith("_helmet")) {
                if (new_armor.getItemMeta().hasEnchant(CustomEnchants.AQUATIC)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 10000000, 0));
                }
            }
        }
    }

}
