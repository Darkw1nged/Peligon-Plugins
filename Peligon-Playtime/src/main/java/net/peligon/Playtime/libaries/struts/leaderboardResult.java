package net.peligon.Playtime.libaries.struts;

import java.util.UUID;

public class leaderboardResult implements Comparable<leaderboardResult> {

    private final UUID uuid;
    private int position;
    private final long playtime;

    public leaderboardResult(UUID uuid, int position, long playtime) {
        this.uuid = uuid;
        this.position = position;
        this.playtime = playtime;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public long getPlaytime() {
        return playtime;
    }

    public UUID getUUID() {
        return uuid;
    }

    @Override
    public int compareTo(leaderboardResult object) {
        return Long.compare(object.getPlaytime(), this.getPlaytime());
    }
}
