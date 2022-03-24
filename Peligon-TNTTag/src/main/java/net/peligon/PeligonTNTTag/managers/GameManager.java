package net.peligon.PeligonTNTTag.managers;

import net.peligon.PeligonTNTTag.GameTick;
import net.peligon.PeligonTNTTag.Main;
import net.peligon.PeligonTNTTag.interfaces.Game;
import org.bukkit.entity.Player;

import java.util.*;

public class GameManager {

    private final Main plugin = Main.getInstance;
    public static GameManager getInstance;
    public GameManager() {
        getInstance = this;
    }

    private final List<Game> gamesWaiting = new ArrayList<>();
    private final List<Game> gamesStarted = new ArrayList<>();
    private final Map<UUID, Game> playerGame = new HashMap<>();

    public boolean isPlayerInGame(Player player) {
        if (playerGame.isEmpty()) return false;
        return playerGame.containsKey(player.getUniqueId());
    }

    public Game getPlayerGame(Player player) {
        if (isPlayerInGame(player)) {
            return playerGame.get(player.getUniqueId());
        }
        return null;
    }

    public void playerJoinGame(Player player) {
        if (gamesWaiting.isEmpty()) {
            createGame().addPlayers(player);
        } else {
            for (Game game : gamesWaiting) {
                if (game.getPlayers().size() == game.getMaxPlayers()) continue;
                game.addPlayers(player);
            }
        }
    }

    public Game createGame() {
        Game game = new Game(UUID.randomUUID().toString(), 10);
        gamesWaiting.add(game);
        new GameTick(game).runTaskLaterAsynchronously(plugin, 20 * 2);
        return game;
    }

    public List<Game> getGamesWaiting() {
        return gamesWaiting;
    }

    public List<Game> getGamesStarted() {
        return gamesStarted;
    }

}
