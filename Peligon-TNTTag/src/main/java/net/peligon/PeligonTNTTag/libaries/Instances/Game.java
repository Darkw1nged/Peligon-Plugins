package net.peligon.PeligonTNTTag.libaries.Instances;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game {

    private String gameID;
    private boolean isFinished;
    private List<UUID> players;
    private List<UUID> deadPlayers;
    private List<UUID> taggedPlayers;
    private int maxPlayers;
    private Location lobbyLocation;

    public Game() { }
    public Game(String gameID, int maxPlayers) {
        this.gameID = gameID;
        this.maxPlayers = maxPlayers;
        this.players = new ArrayList<>();
    }

    public Game(String gameID, boolean isFinished, List<UUID> players, List<UUID> deadPlayers, List<UUID> taggedPlayers, int maxPlayers) {
        this.gameID = gameID;
        this.isFinished = isFinished;
        this.players = players;
        this.deadPlayers = deadPlayers;
        this.taggedPlayers = taggedPlayers;
        this.maxPlayers = maxPlayers;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public List<UUID> getPlayers() {
        return players;
    }

    public void setPlayers(List<UUID> players) {
        this.players = players;
    }

    public void addPlayers(OfflinePlayer player) {
        this.players.add(player.getUniqueId());
    }

    public List<UUID> getDeadPlayers() {
        return deadPlayers;
    }

    public void setDeadPlayers(List<UUID> deadPlayers) {
        this.deadPlayers = deadPlayers;
    }

    public void addDeadPlayers(OfflinePlayer player) {
        this.deadPlayers.add(player.getUniqueId());
    }

    public List<UUID> getTaggedPlayers() {
        return taggedPlayers;
    }

    public void setTaggedPlayers(List<UUID> taggedPlayers) {
        this.taggedPlayers = taggedPlayers;
    }

    public void addTaggedPlayers(OfflinePlayer player) {
        this.taggedPlayers.add(player.getUniqueId());
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public Location getLobbyLocation() {
        return lobbyLocation;
    }

    public void setLobbyLocation(Location location) {
        this.lobbyLocation = location;
    }

}
