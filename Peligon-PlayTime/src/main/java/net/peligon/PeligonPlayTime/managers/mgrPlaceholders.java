package net.peligon.PeligonPlayTime.managers;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.peligon.PeligonPlayTime.Main;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class mgrPlaceholders extends PlaceholderExpansion {

    private Main plugin = Main.getInstance;

    public static mgrPlaceholders getInstance;
    public mgrPlaceholders() {
        getInstance = this;
    }

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
        long timePlayed = plugin.playerTime.getTimePlayed(player);
        long seconds = timePlayed % 1000L;
        long minutes = timePlayed / 1000L % 60L;
        long hours = timePlayed / 1000L / 60L % 60L;
        long days = timePlayed / 1000L / 60L / 60L % 24L;
        long weeks = timePlayed / 1000L / 60L / 60L / 24L;


        if (identifier.equalsIgnoreCase("playertime") || identifier.equalsIgnoreCase("playtime")) {
            return seconds + "s, " + minutes + "m, " + hours + "h, " + days + "d" + weeks + "w";
        }
        if (identifier.equalsIgnoreCase("playersecond") || identifier.equalsIgnoreCase("ptseconds")) {
            return seconds + "s";
        }
        if (identifier.equalsIgnoreCase("playerminutes") || identifier.equalsIgnoreCase("ptminutes")) {
            return minutes + "m";
        }
        if (identifier.equalsIgnoreCase("playerhours") || identifier.equalsIgnoreCase("pthours")) {
            return hours + "h";
        }
        if (identifier.equalsIgnoreCase("playerdays") || identifier.equalsIgnoreCase("ptdays")) {
            return days + "d";
        }
        if (identifier.equalsIgnoreCase("playerweeks") || identifier.equalsIgnoreCase("ptweeks")) {
            return weeks + "w";
        }
        if (identifier.equalsIgnoreCase("playerposition") || identifier.equalsIgnoreCase("ptposition") || identifier.equalsIgnoreCase("ptpos")) {
            return String.valueOf(plugin.playerTime.getTimePlayedPosition(player));
        }
        if (identifier.equalsIgnoreCase("playernextreward") || identifier.equalsIgnoreCase("ptnextreward")) {
            return "";
        }
        return null;
    }
}
