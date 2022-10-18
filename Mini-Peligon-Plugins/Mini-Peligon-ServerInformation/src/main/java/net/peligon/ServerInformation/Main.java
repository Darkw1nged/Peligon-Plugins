package net.peligon.ServerInformation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Hopper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.NumberFormat;
import java.util.Locale;

public final class Main extends JavaPlugin implements CommandExecutor, Listener {

    public void onEnable() {
        saveDefaultConfig();

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new TicksPerSecond(), 100L, 1L);
        getCommand("serverinfo").setExecutor(this);
        getServer().getPluginManager().registerEvents(this, this);
        serverStartTime = System.currentTimeMillis();

        log("[Peligon Mini] ServerInformation has been enabled.");
    }

    public void onDisable() {
        log("[Peligon Mini] ServerInformation has been disabled.");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("serverinfo")) {
            if (sender instanceof Player) {
                if (!sender.getName().equals("darkwinged")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cIm sorry but you do not have permission to perform this command. Please contact the server administrators if you believe this is in error."));
                    return true;
                }
            }

            int hostileMobs = 0;
            int friendlyMobs = 0;
            int chunks = 0;
            int activeHoppers = 0;
            int inactiveHoppers = 0;
            int spawners = 0;
            int players = 0;

            for (World w : Bukkit.getWorlds()) {
                for (Chunk c : w.getLoadedChunks()) {
                    for (BlockState bt : c.getTileEntities()) {
                        if (bt instanceof org.bukkit.block.CreatureSpawner) {
                            spawners++;
                        } else if (bt instanceof Hopper) {
                            if (!isHopperEmpty((Hopper) bt)) {
                                activeHoppers++;
                            } else {
                                inactiveHoppers++;
                            }
                        }
                    }
                    for (Entity e : c.getEntities()) {
                        if (e instanceof org.bukkit.entity.Monster) {
                            hostileMobs++;
                        } else if (e instanceof org.bukkit.entity.Player) {
                            players++;
                        } else if (e instanceof org.bukkit.entity.Creature) {
                            friendlyMobs++;
                        }
                    }
                    chunks++;
                }
            }


            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7----------{ &eServer Information &7}----------"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Items on the Ground: &e" + formatAmount(Bukkit.getServer().getWorlds().get(0).getEntities().size())));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Mobs Alive: &e" + formatAmount(hostileMobs)));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Friendly-Mobs Alive: &e" + formatAmount(friendlyMobs)));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Players Online: &e" + formatAmount(players)));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Chunks Loaded: &e" + formatAmount(chunks)));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Active Hoppers: &e" + formatAmount(activeHoppers)));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Idle Hoppers: &e" + formatAmount(inactiveHoppers)));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Active Spawners: &e" + formatAmount(spawners)));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Blocks Broken: &e" + formatAmount(getConfig().getInt("Blocks-Broken"))));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Blocks Placed: &e" + formatAmount(getConfig().getInt("Blocks-Placed"))));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Uptime: &e" + getUptime()));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6TPS: &a" + getServerTPS()));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Ram Usage: &e" + formatAmount(getUsedMemory()) + "&7/&e" + formatAmount(getMaxMemory()) + "&7MB"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Free Memory: &e" + formatAmount(getMaxMemory() - getUsedMemory()) + "&7MB"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7---------------------------------------------"));
            return true;
        }
        return false;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        getConfig().set("Blocks-Broken", getConfig().getInt("Blocks-Broken") + 1);
        saveConfig();
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        getConfig().set("Blocks-Placed", getConfig().getInt("Blocks-Placed") + 1);
        saveConfig();
    }

    // Utility Methods
    private String formatAmount(int amount) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        return nf.format(amount);
    }

    private String formatAmount(long amount) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        return nf.format(amount);
    }


    private boolean isHopperEmpty(Hopper hopper) {
        for (ItemStack itemStack : hopper.getInventory().getContents()) {
            if (itemStack != null)
                return false;
        }
        return true;
    }

    private long serverStartTime;

    public long getUsedMemory() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024;
    }

    public long getMaxMemory() {
        return Runtime.getRuntime().maxMemory() / 1024 / 1024;
    }

    public String getUptime() {
        int seconds;
        int minutes;
        int hours;
        int days;

        long uptime = System.currentTimeMillis() - serverStartTime;
        seconds = (int) (uptime / 1000);
        minutes = seconds / 60;
        hours = minutes / 60;
        days = hours / 24;

        hours %= 24;
        minutes %= 60;
        seconds %= 60;

        String uptimeString = "";
        if (days > 0) uptimeString += days + " days, ";
        if (hours > 0) uptimeString += hours + " hours, ";
        if (minutes > 0) uptimeString += minutes + " minutes, ";
        if (seconds > 0) uptimeString += seconds + " seconds";
        return uptimeString;
    }

    public String getServerTPS() {
        String tps;
        if (TicksPerSecond.getTPS() < 20) {
            tps = String.format("%.0f", TicksPerSecond.getTPS());
        } else {
            tps = "20";
        }
        return tps;
    }

    private void log(String string) {
        System.out.println(string);
    }
}
