package net.peligon.InstantRespawn.Utilities;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Hopper;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class WorldUtils {

    /**
     * Getting the time in the world.
     *
     * @param world The world.
     * @return The time in the world.
     */
    public String getTime(World world) {
        long rawTime = world.getTime();
        long hours = rawTime / 1000 + 6;
        long rawMinutes = (rawTime % 1000) * 60 / 1000;
        String clock = "am";

        if (hours >= 12) { hours -= 12; clock = "pm"; }
        if (hours >= 12) { hours -= 12; clock = "am"; }
        if (hours == 0) hours = 12;

        String minutes = "0" + rawMinutes;
        minutes = minutes.substring(minutes.length() - 2);
        return hours + ":" + minutes + clock;
    }

    /**
     * Getting the time in the world.
     *
     * @param world The world.
     * @param coloredTime change am : pm to be colored
     * @return The time in the world.
     */
    public String getTime(World world, ChatColor coloredTime) {
        long rawTime = world.getTime();
        long hours = rawTime / 1000 + 6;
        long rawMinutes = (rawTime % 1000) * 60 / 1000;
        String clock = "am";

        if (hours >= 12) { hours -= 12; clock = "pm"; }
        if (hours >= 12) { hours -= 12; clock = "am"; }
        if (hours == 0) hours = 12;

        String minutes = "0" + rawMinutes;
        minutes = minutes.substring(minutes.length() - 2);
        return hours + ":" + minutes + coloredTime + clock;
    }

    /**
     * Check if a crop is fully grown.
     *
     * @param block The block to check.
     * @return True if the crop is fully grown.
     */
    public boolean isFullyGrown(Block block) {
        return ((Ageable) block.getBlockData()).getAge() == ((Ageable) block.getBlockData()).getMaximumAge();
    }

    /**
     * Check if a hopper is empty.
     *
     * @param hopper The hopper to check.
     * @return True if the hopper is empty.
     */
    public boolean isHopperEmpty(Hopper hopper) {
        for (ItemStack itemStack : hopper.getInventory().getContents()) {
            if (itemStack != null)
                return false;
        }
        return true;
    }

    /**
     * Get all loaded chunks in a world.
     *
     * @param world The world.
     * @return The loaded chunks.
     */
    public List<Chunk> getLoadedChunks(World world) {
        return Arrays.asList(world.getLoadedChunks());
    }

    /**
     * Get a number of loaded chunks in a world.
     *
     * @param world The world.
     * @return The number of loaded chunks.
     */
    public int getLoadedChunksCount(World world) {
        return world.getLoadedChunks().length;
    }

    /**
     * Get all entities in a chunk.
     *
     * @param chunk The chunk to get the entities from.
     * @return The entities in the chunk.
     */
    public List<Entity> getEntitiesInChunk(Chunk chunk, boolean excludePlayers) {
        List<Entity> entities = Arrays.asList(chunk.getEntities());
        if (excludePlayers) {
            for (int i = 0; i < entities.size(); i++) {
                if (entities.get(i) instanceof LivingEntity) {
                    entities.remove(i);
                    i--;
                }
            }
        }
        return entities;
    }

    /**
     * Get all animals in a chunk.
     *
     * @param chunk The chunk to get the entities from.
     * @return The entities in the chunk.
     */
    public List<Entity> getAnimalsInChunk(Chunk chunk) {
        List<Entity> entities = Arrays.asList(chunk.getEntities());
        for (int i = 0; i < entities.size(); i++) {
            if (!(entities.get(i) instanceof Animals)) {
                entities.remove(i);
                i--;
            }
        }
        return entities;
    }

    /**
     * Get all mobs in a chunk.
     *
     * @param chunk The chunk to get the entities from.
     * @return The entities in the chunk.
     */
    public List<Entity> getMobsInChunk(Chunk chunk) {
        List<Entity> entities = Arrays.asList(chunk.getEntities());
        for (int i = 0; i < entities.size(); i++) {
            if (!(entities.get(i) instanceof Mob)) {
                entities.remove(i);
                i--;
            }
        }
        return entities;
    }

}
