package net.peligon.PeligonPrison.manager;

import net.peligon.PeligonPrison.Main;
import net.peligon.PeligonPrison.libaries.CustomConfig;
import net.peligon.PeligonPrison.libaries.Utils;
import net.peligon.PeligonPrison.struts.Mine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class mgrMines {

    public static mgrMines getInstance;
    public mgrMines() {
        getInstance = this;
    }

    public Mine getMine(String name) {
        for (Mine mine : Utils.mines) {
            if (mine.getName().equals(name)) {
                return mine;
            }
        }
        return null;
    }

    public Location getMineSpawn(String name) {
        for (Mine mine : Utils.mines) {
            if (mine.getName().equals(name)) {
                return mine.getSpawnLocation();
            }
        }
        return null;
    }

    public boolean hasPermission(String name, Player player) {
        for (Mine mine : Utils.mines) {
            if (mine.getName().equals(name)) {
                if (mine.getPermission().equals("")) {
                    return true;
                } else {
                    if (player.hasPermission(mine.getPermission()) || player.hasPermission("Peligon.Prison.*")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public List<Material> getMineBlocks(String name) {
        for (Mine mine : Utils.mines) {
            if (mine.getName().equals(name)) {
                return mine.getBlocks();
            }
        }
        return null;
    }

    public void addBlock(String name, Material block) {
        for (Mine mine : Utils.mines) {
            if (mine.getName().equals(name)) {
                List<Material> blocks = mine.getBlocks();
                blocks.add(block);
                mine.setBlocks(blocks);
            }
        }
    }

    public void removeBlock(String name, Material block) {
        for (Mine mine : Utils.mines) {
            if (mine.getName().equals(name)) {
                List<Material> blocks = mine.getBlocks();
                blocks.remove(block);
                mine.setBlocks(blocks);
            }
        }
    }

    public List<Block> getMinesRemainingBlocks(String name) {
        for (Mine mine : Utils.mines) {
            if (mine.getName().equals(name)) {
                Location location1 = mine.getCornerOne();
                Location location2 = mine.getCornerTwo();
                List<Block> blocks = new ArrayList<>();
                for (int x = location1.getBlockX(); x <= location2.getBlockX(); x++) {
                    for (int y = location1.getBlockY(); y <= location2.getBlockY(); y++) {
                        for (int z = location1.getBlockZ(); z <= location2.getBlockZ(); z++) {
                            Block block = location1.getWorld().getBlockAt(x, y, z);
                            if (!blocks.contains(block)) {
                                blocks.add(block);
                            }
                        }
                    }
                }
                return blocks;
            }
        }
        return null;
    }

    public double getMinesBrokenBlocks(String name) {
        for (Mine mine : Utils.mines) {
            if (mine.getName().equals(name)) {
                List<Block> blocks = getMinesRemainingBlocks(name);
                List<Block> brokenBlocks = new ArrayList<>(blocks.size());
                for (Block block : blocks) {
                    if (block.getType().equals(Material.AIR)) {
                        brokenBlocks.add(block);
                    }
                }
                return (double) brokenBlocks.size() / blocks.size() * 100;
            }
        }
        return 0;
    }

    public List<Mine> getMines() {
        return Utils.mines;
    }

    public String getTimeUntilReset(String name) {
        for (Mine mine : Utils.mines) {
            if (mine.getName().equals(name)) {
                long lastReset = mine.getLastReset();
                long currentTime = System.currentTimeMillis();
                // Calculate the time until the next reset
                long timeUntilReset = (lastReset + mine.getResetTime()) - currentTime;
                // Convert the time to a readable format
                long hours = TimeUnit.MILLISECONDS.toHours(timeUntilReset);
                timeUntilReset -= TimeUnit.HOURS.toMillis(hours);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(timeUntilReset);
                timeUntilReset -= TimeUnit.MINUTES.toMillis(minutes);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(timeUntilReset);
                timeUntilReset -= TimeUnit.SECONDS.toMillis(seconds);
                long milliseconds = timeUntilReset;
                // Return the time until the next reset
                return String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, milliseconds);
            }
        }
        return null;
    }

    public void addMine(Mine mine) {
        Utils.mines.add(mine);
    }

    public void removeMine(String name) {
        for (Mine mine : Utils.mines) {
            if (mine.getName().equals(name)) {
                Utils.mines.remove(mine);
                return;
            }
        }
    }

    public void resetMine(String name) {
        for (Mine mine : Utils.mines) {
            if (mine.getName().equals(name)) {
                List<Material> blocks = mine.getBlocks();
                for (Block areaBlock : getMinesRemainingBlocks(name)) {
                    Random rand = new Random();
                    Material randomMaterial = blocks.get(rand.nextInt(blocks.size()));
                    areaBlock.setType(randomMaterial);
                }
                mine.setLastReset(System.currentTimeMillis());
            }
        }
    }

    public void resetMines() {
        for (Mine mine : Utils.mines) {
            resetMine(mine.getName());
        }
    }

    public void saveMines() {
        for (Mine mine : Utils.mines) {
            CustomConfig config = new CustomConfig(Main.getInstance, mine.getName(), "mines");
            YamlConfiguration data = config.getConfig();
            data.set("name", mine.getName());
            data.set("permission.hasPermission", mine.hasPermission());
            data.set("permission.permission", mine.getPermission());
            data.set("spawn.world", mine.getSpawnLocation().getWorld().getName());
            data.set("spawn.x", mine.getSpawnLocation().getX());
            data.set("spawn.y", mine.getSpawnLocation().getY());
            data.set("spawn.z", mine.getSpawnLocation().getZ());
            data.set("spawn.yaw", mine.getSpawnLocation().getYaw());
            data.set("spawn.pitch", mine.getSpawnLocation().getPitch());
            data.set("blocks", mine.getBlocks());
            data.set("reset.last", mine.getLastReset());
            data.set("reset.delay", mine.getResetTime());
            data.set("cornerOne", mine.getCornerOne());
            data.set("cornerTwo", mine.getCornerTwo());
            config.saveConfig();
        }
    }

    public void loadMines() {
        File[] files = new File(Main.getInstance.getDataFolder() + File.separator + "gangs").listFiles();
        if (files != null)
            for (File file : files) {
                CustomConfig config = new CustomConfig(Main.getInstance, file.getName(), "mines");
                YamlConfiguration data = config.getConfig();
                String name = data.getString("name");

                boolean hasPermission = data.getBoolean("permission.hasPermission");
                String permission = data.getString("permission.permission");

                World world = Bukkit.getWorld(data.getString("spawn.world"));
                double x = data.getDouble("spawn.x");
                double y = data.getDouble("spawn.y");
                double z = data.getDouble("spawn.z");
                float yaw = data.getInt("spawn.yaw");
                float pitch = data.getInt("spawn.pitch");
                Location spawn = new Location(world, x, y, z, yaw, pitch);

                List<Material> blocks = new ArrayList<>();
                for (String block : data.getStringList("blocks")) {
                    blocks.add(Material.valueOf(block));
                }

                long resetTime = data.getLong("reset.delay");
                long lastReset = data.getLong("reset.last");

                Location cornerOne = data.getLocation("cornerOne");
                Location cornerTwo = data.getLocation("cornerTwo");

                Mine mine = new Mine(name, hasPermission, permission, spawn, blocks, lastReset, resetTime, cornerOne, cornerTwo);
                Utils.mines.add(mine);
            }
    }


}
