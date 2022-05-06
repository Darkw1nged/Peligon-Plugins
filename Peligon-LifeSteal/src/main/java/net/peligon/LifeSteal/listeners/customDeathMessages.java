package net.peligon.LifeSteal.listeners;

import net.peligon.LifeSteal.Main;
import net.peligon.LifeSteal.libaries.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class customDeathMessages implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (plugin.getConfig().getStringList("Events").contains("custom-death-messages")) {
            if (event.getEntity().getLastDamageCause() == null) return;
            switch (event.getEntity().getLastDamageCause().getCause()) {
                case BLOCK_EXPLOSION:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("BLOCK_EXPLOSION"), player.getName())));
                    break;
                case CUSTOM:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("CUSTOM"), player.getName())));
                    break;
                case CONTACT:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("CONTACT"), player.getName())));
                    break;
                case CRAMMING:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("CRAMMING"), player.getName())));
                    break;
                case DROWNING:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("DROWNING"), player.getName())));
                    break;
                case DRAGON_BREATH:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("DRAGON_BREATH"), player.getName())));
                    break;
                case DRYOUT:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("DRYOUT"), player.getName())));
                    break;
                case ENTITY_ATTACK:
                    if (player.getKiller() != null) {
                        event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("ENTITY_ATTACK"), player.getName(), player.getKiller().getName())));
                    }
                    break;
                case ENTITY_SWEEP_ATTACK:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("ENTITY_SWEEP_ATTACK"), player.getName())));
                    break;
                case ENTITY_EXPLOSION:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("ENTITY_EXPLOSION"), player.getName())));
                    break;
                case FALLING_BLOCK:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("FALLING_BLOCK"), player.getName())));
                    break;
                case FREEZE:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("FREEZE"), player.getName())));
                    break;
                case FALL:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("FALL"), player.getName())));
                    break;
                case FIRE:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("FIRE"), player.getName())));
                    break;
                case FIRE_TICK:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("FIRE_TICK"), player.getName())));
                    break;
                case FLY_INTO_WALL:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("FLY_INTO_WALL"), player.getName())));
                    break;
                case HOT_FLOOR:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("HOT_FLOOR"), player.getName())));
                    break;
                case LAVA:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("LAVA"), player.getName())));
                    break;
                case LIGHTNING:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("LIGHTNING"), player.getName())));
                    break;
                case MAGIC:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("MAGIC"), player.getName())));
                    break;
                case MELTING:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("MELTING"), player.getName())));
                    break;
                case POISON:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("POISON"), player.getName())));
                    break;
                case PROJECTILE:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("PROJECTILE"), player.getName())));
                    break;
                case SUICIDE:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("SUICIDE"), player.getName())));
                    break;
                case STARVATION:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("STARVATION"), player.getName())));
                    break;
                case SUFFOCATION:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("SUFFOCATION"), player.getName())));
                    break;
                case THORNS:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("THORNS"), player.getName())));
                    break;
                case VOID:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("VOID"), player.getName())));
                    break;
                case WITHER:
                    event.setDeathMessage(Utils.chatColor(String.format(plugin.fileDeathMessage.getConfig().getString("WITHER"), player.getName())));
                    break;
            }
        }
    }

}
