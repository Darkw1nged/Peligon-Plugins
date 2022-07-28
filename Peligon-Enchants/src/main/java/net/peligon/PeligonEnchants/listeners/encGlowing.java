package net.peligon.PeligonEnchants.listeners;

import com.jeff_media.armorequipevent.ArmorEquipEvent;
import net.peligon.PeligonEnchants.libaries.CustomEnchants;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class encGlowing implements Listener {

    @EventHandler
    public void onEquip(ArmorEquipEvent event) {
        Player player = event.getPlayer();
        ItemStack new_armor = event.getNewArmorPiece();
        ItemStack old_armor = event.getOldArmorPiece();

        if (old_armor != null && old_armor.hasItemMeta() && old_armor.getItemMeta().hasEnchant(CustomEnchants.GLOWING)) {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
        if (new_armor != null && new_armor.hasItemMeta() && new_armor.getType().name().toLowerCase().endsWith("_helmet")) {
            if (new_armor.getItemMeta().hasEnchant(CustomEnchants.GLOWING)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 10000000, 0));
            }
        }
    }

}
