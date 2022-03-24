package net.peligon.PeligonTNTTag.libaries;

import net.peligon.PeligonTNTTag.interfaces.Game;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardTick {

    private final Map<String, Integer> scoreboardTasks = new HashMap<>();
    private final Game game;

    public ScoreboardTick(Game game) {
        this.game = game;
    }

    public void setID(int id) {
        scoreboardTasks.put(game.getGameID(), id);
    }

    public int getID() {
        return scoreboardTasks.get(game.getGameID());
    }

    public boolean hasID() {
        return scoreboardTasks.containsKey(game.getGameID());
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(scoreboardTasks.get(game.getGameID()));
        scoreboardTasks.remove(game.getGameID());
    }

}
