package net.peligon.PeligonTNTRun.libaries;

import net.peligon.PeligonTNTRun.Main;
import net.peligon.PeligonTNTRun.libaries.Instances.Game;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTick extends BukkitRunnable {

    private final Main plugin = Main.getInstance;
    private final Game game;
    public GameTick(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        if (Utils.gamesWaiting.contains(game)) {
            if (game.getPlayers().size() < 9) {

            }
        }
    }
}