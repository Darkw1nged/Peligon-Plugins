package net.peligon.PeligonEnchants.listeners;

import net.peligon.PeligonEnchants.libaries.CustomEnchants;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class encHeadless implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        Player target = event.getEntity().getKiller();

        if (target == null) return;

        if (!target.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.HEADLESS)) return;
        if (target.getGameMode() == GameMode.CREATIVE || target.getGameMode() == GameMode.SPECTATOR) return;

        double chance = (Math.random() * 100);
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(player);
        item.setItemMeta(meta);

        if (target.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.HEADLESS) == 1  && chance >= 80) {
            event.getDrops().add(item);
        } else if (target.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.HEADLESS) == 2 && chance >= 70) {
            event.getDrops().add(item);
        } else if (target.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(CustomEnchants.HEADLESS) == 3 && chance >= 60) {
            event.getDrops().add(item);
        }
    }

//    @EventHandler
//    public void onJoin(PlayerJoinEvent event) {
//        Player player = event.getPlayer();
//        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
//
//        Utils.addEnchant(item, CustomEnchants.HEADLESS, 1);
//        player.getInventory().addItem(item);
//    }

}
