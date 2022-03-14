package net.peligon.PeligonTNTTag.libaries.Instances;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.session.ClipboardHolder;
import net.peligon.PeligonTNTTag.Main;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

public class Map {

    private final Main plugin = Main.getInstance;

    private World world;
    private Location spawnLocation;
    private World lobbyWorld;
    private Location lobbyLocation;


    public static Map instance;
    public Map() { instance = this; }

    public void createMap() throws IOException {
        File[] files = new File(plugin.getDataFolder().getAbsolutePath() + "/maps/").listFiles();
        if (files == null) return;
        File schematicFile = files[new Random().nextInt(files.length)];

        ClipboardFormat clipboardFormat = ClipboardFormats.findByFile(schematicFile);
        Clipboard clipboard;
        try (ClipboardReader reader = clipboardFormat.getReader(new FileInputStream(schematicFile))) {
            clipboard = reader.read();
        }

        World world = new WorldCreator("new map").type(WorldType.FLAT).generatorSettings("2;0;1;").createWorld();

        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession((com.sk89q.worldedit.world.World) world, -1)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .ignoreAirBlocks(true)
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }
    }

}
