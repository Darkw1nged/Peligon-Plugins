package net.peligon.PeligonPrison.manager;

import net.peligon.PeligonPrison.Main;
import net.peligon.PeligonPrison.libaries.CustomConfig;
import net.peligon.PeligonPrison.libaries.Utils;
import net.peligon.PeligonPrison.struts.Gang;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import javax.security.auth.login.Configuration;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class mgrGangs {

    public static mgrGangs getInstance;
    public mgrGangs() {
        getInstance = this;
    }

    public Gang getGang(Player player) {
        for (Gang gang : Utils.gangs)
            if (gang.getOwner().equals(player.getUniqueId()) || gang.getMembers().contains(player.getUniqueId()))
                return gang;
        return null;
    }

    public boolean isOwner(Player player) {
        for (Gang gang : Utils.gangs)
            if (gang.getOwner().equals(player.getUniqueId()))
                return true;
        return false;
    }

    public boolean isMember(Player player) {
        for (Gang gang : Utils.gangs)
            if (gang.getMembers().contains(player.getUniqueId()))
                return true;
        return false;
    }

    public boolean isBanned(Player player) {
        for (Gang gang : Utils.gangs)
            if (gang.getBanned().contains(player.getUniqueId()))
                return true;
        return false;
    }

    public Gang getGang(String name) {
        for (Gang gang : Utils.gangs)
            if (gang.getName().equals(name))
                return gang;
        return null;
    }

    public String getGangName(Player player) {
        for (Gang gang : Utils.gangs)
            if (gang.getOwner().equals(player.getUniqueId()) || gang.getMembers().contains(player.getUniqueId()))
                return gang.getName();
        return null;
    }

    public void addMember(Player player, String gangName) {
        Gang gang = getGang(gangName);
        if (gang != null) {
            gang.addMember(player.getUniqueId());
            gang.addMemberName(player.getName());
        }

    }

    public void removeMember(Player player, String gangName) {
        Gang gang = getGang(gangName);
        if (gang != null) {
            gang.removeMember(player.getUniqueId());
            gang.removeMemberName(player.getName());
        }
    }

    public void addBanned(Player player, String gangName) {
        Gang gang = getGang(gangName);
        if (gang != null) {
            removeMember(player, gangName);
            gang.addBanned(player.getUniqueId());
            gang.addBannedName(player.getName());
        }

    }

    public void removeBanned(Player player, String gangName) {
        Gang gang = getGang(gangName);
        if (gang != null) {
            gang.removeBanned(player.getUniqueId());
            gang.removeBannedName(player.getName());
        }
    }

    public void disbandGang(String gangName) {
        Gang gang = getGang(gangName);
        if (gang != null) {
            Utils.gangs.remove(gang);
            File file = new File(Main.getInstance.getDataFolder(), "gangs/" + gangName + ".yml");
            if (file.exists())
                file.delete();
        }

    }

    public void setGangDescription(String gangName, String description) {
        Gang gang = getGang(gangName);
        if (gang != null)
            gang.setDescription(description);
    }

    public void openGang(String gangName, boolean open) {
        Gang gang = getGang(gangName);
        if (gang != null)
            gang.setIsPublic(open);
    }

    public void saveGangs() {
        for (Gang gang : Utils.gangs) {
            CustomConfig config = new CustomConfig(Main.getInstance, "gangs/" + gang.getName(), false);
            YamlConfiguration data = config.getConfig();

            data.set("name", gang.getName());
            data.set("owner.uuid", gang.getOwner().toString());
            data.set("owner.name", gang.getOwner().toString());
            data.set("description", gang.getDescription());
            data.set("tag", gang.getTag());

            List<String> members = new ArrayList<>();
            for (UUID uuid : gang.getMembers()) {
                members.add(uuid.toString());
            }
            data.set("members.uuids", members);
            data.set("members.names", gang.getMemberNames());

            List<String> banned = new ArrayList<>();
            for (UUID uuid : gang.getBanned()) {
                banned.add(uuid.toString());
            }
            data.set("banned.uuid", banned);
            data.set("banned.names", gang.getBannedNames());
            data.set("isPublic", gang.isPublic());

            config.saveConfig();
        }
    }

    public void loadGangs() {
        File[] files = new File(Main.getInstance.getDataFolder() + File.separator + "gangs").listFiles();
        if (files != null)
            for (File file : files) {
                CustomConfig config = new CustomConfig(Main.getInstance, "gangs/" + file.getName(), false);
                YamlConfiguration data = config.getConfig();

                String name = data.getString("name");
                UUID owner = UUID.fromString(data.getString("owner.uuid"));
                String ownerName = data.getString("owner.name");
                String description = data.getString("description");
                String tag = data.getString("tag");

                List<UUID> members = new ArrayList<>();
                for (String uuid : data.getStringList("members.uuids")) {
                    members.add(UUID.fromString(uuid));
                }
                List<String> memberNames = new ArrayList<>(data.getStringList("members.names"));

                List<UUID> banned = new ArrayList<>();
                for (String uuid : data.getStringList("banned.uuid")) {
                    banned.add(UUID.fromString(uuid));
                }
                List<String> bannedNames = new ArrayList<>(data.getStringList("banned.names"));

                boolean isPublic = data.getBoolean("isPublic");

                Utils.gangs.add(new Gang(name, owner, ownerName, description, tag, members, memberNames, banned, bannedNames, isPublic));
            }
    }

}
