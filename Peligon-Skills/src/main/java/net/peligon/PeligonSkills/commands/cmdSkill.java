package net.peligon.PeligonSkills.commands;

import net.peligon.PeligonSkills.Main;
import net.peligon.PeligonSkills.libaries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdSkill implements CommandExecutor {

    private final Main plugin = Main.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("skill")) {
            if (args.length == 1) {
                if (sender.hasPermission("Peligon.Skills.View.Other") || sender.hasPermission("Peligon.Skills.*")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-player-found").replaceAll("%player%", args[0])));
                        return true;
                    }


                    String stats = "&eAcrobatics Skill Level: &a" + plugin.Acrobatics.getLevel(target) + " &b(" + plugin.Acrobatics.getExperience(target) + "/" + Utils.neededExperience(plugin.Acrobatics.getLevel(target)) + ")\n" +
                            "&eExcavation Skill Level: &a" + plugin.Excavation.getLevel(target) + " &b(" + plugin.Excavation.getExperience(target) + "/" + Utils.neededExperience(plugin.Excavation.getLevel(target)) + ")\n" +
                            "&eFishing Skill Level: &a" + plugin.Fishing.getLevel(target) + " &b(" + plugin.Fishing.getExperience(target) + "/" + Utils.neededExperience(plugin.Fishing.getLevel(target)) + ")\n" +
                            "&eHerbalism Skill Level: &a" + plugin.Herbalism.getLevel(target) + " &b(" + plugin.Herbalism.getExperience(target) + "/" + Utils.neededExperience(plugin.Herbalism.getLevel(target)) + ")\n" +
                            "&eLumberjack Skill Level: &a" + plugin.Lumberjack.getLevel(target) + " &b(" + plugin.Lumberjack.getExperience(target) + "/" + Utils.neededExperience(plugin.Lumberjack.getLevel(target)) + ")\n" +
                            "&eMining Skill Level: &a" + plugin.Mining.getLevel(target) + " &b(" + plugin.Mining.getExperience(target) + "/" + Utils.neededExperience(plugin.Mining.getLevel(target)) + ")\n" +
                            "&eRepair Skill Level: &a" + plugin.Repair.getLevel(target) + " &b(" + plugin.Repair.getExperience(target) + "/" + Utils.neededExperience(plugin.Repair.getLevel(target)) + ")\n" +
                            "&eSmelting Skill Level: &a" + plugin.Smelting.getLevel(target) + " &b(" + plugin.Smelting.getExperience(target) + "/" + Utils.neededExperience(plugin.Smelting.getLevel(target)) + ")\n";

                    sender.sendMessage(Utils.chatColor(stats));
                } else
                    sender.sendMessage(Utils.chatColor(plugin.fileMessage.getConfig().getString("no-permission")));
            }
        }
        return false;
    }

}
