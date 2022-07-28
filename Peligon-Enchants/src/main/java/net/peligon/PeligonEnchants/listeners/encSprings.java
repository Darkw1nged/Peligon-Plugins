package net.peligon.PeligonEnchants.listeners;

import com.jeff_media.armorequipevent.ArmorEquipEvent;
import net.peligon.PeligonEnchants.libaries.CustomEnchants;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class encSprings implements Listener {

    @EventHandler
    public void onEquip(ArmorEquipEvent event) {
        Player player = event.getPlayer();
        ItemStack new_armor = event.getNewArmorPiece();
        ItemStack old_armor = event.getOldArmorPiece();

        if (old_armor != null && old_armor.hasItemMeta() && old_armor.getItemMeta().hasEnchant(CustomEnchants.SPRINGS)) {
            player.removePotionEffect(PotionEffectType.JUMP);
        }
        if (new_armor != null && new_armor.hasItemMeta() && new_armor.getType().name().toLowerCase().endsWith("_boots")) {
            if (new_armor.getItemMeta().hasEnchant(CustomEnchants.SPRINGS)) {
                if (new_armor.getItemMeta().getEnchantLevel(CustomEnchants.SPRINGS) == 1) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10000000, 1));
                } else if (new_armor.getItemMeta().getEnchantLevel(CustomEnchants.SPRINGS) == 2) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10000000, 2));
                } else if (new_armor.getItemMeta().getEnchantLevel(CustomEnchants.SPRINGS) == 3) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10000000, 3));
                }
            }
        }
    }

}
