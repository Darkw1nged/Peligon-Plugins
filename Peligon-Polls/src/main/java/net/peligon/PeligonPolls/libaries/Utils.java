package net.peligon.PeligonPolls.libaries;

import net.dv8tion.jda.api.EmbedBuilder;
import net.peligon.PeligonPolls.Main;
import net.peligon.PeligonPolls.libaries.struts.Poll;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.*;

public class Utils {

    private static final Main plugin = Main.getInstance;

    // ---- [ Managing chat color within the plugin ] ----
    public static String chatColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    // ---- [ Converting a lore to include colors ] ----
    public static List<String> getConvertedLore(FileConfiguration config, String path) {
        if (config == null) return null;
        List<String> oldList = config.getStringList(path + ".lore");
        List<String> newList = new ArrayList<>();
        for (String a : oldList)
            newList.add(ChatColor.translateAlternateColorCodes('&', a));
        return newList;
    }

    // ---- [ Send an Embed to discord ] ----
    public static String sendPoll(String title, String description, int voteUp, int voteDown, Poll poll) {
        if (plugin.discordChannel == null) return "";
        String messageID = "";
        EmbedBuilder pollEmbed = new EmbedBuilder()
                .setAuthor("Submitter - " + poll.getAuthor(), null,
                        "https://crafatar.com/avatars/" + Bukkit.getPlayer(poll.getAuthor()).getUniqueId().toString() + "?default=MHF_Steve&overlay")
                .setColor(Color.green)
                .setDescription("**New Suggestion - " + replaceAllColorCodes(title) + "**\n" + replaceAllColorCodes(description) + "\n\n**__Results__**\n✅ " + voteUp + " | ❌ " + voteDown)
                .setFooter("**Submitted: " + poll.getCreated().getDayOfMonth() + "-" + poll.getCreated().getMonthValue() + "-" + poll.getCreated().getYear() + "**");


        plugin.discordChannel.sendMessageEmbeds(pollEmbed.build()).queue((message) -> {
            polls.add(new Poll(poll.getTitle(), poll.getDescription(), poll.getAuthor(), poll.getAuthorID(), poll.getUpVotes(), poll.getDownVotes(), poll.getCreated(), message.getIdLong()));
        });
        return messageID;
    }

    // ---- [ Update Embed ] ----
    public static void updatePoll(Player player) {
        if (plugin.discordChannel == null) return;
        Poll poll = polls.get(polls_temp.get(player.getUniqueId()));

        EmbedBuilder pollEmbed = new EmbedBuilder()
                .setAuthor("Submitter - " + poll.getAuthor(), null,
                        "https://crafatar.com/avatars/" + poll.getAuthorID() + "?default=MHF_Steve&overlay")
                .setColor(Color.green)
                .setDescription("**New Suggestion - " + replaceAllColorCodes(poll.getTitle()) + "**\n" + replaceAllColorCodes(poll.getDescription()) + "\n\n**__Results__**\n✅ " + poll.getUpVotes() + " | ❌ " + poll.getDownVotes())
                .setFooter("**Submitted: " + poll.getCreated().getDayOfMonth() + "-" + poll.getCreated().getMonthValue() + "-" + poll.getCreated().getYear() + "**");

        plugin.discordChannel.retrieveMessageById(String.valueOf(poll.getMessageID())).queue((message ->
                message.editMessageEmbeds(pollEmbed.build()).queue()));

        Utils.poll_ID.remove(player.getUniqueId());
    }

    // ---- [ Remove Embed ] ----
    public static void removeEmbed(String messageID) {
        if (plugin.discordChannel == null) return;
        plugin.discordChannel.retrieveMessageById(messageID).queue(message -> message.delete().queue());
        if (plugin.fileCache.getConfig().contains(messageID)) {
            plugin.fileCache.getConfig().set(messageID, null);
            plugin.fileCache.saveConfig();
        }
    }

    // ---- [ Removes all color codes from string ] ----
    public static String replaceAllColorCodes(String string) {
        return string.replaceAll("&a", "")
                .replaceAll("&b", "")
                .replaceAll("&c", "")
                .replaceAll("&d", "")
                .replaceAll("&e", "")
                .replaceAll("&f", "")
                .replaceAll("&1", "")
                .replaceAll("&2", "")
                .replaceAll("&3", "")
                .replaceAll("&4", "")
                .replaceAll("&5", "")
                .replaceAll("&6", "")
                .replaceAll("&7", "")
                .replaceAll("&8", "")
                .replaceAll("&9", "")
                .replaceAll("&k", "")
                .replaceAll("&l", "")
                .replaceAll("&m", "")
                .replaceAll("&n", "")
                .replaceAll("&o", "")
                .replaceAll("&r", "");

    }

    // ---- [ Cached Items ] ----
    public static List<Poll> polls = new ArrayList<>();
    public static Map<UUID, Poll> poll_ID = new HashMap<>();
    public static Map<UUID, Integer> polls_temp = new HashMap<>();

    // ---- [ Saving active polls ] ----
    public static void saveActivePolls() {
        for (Poll poll : polls) {
            plugin.fileCache.getConfig().set(poll.getMessageID() + ".title", poll.getTitle());
            plugin.fileCache.getConfig().set(poll.getMessageID() + ".description", poll.getDescription());
            plugin.fileCache.getConfig().set(poll.getMessageID() + ".author", poll.getAuthor());
            plugin.fileCache.getConfig().set(poll.getMessageID() + ".authorID", poll.getAuthorID().toString());
            plugin.fileCache.getConfig().set(poll.getMessageID() + ".upVotes", poll.getUpVotes());
            plugin.fileCache.getConfig().set(poll.getMessageID() + ".downVotes", poll.getDownVotes());
            plugin.fileCache.getConfig().set(poll.getMessageID() + ".created", poll.getCreated().toString());
            plugin.fileCache.getConfig().set(poll.getMessageID() + ".messageID", poll.getMessageID());

            List<String> list = new ArrayList<>();
            for (UUID uuid : poll.getVotes()) {
                list.add(uuid.toString());
            }
            plugin.fileCache.getConfig().set(poll.getMessageID() + ".votes", list);

            plugin.fileCache.saveConfig();
        }
    }

    // ---- [ Loading active polls ] ----
    public static void loadActivePolls() {
        for (String key : plugin.fileCache.getConfig().getKeys(false)) {
            List<UUID> list = new ArrayList<>();
            for (String uuid : plugin.fileCache.getConfig().getStringList(key + ".votes")) {
                list.add(UUID.fromString(uuid));
            }

            polls.add(new Poll(plugin.fileCache.getConfig().getString(key + ".title"),
                    plugin.fileCache.getConfig().getString(key + ".description"),
                    plugin.fileCache.getConfig().getString(key + ".author"),
                    UUID.fromString(plugin.fileCache.getConfig().getString(key + ".authorID")),
                    plugin.fileCache.getConfig().getInt(key + ".upVotes"),
                    plugin.fileCache.getConfig().getInt(key + ".downVotes"),
                    LocalDateTime.parse(plugin.fileCache.getConfig().getString(key + ".created")),
                    plugin.fileCache.getConfig().getLong(key + ".messageID"),
                    list)
            );
        }
    }
}
