package net.peligon.PeligonTNTTag;

import net.peligon.PeligonTNTTag.libaries.Utils;
import net.peligon.PeligonTNTTag.interfaces.Game;
import net.peligon.PeligonTNTTag.managers.GameManager;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTick extends BukkitRunnable {

    private final Main plugin = Main.getInstance;
    private final Game game;
    public GameTick(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        if (GameManager.getInstance.getGamesWaiting().contains(game)) {
            // default size 9
            if (game.getPlayers().size() < 2) {

            }
        }
    }
}