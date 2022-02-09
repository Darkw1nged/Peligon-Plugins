package net.peligon.PeligonEconomy.libaries;

import net.milkbowl.vault.economy.Economy;
import net.peligon.PeligonEconomy.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;

public class VaultHook {

    private final Main plugin = Main.getInstance;
    private final Economy provider = plugin.peligonEconomy;

    public void hook() {
        Bukkit.getServicesManager().register(Economy.class, provider, plugin, ServicePriority.Highest);
        Bukkit.getConsoleSender().sendMessage(Utils.chatColor("&aVaultAPI hooked into &bPeligon Economy"));
    }

    public void unhook() {
        Bukkit.getServicesManager().unregisterAll(plugin);
        Bukkit.getConsoleSender().sendMessage(Utils.chatColor("&aVaultAPI unhooked into &bPeligon Economy"));
    }

}
