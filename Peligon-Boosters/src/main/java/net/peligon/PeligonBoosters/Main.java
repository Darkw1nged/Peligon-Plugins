package net.peligon.PeligonBoosters;

import com.djrapitops.vaultevents.events.economy.PlayerDepositEvent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    public static Main getInstance;
    private static Economy econ = null;


    public void onEnable() {

        getInstance = this;

        if (!setupEconomy() ) {

            return;
        }

        getServer().getPluginManager().registerEvents(this, this);

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public void onDisable() {
    }


    @EventHandler
    public void onJoin(PlayerDepositEvent event) {
        System.out.println(event.getAmount());
        System.out.println(event.getResponse());
        System.out.println(event.getOfflinePlayer());
    }

    public static Economy getEconomy() {
        return econ;
    }


}
