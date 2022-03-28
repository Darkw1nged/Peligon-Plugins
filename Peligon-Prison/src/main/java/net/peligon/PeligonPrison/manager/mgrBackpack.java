package net.peligon.PeligonPrison.manager;

import net.peligon.PeligonPrison.Main;
import net.peligon.PeligonPrison.libaries.Utils;
import net.peligon.PeligonPrison.libaries.storage.SQLite;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class mgrBackpack {

    private final Main plugin = Main.getInstance;

    public ItemStack backpack;
    public static mgrBackpack getInstance;
    public mgrBackpack() {
        getInstance = this;

        ItemStack item = new ItemStack(Material.getMaterial(plugin.getConfig().getString("Backpack.Item.material")));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chatColor(plugin.getConfig().getString("Backpack.Item.name")));
        meta.setLore(Utils.getConvertedLore(plugin.getConfig(), "Backpack.Item"));
        item.setItemMeta(meta);
        backpack = item;
    }
    /**
     * Checking if player has any data in database
     *
     * @return if data exists in database
     */
    public boolean hasData(OfflinePlayer player) {
        String query = "SELECT 1 FROM backpack WHERE uuid='" + player.getUniqueId() + "';";
        try {
            PreparedStatement statement = SQLite.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Creates a backpack for the user if they do not already have one.
     *
     * @param player of the player
     * @param contents to open account with
     */
    public void createBackpack(OfflinePlayer player, ItemStack[] contents) {
        if (hasData(player)) return;
        if (contents == null) return;
        String uuid = String.valueOf(player.getUniqueId());
        String query = "INSERT INTO backpack values('" + uuid + "', " + contents + ");";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the player's backpack contents
     *
     * @param contents New backpack contents
     */
    public void setBackpack(OfflinePlayer player, ItemStack[] contents) {
        if (!hasData(player)) return;
        if (contents == null) return;
        String query = "UPDATE backpack SET contents='" + contents + "' WHERE uuid='" + player.getUniqueId() + "';";
        try {
            Statement statement = SQLite.connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds an item to the player's backpack
     *
     * @param item Item to add
     */
    public void addItem(OfflinePlayer player, ItemStack item) {
        if (!hasData(player)) return;
        ItemStack[] contents = getBackpack(player);
        ItemStack[] newContents = new ItemStack[contents.length + 1];
        System.arraycopy(contents, 0, newContents, 0, contents.length);
        newContents[newContents.length - 1] = item;
        setBackpack(player, newContents);
    }

    /**
     * Removes an item from the player's backpack
     *
     * @param item Item to remove
     */
    public void removeItem(OfflinePlayer player, ItemStack item) {
        if (!hasData(player)) return;
        ItemStack[] contents = getBackpack(player);
        ItemStack[] newContents = new ItemStack[contents.length - 1];
        int i = 0;
        for (ItemStack content : contents) {
            if (content.equals(item)) continue;
            newContents[i] = content;
            i++;
        }
        setBackpack(player, newContents);
    }

    /**
     * Gets the player's backpack contents
     *
     * @return Player's backpack contents
     */
    public ItemStack[] getBackpack(OfflinePlayer player) {
        if (!hasData(player)) return null;
        String query = "SELECT * FROM server WHERE uuid='" + player.getUniqueId() + "';";
        try {

            PreparedStatement statement = SQLite.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return (ItemStack[]) rs.getObject("contents");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
