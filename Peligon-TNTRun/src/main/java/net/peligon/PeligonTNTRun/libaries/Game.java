package net.peligon.PeligonTNTRun.libaries;

import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.UUID;

public class Game {

    private String gameID;
    private boolean hasStarted;
    private boolean isFinished;
    private List<UUID> players;
    private List<UUID> deadPlayers;
    private List<UUID> taggedPlayers;
    private int maxPlayers;

    public Game() { }
    public Game(String gameID, List<UUID> players, int maxPlayers) {
        this.gameID = gameID;
        this.players = players;
        this.maxPlayers = maxPlayers;
    }

    public Game(String gameID, boolean hasStarted, boolean isFinished, List<UUID> players, List<UUID> deadPlayers, List<UUID> taggedPlayers, int maxPlayers) {
        this.gameID = gameID;
        this.hasStarted = hasStarted;
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

    public boolean hasStarted() {
        return hasStarted;
    }

    public void setHasStarted(boolean hasStarted) {
        this.hasStarted = hasStarted;
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

}
