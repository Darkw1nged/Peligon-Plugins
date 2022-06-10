package net.peligon.Teams.Core;

import net.peligon.Teams.Main;
import net.peligon.Teams.libaries.CustomConfig;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.math.BigDecimal;
import java.util.*;

public class Team {

    private String name;
    private String description;
    private final String defaultTag = "&7[&e" + name + "&7]";
    private UUID leader;
    private List<UUID> members;
    private int maximumMembers;
    private Map<UUID, Ranks> ranks;
    private Map<UUID, String> tags;
    private List<UUID> banned;
    private Map<String, Location> warps;
    private Location home;
    private Double bank;
    private Map<String, Integer> upgrades;
    private List<Team> allies;
    private List<Team> enemies;
    private List<Team> truces;
    private Boolean open;

    public Team(String name, String description, UUID leader) {
        this.name = name;
        this.description = description;
        this.leader = leader;

        List<UUID> members = new ArrayList<>();
        members.add(leader);
        this.members = members;

        this.maximumMembers = Main.getInstance.getConfig().getInt("defaults.maximum-team-size");

        Map<UUID, Ranks> ranks = new HashMap<>();
        ranks.put(leader, Ranks.Leader);
        this.ranks = ranks;

        Map<UUID, String> tags = new HashMap<>();
        tags.put(leader, defaultTag);
        this.tags = tags;

        this.banned = new ArrayList<>();
        this.warps = new HashMap<>();
        this.home = null;
        this.bank = 0.0;
        this.upgrades = new HashMap<>();
        this.allies = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.truces = new ArrayList<>();
        this.ranks.put(leader, Ranks.Leader);
        this.tags.put(leader, defaultTag);
        this.members.add(leader);
        this.open = false;
        this.save();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public UUID getLeader() {
        return leader;
    }

    public List<UUID> getMembers() {
        return members;
    }

    public int getMaximumMembers() {
        return maximumMembers;
    }

    public Map<UUID, Ranks> getRanks() {
        return ranks;
    }

    public Map<UUID, String> getTags() {
        return tags;
    }

    public List<UUID> getBanned() {
        return banned;
    }

    public Map<String, Location> getWarps() {
        return warps;
    }

    public Location getHome() {
        return home;
    }

    public Double getBank() {
        return bank;
    }

    public Map<String, Integer> getUpgrades() {
        return upgrades;
    }

    public List<Team> getAllies() {
        return allies;
    }

    public List<Team> getEnemies() {
        return enemies;
    }

    public List<Team> getTruces() {
        return truces;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLeader(UUID leader) {
        this.leader = leader;
    }

    public void setMembers(List<UUID> members) {
        this.members = members;
    }

    public void setMaximumMembers(int maximumMembers) {
        this.maximumMembers = maximumMembers;
    }

    public void setRanks(Map<UUID, Ranks> ranks) {
        this.ranks = ranks;
    }

    public void setTags(Map<UUID, String> tags) {
        this.tags = tags;
    }

    public void setBanned(List<UUID> banned) {
        this.banned = banned;
    }

    public void setWarps(Map<String, Location> warps) {
        this.warps = warps;
    }

    public void setHome(Location home) {
        this.home = home;
    }

    public void setBank(Double bank) {
        this.bank = bank;
    }

    public void setUpgrades(Map<String, Integer> upgrades) {
        this.upgrades = upgrades;
    }

    public void setAllies(List<Team> allies) {
        this.allies = allies;
    }

    public void setEnemies(List<Team> enemies) {
        this.enemies = enemies;
    }

    public void setTruces(List<Team> truces) {
        this.truces = truces;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public void addMember(UUID member) {
        if (!members.contains(member)) {
            members.add(member);
            ranks.put(member, Ranks.Recruit);
            tags.put(member, defaultTag);
            save();
        }
    }

    public void removeMember(UUID member) {
        if (members.contains(member)) {
            members.remove(member);
            ranks.remove(member);
            tags.remove(member);
            save();
        }
    }

    public void increaseMaximumMembers(int amount) {
        maximumMembers += amount;
        save();
    }

    public void decreaseMaximumMembers(int amount) {
        maximumMembers -= amount;
        save();
    }

    public void resetMaximumMembers() {
        maximumMembers = Main.getInstance.getConfig().getInt("defaults.maximum-team-size");
        save();
    }

    public void setRank(UUID member, Ranks rank) {
        if (members.contains(member)) {
            ranks.put(member, rank);
            save();
        }
    }

    public void setTag(UUID member, String tag) {
        if (members.contains(member)) {
            tags.put(member, tag);
            save();
        }
    }

    public void addBanned(UUID member) {
        if (!banned.contains(member)) {
            banned.add(member);
            save();
        }
    }

    public void removeBanned(UUID member) {
        if (banned.contains(member)) {
            banned.remove(member);
            save();
        }
    }

    public void addWarp(String name, Location location) {
        if (!warps.containsKey(name)) {
            warps.put(name, location);
            save();
        }
    }

    public void removeWarp(String name) {
        if (warps.containsKey(name)) {
            warps.remove(name);
            save();
        }
    }

    public void addBank(Double amount) {
        bank += amount;
        save();
    }

    public void removeBank(Double amount) {
        bank -= amount;
        save();
    }

    public void addUpgrade(String name, int level) {
        if (!upgrades.containsKey(name)) {
            upgrades.put(name, level);
            save();
        }
    }

    public void removeUpgrade(String name) {
        if (upgrades.containsKey(name)) {
            upgrades.remove(name);
            save();
        }
    }

    public void addAlly(Team ally) {
        if (!allies.contains(ally)) {
            allies.add(ally);
            save();
        }
    }

    public void removeAlly(Team ally) {
        if (allies.contains(ally)) {
            allies.remove(ally);
            save();
        }
    }

    public void addEnemy(Team enemy) {
        if (!enemies.contains(enemy)) {
            enemies.add(enemy);
            save();
        }
    }

    public void removeEnemy(Team enemy) {
        if (enemies.contains(enemy)) {
            enemies.remove(enemy);
            save();
        }
    }

    public void addTruce(Team truce) {
        if (!truces.contains(truce)) {
            truces.add(truce);
            save();
        }
    }

    public void removeTruce(Team truce) {
        if (truces.contains(truce)) {
            truces.remove(truce);
            save();
        }
    }

    public void save() {
        CustomConfig rawConfig = new CustomConfig(Main.getInstance, name, "teams");
        YamlConfiguration configuration = rawConfig.getConfig();

        configuration.set("name", name);
        configuration.set("description", description);
        configuration.set("default-tag", defaultTag);
        configuration.set("leader", leader);

        List<String> members = new ArrayList<>();
        for (UUID member : this.members) {
            members.add(member.toString());
        }
        configuration.set("members", members);

        List<String> ranks = new ArrayList<>();
        for (Map.Entry<UUID, Ranks> entry : this.ranks.entrySet()) {
            ranks.add(entry.getKey().toString() + ":" + entry.getValue().toString());
        }
        configuration.set("ranks", ranks);

        List<String> tags = new ArrayList<>();
        for (Map.Entry<UUID, String> entry : this.tags.entrySet()) {
            tags.add(entry.getKey().toString() + ":" + entry.getValue());
        }
        configuration.set("tags", tags);

        List<String> banned = new ArrayList<>();
        for (UUID bannedUUID : this.banned) {
            banned.add(bannedUUID.toString());
        }
        configuration.set("banned", banned);

        List<String> warps = new ArrayList<>();
        for (Map.Entry<String, Location> entry : this.warps.entrySet()) {
            warps.add(entry.getKey() + ":" + entry.getValue().getWorld().getName() + ":" + entry.getValue().getX() + ":" + entry.getValue().getY() + ":" + entry.getValue().getZ());
        }
        configuration.set("warps", warps);

        if (this.home != null) {
            configuration.set("home", this.home.getWorld().getName() + ":" + this.home.getX() + ":" + this.home.getY() + ":" + this.home.getZ());
        }

        configuration.set("bank", this.bank);

        List<String> upgrades = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : this.upgrades.entrySet()) {
            upgrades.add(entry.getKey() + ":" + entry.getValue());
        }
        configuration.set("upgrades", upgrades);

        List<String> allies = new ArrayList<>();
        for (Team ally : this.allies) {
            allies.add(ally.getName());
        }
        configuration.set("allies", allies);

        List<String> enemies = new ArrayList<>();
        for (Team enemy : this.enemies) {
            enemies.add(enemy.getName());
        }
        configuration.set("enemies", enemies);

        List<String> truces = new ArrayList<>();
        for (Team truce : this.truces) {
            truces.add(truce.getName());
        }
        configuration.set("truces", truces);

        configuration.set("open", open);

        rawConfig.saveConfig();
    }

}
