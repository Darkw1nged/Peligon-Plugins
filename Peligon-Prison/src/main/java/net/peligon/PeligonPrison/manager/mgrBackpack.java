package net.peligon.PeligonPrison.manager;

import net.peligon.PeligonPrison.Main;
import net.peligon.PeligonPrison.libaries.CustomConfig;
import net.peligon.PeligonPrison.libaries.Utils;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

    public void createBackpack(Player player, ItemStack[] contents) {
        CustomConfig config = new CustomConfig(plugin, player.getUniqueId().toString(), "backpacks");
        YamlConfiguration data = config.getConfig();
        data.set("contents", contents);
        config.saveConfig();
    }

    public void setBackpack(Player player, ItemStack[] contents) {
        CustomConfig config = new CustomConfig(plugin, player.getUniqueId().toString(), "backpacks");
        YamlConfiguration data = config.getConfig();
        for (int i=0; i<contents.length; i++) {
            data.set("contents." + i, contents[i]);
        }
        config.saveConfig();
    }

    public ItemStack[] getBackpack(Player player) {
        CustomConfig config = new CustomConfig(plugin, player.getUniqueId().toString(), "backpacks");
        YamlConfiguration data = config.getConfig();
        if (data == null || !data.contains("contents")) return null;

        ItemStack[] contents = new ItemStack[54];
        for (int i=0; i<contents.length; i++) {
            contents[i] = data.getItemStack("contents." + i);
        }
        return contents;
    }


}
