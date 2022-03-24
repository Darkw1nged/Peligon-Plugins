package net.peligon.PeligonTNTTag.libaries.util;

import net.peligon.PeligonTNTTag.Main;
import net.peligon.PeligonTNTTag.interfaces.Game;
import net.peligon.PeligonTNTTag.libaries.ScoreboardTick;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardUtil {

    public static ScoreboardUtil getInstance;
    public ScoreboardUtil() {
        getInstance = this;
    }

    private int taskID;
    private final Map<Game, Scoreboard> gameScoreboard = new HashMap<>();

    public void startScoreboard(Game game) {
        ScoreboardTick scoreboardTick = new ScoreboardTick(game);
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance, new Runnable() {
            int count = 0;
            Scoreboard scoreboard = gameScoreboard.get(game);
            public void run() {
                if (!scoreboardTick.hasID()) scoreboardTick.setID(taskID);
                if (count == 13) count = 0;
                switch (count) {
                    case 0:
                        break;
                }
                count++;
            }
        }, 0, 10);
    }

    public void createPlayerScoreboard(Game game, String scoreboardName, String scoreboardTitle, Score... values) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective(scoreboardName, "dummy");
        objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', scoreboardTitle));

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        for (Score score : values) {
            objective.getScore(ChatColor.translateAlternateColorCodes('&', score.getEntry()));
        }

        gameScoreboard.put(game, scoreboard);
    }

    public void removeScoreboard(Game game) {
        ScoreboardTick scoreboardTick = new ScoreboardTick(game);
        if (scoreboardTick.hasID())
            scoreboardTick.stop();
        gameScoreboard.remove(game);
    }

    public boolean hasScoreboard(Game game) {
        return gameScoreboard.containsKey(game);
    }

}
