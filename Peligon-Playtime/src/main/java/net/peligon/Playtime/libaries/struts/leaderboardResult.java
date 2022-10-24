package net.peligon.Playtime.libaries.struts;

import java.util.UUID;

public class leaderboardResult implements Comparable<leaderboardResult> {

    private UUID uuid;
    private final int position;
    private final long playtime;

    public leaderboardResult(int position, long playtime) {
        this.position = position;
        this.playtime = playtime;
    }

    public int getPosition() {
        return position;
    }

    public long getPlaytime() {
        return playtime;
    }

    public UUID getUUID() {
        return uuid;
    }


    @Override
    public int compareTo(leaderboardResult object) {
        if (object.getPlaytime() > this.getPlaytime()) {
            return 1;
        } else if (object.getPlaytime() < this.getPlaytime()) {
            return -1;
        } else {
            return 0;
        }
    }
}
