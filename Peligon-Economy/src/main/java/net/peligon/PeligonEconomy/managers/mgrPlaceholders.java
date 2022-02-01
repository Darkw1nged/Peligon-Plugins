package net.peligon.PeligonEconomy.managers;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.peligon.PeligonEconomy.Main;
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

        return null;
    }
}
