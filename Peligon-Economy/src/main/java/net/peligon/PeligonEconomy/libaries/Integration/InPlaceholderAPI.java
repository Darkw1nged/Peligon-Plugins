package net.peligon.PeligonEconomy.libaries.Integration;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.peligon.PeligonEconomy.Main;
import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class InPlaceholderAPI extends PlaceholderExpansion {

    // Getting the instance of the main class.
    private final Main plugin = Main.getInstance;

    /**
     * The placeholder identifier of this expansion. May not contain {@literal %},
     * {@literal {}} or _
     *
     * @return placeholder identifier that is associated with this expansion
     */
    @Override
    public @NotNull String getIdentifier() {
        return "peligon";
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
        // -- { Raw values } --
        if (identifier.equalsIgnoreCase("balance") || identifier.equalsIgnoreCase("cash")) {
            return String.valueOf(plugin.Economy.getAccount(player));
        }
        if (identifier.equalsIgnoreCase("bank")) {
            return String.valueOf(plugin.Economy.getBank(player));
        }

        if (identifier.equalsIgnoreCase("server_balance") || identifier.equalsIgnoreCase("server_cash")) {
            return String.valueOf(plugin.Economy.getServerTotalCash());
        }
        if (identifier.equalsIgnoreCase("server_bank")) {
            return String.valueOf(plugin.Economy.getServerTotalBank());
        }
        if (identifier.equalsIgnoreCase("server_net")) {
            return String.valueOf(plugin.Economy.getServerTotalCash() + plugin.Economy.getServerTotalBank());
        }

        if (identifier.equalsIgnoreCase("experience_points")) {
            return String.valueOf(Utils.getPlayerExp(player));
        }
        if (identifier.equalsIgnoreCase("experience_level")) {
            return String.valueOf(player.getLevel());
        }

        // -- { Formatted values } --
        if (identifier.equalsIgnoreCase("formatted_balance") || identifier.equalsIgnoreCase("formatted_cash")) {
            return Utils.format(plugin.Economy.getAccount(player));
        }
        if (identifier.equalsIgnoreCase("formatted_bank")) {
            return Utils.format(plugin.Economy.getBank(player));
        }

        if (identifier.equalsIgnoreCase("formatted_server_balance") || identifier.equalsIgnoreCase("formatted_server_cash")) {
            return Utils.format(plugin.Economy.getServerTotalCash());
        }
        if (identifier.equalsIgnoreCase("formatted_server_bank")) {
            return Utils.format(plugin.Economy.getServerTotalBank());
        }
        if (identifier.equalsIgnoreCase("formatted_server_net")) {
            return Utils.format(plugin.Economy.getServerTotalCash() + plugin.Economy.getServerTotalBank());
        }

        if (identifier.equalsIgnoreCase("formatted_experience_points")) {
            return Utils.format(Utils.getPlayerExp(player));
        }
        if (identifier.equalsIgnoreCase("formatted_experience_level")) {
            return Utils.format(player.getLevel());
        }

        return null;
    }
}
