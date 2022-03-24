package net.peligon.PeligonSkills.managers;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.peligon.PeligonSkills.Main;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class mgrPlaceholders extends PlaceholderExpansion {

    private final Main plugin = Main.getInstance;

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
        switch (identifier.toLowerCase()) {
            case "stat_smelting_level":
                return String.valueOf(plugin.Smelting.getLevel(player));
            case "stat_smelting_experience":
                return String.valueOf(plugin.Smelting.getExperience(player));
            case "stat_repair_level":
                return String.valueOf(plugin.Repair.getLevel(player));
            case "stat_repair_experience":
                return String.valueOf(plugin.Repair.getExperience(player));
            case "stat_alchemy_level":
                return String.valueOf(plugin.Alchemy.getLevel(player));
            case "stat_alchemy_experience":
                return String.valueOf(plugin.Alchemy.getExperience(player));
            case "stat_acrobatics_level":
                return String.valueOf(plugin.Acrobatics.getLevel(player));
            case "stat_acrobatics_experience":
                return String.valueOf(plugin.Acrobatics.getExperience(player));
            case "stat_mining_level":
                return String.valueOf(plugin.Mining.getLevel(player));
            case "stat_mining_experience":
                return String.valueOf(plugin.Mining.getExperience(player));
            case "stat_lumberjack_level":
                return String.valueOf(plugin.Lumberjack.getLevel(player));
            case "stat_lumberjack_experience":
                return String.valueOf(plugin.Lumberjack.getExperience(player));
            case "stat_fishing_level":
                return String.valueOf(plugin.Fishing.getLevel(player));
            case "stat_fishing_experience":
                return String.valueOf(plugin.Fishing.getExperience(player));
            case "stat_herbalism_level":
                return String.valueOf(plugin.Herbalism.getLevel(player));
            case "stat_herbalism_experience":
                return String.valueOf(plugin.Herbalism.getExperience(player));
            case "stat_excavation_level":
                return String.valueOf(plugin.Excavation.getLevel(player));
            case "stat_excavation_experience":
                return String.valueOf(plugin.Excavation.getExperience(player));
        }
        return null;
    }
}
