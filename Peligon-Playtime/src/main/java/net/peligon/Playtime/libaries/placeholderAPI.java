package net.peligon.Playtime.libaries;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.peligon.Playtime.Main;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class placeholderAPI extends PlaceholderExpansion {

    // Getting the main instance of the main class.
    private final Main plugin = Main.getInstance;

    /**
     * The placeholder identifier of this expansion. May not contain {@literal %},
     * {@literal {}} or _
     *
     * @return placeholder identifier that is associated with this expansion
     */
    @Override
    public @NotNull String getIdentifier() {
        return "Peligon";
    }

    /**
     * The author of this expansion
     *
     * @return name of the author for this expansion
     */
    @Override
    public @NotNull String getAuthor() {
        return "Peligon";
    }

    /**
     * The version of this expansion
     *
     * @return current version of this expansion
     */
    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        long rawPlaytime = playerUtils.getPlaytime(player);
        long playtimeWeeks = TimeUnit.MILLISECONDS.toDays(rawPlaytime) / 7;
        long playtimeDays = TimeUnit.MILLISECONDS.toDays(rawPlaytime);
        long playtimeHours = TimeUnit.MILLISECONDS.toHours(rawPlaytime) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(rawPlaytime));
        long playtimeMinutes = TimeUnit.MILLISECONDS.toMinutes(rawPlaytime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(rawPlaytime));
        long playtimeSeconds = TimeUnit.MILLISECONDS.toSeconds(rawPlaytime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(rawPlaytime));


        if (identifier.equalsIgnoreCase("playertime") || identifier.equalsIgnoreCase("playtime")) {
            String playtime = playtimeWeeks <= 0 ? "" : playtimeWeeks + "w, ";
            playtime += playtimeDays <= 0 ? "" : playtimeDays + "d, ";
            playtime += playtimeHours <= 0 ? "" : playtimeHours + "h, ";
            playtime += playtimeMinutes <= 0 ? "" : playtimeMinutes + "m, ";
            playtime += playtimeSeconds <= 0 ? "" : playtimeSeconds + "s";

            return playtime;
        }
        if (identifier.equalsIgnoreCase("playersecond") || identifier.equalsIgnoreCase("ptseconds")) {
            return playtimeSeconds + "s";
        }
        if (identifier.equalsIgnoreCase("playerminutes") || identifier.equalsIgnoreCase("ptminutes")) {
            return playtimeMinutes + "m";
        }
        if (identifier.equalsIgnoreCase("playerhours") || identifier.equalsIgnoreCase("pthours")) {
            return playtimeHours + "h";
        }
        if (identifier.equalsIgnoreCase("playerdays") || identifier.equalsIgnoreCase("ptdays")) {
            return playtimeDays + "d";
        }
        if (identifier.equalsIgnoreCase("playerweeks") || identifier.equalsIgnoreCase("ptweeks")) {
            return playtimeWeeks + "w";
        }
        if (identifier.equalsIgnoreCase("playerposition") || identifier.equalsIgnoreCase("ptposition") || identifier.equalsIgnoreCase("ptpos")) {
            AtomicInteger position = new AtomicInteger(-1);
            systemUtils.getPlaytimeLeaderboard().forEach((uuid, playtime) -> {
                if (uuid.equals(player.getUniqueId())) {
                    position.set(playtime.getPosition());
                }
            });
            return position.toString();
        }
        return null;
    }
}
