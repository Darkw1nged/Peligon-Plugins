package net.peligon.PeligonEconomy.listeners;

import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class signEvents implements Listener {

    private final Main plugin = Main.getInstance;

    @EventHandler
    public void onCreate(SignChangeEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("Peligon.Economy.Sign.Balance") || player.hasPermission("Peligon.Economy.*")) {
            String line0 = event.getLine(0);
            if (line0 == null)
                return;
            if (line0.equalsIgnoreCase("[balance]") || line0.equalsIgnoreCase("[cash]")) {
                event.setLine(0, Utils.chatColor(plugin.fileSigns.getConfig().getString("Cash.line-1")));
                event.setLine(1, Utils.chatColor(plugin.fileSigns.getConfig().getString("Cash.line-2")));
                event.setLine(2, Utils.chatColor(plugin.fileSigns.getConfig().getString("Cash.line-3")));
                event.setLine(3, Utils.chatColor(plugin.fileSigns.getConfig().getString("Cash.line-4")));
            }
            if (line0.equalsIgnoreCase("[bank]")) {
                event.setLine(0, Utils.chatColor(plugin.fileSigns.getConfig().getString("Bank.line-1")));
                event.setLine(1, Utils.chatColor(plugin.fileSigns.getConfig().getString("Bank.line-2")));
                event.setLine(2, Utils.chatColor(plugin.fileSigns.getConfig().getString("Bank.line-3")));
                event.setLine(3, Utils.chatColor(plugin.fileSigns.getConfig().getString("Bank.line-4")));
            }
        }
        if (player.hasPermission("Peligon.Economy.Sign.ATM") || player.hasPermission("Peligon.Economy.*")) {
            String line0 = event.getLine(0);
            if (line0 == null)
                return;
            if (line0.equalsIgnoreCase("[atm]")) {
                event.setLine(0, Utils.chatColor(plugin.fileSigns.getConfig().getString("ATM.line-1")));
                event.setLine(1, Utils.chatColor(plugin.fileSigns.getConfig().getString("ATM.line-2")));
                event.setLine(2, Utils.chatColor(plugin.fileSigns.getConfig().getString("ATM.line-3")));
                event.setLine(3, Utils.chatColor(plugin.fileSigns.getConfig().getString("ATM.line-4")));
            }
        }
        if (player.hasPermission("Peligon.Economy.Sign.Chestsell") || player.hasPermission("Peligon.Economy.*")) {
            String line0 = event.getLine(0);
            if (line0 != null && line0.equalsIgnoreCase("[chestsell]")) {
                org.bukkit.material.Sign sign = (org.bukkit.material.Sign) event.getBlock().getState().getData();
                Block attached = event.getBlock().getRelative(sign.getAttachedFace());
                if (attached.getType() == Material.CHEST || attached.getType() == Material.TRAPPED_CHEST) {
                    event.setLine(0, Utils.chatColor(plugin.fileSigns.getConfig().getString("Chestsell.line-1")));
                    event.setLine(1, Utils.chatColor(plugin.fileSigns.getConfig().getString("Chestsell.line-2")));
                    event.setLine(2, Utils.chatColor(plugin.fileSigns.getConfig().getString("Chestsell.line-3")));
                    event.setLine(3, Utils.chatColor(plugin.fileSigns.getConfig().getString("Chestsell.line-4")));
                } else {
                    event.setLine(0, Utils.chatColor(plugin.fileSigns.getConfig().getString("Chestsell.invalid")));
                }
            }
        }
        if (player.hasPermission("Peligon.Economy.Sign.Experience") || player.hasPermission("Peligon.Economy.*")) {
            String line0 = event.getLine(0);
            if (line0 == null)
                return;
            if (line0.equalsIgnoreCase("[experience]")) {
                event.setLine(0, Utils.chatColor(plugin.fileSigns.getConfig().getString("Experience.line-1")));
                event.setLine(1, Utils.chatColor(plugin.fileSigns.getConfig().getString("Experience.line-2")));
                event.setLine(2, Utils.chatColor(plugin.fileSigns.getConfig().getString("Experience.line-3")));
                event.setLine(3, Utils.chatColor(plugin.fileSigns.getConfig().getString("Experience.line-4")));
            }
        }
    }

    @EventHandler
    public void SignInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            Block block = event.getClickedBlock();
            if (block == null) return;
            BlockState blockState = block.getState();
            if (!(blockState instanceof org.bukkit.block.Sign)) return;

            if (isSign(block)) {
                Sign sign = (Sign)block.getState();
                String line0 = sign.getLine(0);
                if (line0.contains(Utils.chatColor(plugin.fileSigns.getConfig().getString("Chestsell.line-1")))) {
                    org.bukkit.material.Sign chestSell = (org.bukkit.material.Sign) event.getClickedBlock().getState().getData();
                    Block attached = event.getClickedBlock().getRelative(chestSell.getAttachedFace());
                    if (attached.getType().equals(Material.CHEST) || attached.getType().equals(Material.TRAPPED_CHEST)) {
                        Chest chest = (Chest)attached.getState();
                        double amount = 0;
                        if (chest.getInventory().getSize() == 54) {
                            for (int i = 0; i <= 54; i++) {
                                amount = getAmount(chest, amount, i);
                            }
                        } else if (chest.getInventory().getSize() == 27) {
                            for (int i = 0; i <= 27; i++) {
                                amount = getAmount(chest, amount, i);
                            }
                        }
                        if (amount <= 0) {
                            player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-items")));
                            return;
                        }
                        player.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("prefix") +
                                plugin.fileMessage.getConfig().getString("sold-items"), amount));
                        plugin.Economy.addAccount(player, amount);
                    }
                } else if (line0.contains(Utils.chatColor(plugin.fileSigns.getConfig().getString("Cash.line-1")))) {
                    player.performCommand("balance");
                } else if (line0.contains(Utils.chatColor(plugin.fileSigns.getConfig().getString("Bank.line-1")))) {
                    player.performCommand("balance bank");
                } else if (line0.contains(Utils.chatColor(plugin.fileSigns.getConfig().getString("ATM.line-1")))) {
                    player.performCommand("atm");
                } else if (line0.contains(Utils.chatColor(plugin.fileSigns.getConfig().getString("Experience.line-1")))) {
                    player.performCommand("experience");
                }
            }
        }
    }

    private double getAmount(Chest chest, double amount, int i) {
        try {
            ItemStack item = chest.getInventory().getItem(i);
            if (plugin.fileWorth.getConfig().contains("worth." + item.getType().name().toUpperCase())) {
                amount += plugin.fileWorth.getConfig().getDouble("worth." + item.getType().name().toUpperCase()) * item.getAmount();
            }
            item.setAmount(0);
        } catch (Exception ignored) { }
        return amount;
    }

    public static boolean isSign(Block block) {
        switch (block.getType()) {
            case SPRUCE_SIGN:
            case ACACIA_SIGN:
            case BIRCH_SIGN:
            case CRIMSON_SIGN:
            case DARK_OAK_SIGN:
            case JUNGLE_SIGN:
            case OAK_SIGN:
            case WARPED_SIGN:
            case SPRUCE_WALL_SIGN:
            case ACACIA_WALL_SIGN:
            case BIRCH_WALL_SIGN:
            case CRIMSON_WALL_SIGN:
            case DARK_OAK_WALL_SIGN:
            case JUNGLE_WALL_SIGN:
            case OAK_WALL_SIGN:
            case WARPED_WALL_SIGN:
                return true;
        }
        return false;
    }

}
