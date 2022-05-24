package net.peligon.Teams.libaries;

import net.md_5.bungee.api.chat.hover.content.ItemSerializer;
import net.peligon.Teams.Main;
import net.peligon.Teams.libaries.teamSettings.Rank;
import net.peligon.Teams.libaries.teamSettings.Upgrade;
import net.peligon.Teams.libaries.teamSettings.Vault;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class Team {

    private final String name;
    private String description;
    private final UUID leader;
    private List<UUID> members;
    private List<UUID> banned;
    private Map<UUID, Rank> playerRanks;
    private Double teamBankVault;
    private Integer teamExperienceVault;
    private Map<Upgrade, Boolean> unlockedUpgrades;
    private final Integer maximumVaults;
    private List<Vault> vaults;

    public Team(String name, UUID leader, List<UUID> members, Map<UUID, Rank> playerRanks, Integer maximumVaults, List<Vault> vaults) {
        this.name = name;
        this.description = "";
        this.leader = leader;
        this.members = members;
        this.banned = new ArrayList<>();
        this.playerRanks = playerRanks;
        this.teamBankVault = 0.0;
        this.teamExperienceVault = 0;

        this.unlockedUpgrades = new HashMap<>();
        for (Upgrade upgrade : Upgrade.values()) {
            this.unlockedUpgrades.put(upgrade, false);
        }

        this.maximumVaults = maximumVaults;
        this.vaults = vaults;
    }

    public Team(String name, String description, UUID leader, List<UUID> members, List<UUID> banned, Map<UUID, Rank> playerRanks, Double teamBankVault, Integer teamExperienceVault, Map<Upgrade, Boolean> unlockedUpgrades, Integer maximumVaults, List<Vault> vaults) {
        this.name = name;
        this.description = description;
        this.leader = leader;
        this.members = members;
        this.banned = banned;
        this.playerRanks = playerRanks;
        this.teamBankVault = teamBankVault;
        this.teamExperienceVault = teamExperienceVault;
        this.unlockedUpgrades = unlockedUpgrades;
        this.maximumVaults = maximumVaults;
        this.vaults = vaults;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getLeader() {
        return leader;
    }

    public List<UUID> getMembers() {
        return members;
    }

    public void setMembers(List<UUID> members) {
        this.members = members;
    }

    public List<UUID> getBanned() {
        return banned;
    }

    public void setBanned(List<UUID> banned) {
        this.banned = banned;
    }

    public Map<UUID, Rank> getPlayerRanks() {
        return playerRanks;
    }

    public void setPlayerRanks(Map<UUID, Rank> playerRanks) {
        this.playerRanks = playerRanks;
    }

    public Double getTeamBankVault() {
        return teamBankVault;
    }

    public void setTeamBankVault(Double teamBankVault) {
        this.teamBankVault = teamBankVault;
    }

    public Integer getTeamExperienceVault() {
        return teamExperienceVault;
    }

    public void setTeamExperienceVault(Integer teamExperienceVault) {
        this.teamExperienceVault = teamExperienceVault;
    }

    public Map<Upgrade, Boolean> getUnlockedUpgrades() {
        return unlockedUpgrades;
    }

    public void setUnlockedUpgrades(Map<Upgrade, Boolean> unlockedUpgrades) {
        this.unlockedUpgrades = unlockedUpgrades;
    }

    public Integer getMaximumVaults() {
        return maximumVaults;
    }

    public List<Vault> getVaults() {
        return vaults;
    }

    public void setVaults(List<Vault> vaults) {
        this.vaults = vaults;
    }

    public void addMember(UUID member) {
        members.add(member);
    }

    public void removeMember(UUID member) {
        members.remove(member);
    }

    public void addBanned(UUID member) {
        banned.add(member);
    }

    public void removeBanned(UUID member) {
        banned.remove(member);
    }

    public void addPlayerRank(UUID player, Rank rank) {
        playerRanks.put(player, rank);
    }

    public void removePlayerRank(UUID player) {
        playerRanks.remove(player);
    }

    public void addTeamBankVault(Double amount) {
        teamBankVault += amount;
    }

    public void removeTeamBankVault(Double amount) {
        teamBankVault -= amount;
    }

    public void addTeamExperienceVault(Integer amount) {
        teamExperienceVault += amount;
    }

    public void removeTeamExperienceVault(Integer amount) {
        teamExperienceVault -= amount;
    }

    public void addUnlockedUpgrade(Upgrade upgrade) {
        unlockedUpgrades.put(upgrade, true);
    }

    public void removeUnlockedUpgrade(Upgrade upgrade) {
        unlockedUpgrades.put(upgrade, false);
    }

    public void addVault(Vault vault) {
        vaults.add(vault);
    }

    public void removeVault(Vault vault) {
        vaults.remove(vault);
    }

    public void saveTeam() {
        CustomConfig rawConfig = new CustomConfig(Main.getInstance, name, "teams");
        YamlConfiguration configuration = rawConfig.getConfig();

        configuration.set("name", name);
        configuration.set("description", description);
        configuration.set("leader", leader.toString());

        List<String> members = new ArrayList<>();
        for (UUID member : this.members) {
            members.add(member.toString());
        }
        configuration.set("members", members);

        List<String> banned = new ArrayList<>();
        for (UUID bannedMember : this.banned) {
            banned.add(bannedMember.toString());
        }
        configuration.set("banned", banned);

        Map<String, String> playerRanks = new HashMap<>();
        for (Map.Entry<UUID, Rank> entry : this.playerRanks.entrySet()) {
            playerRanks.put(entry.getKey().toString(), entry.getValue().toString());
        }
        configuration.set("playerRanks", playerRanks);

        configuration.set("teamBankVault", teamBankVault);
        configuration.set("teamExperienceVault", teamExperienceVault);
        configuration.set("unlockedUpgrades", unlockedUpgrades);
        configuration.set("maximumVaults", maximumVaults);

        int position = 0;
        for (Vault vault : vaults) {
            Map<String, Object> vaultMap = new HashMap<>();
            vaultMap.put("contents", vault.getItems());
            vaultMap.put("size", vault.getSize());
            vaultMap.put("secured", vault.isSecured());

            configuration.set("vaults." + position, vaultMap);
            position++;
        }

        for (String string : configuration.getStringList("members")) {
            System.out.println(string);
        }

        rawConfig.saveConfig();
    }

}
