package net.peligon.PeligonPolls.libaries.struts;

import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Poll {

    private String title;
    private String description;
    private String author;
    private UUID authorID;
    private int upVotes;
    private int downVotes;
    private LocalDateTime created;
    private long messageID;
    private List<UUID> votes = new ArrayList<>();

    public Poll(String title, String description, String author, UUID authorID, int upVotes, int downVotes, LocalDateTime created, long messageID) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.authorID = authorID;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
        this.created = created;
        this.messageID = messageID;
    }

    public Poll(String title, String description, String author, UUID authorID, int upVotes, int downVotes, LocalDateTime created, List<UUID> votes) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.authorID = authorID;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
        this.created = created;
        this.votes = votes;
    }

    public Poll(String title, String description, String author, UUID authorID, int upVotes, int downVotes, LocalDateTime created, long messageID, List<UUID> votes) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.authorID = authorID;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
        this.created = created;
        this.messageID = messageID;
        this.votes = votes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(int downVotes) {
        this.downVotes = downVotes;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public long getMessageID() {
        return messageID;
    }

    public void setCreated(long messageID) {
        this.messageID = messageID;
    }

    public List<UUID> getVotes() {
        return votes;
    }

    public boolean alreadyVoted(Player player) {
        return votes.contains(player.getUniqueId());
    }

    public void setVotes(List<UUID> list) {
        this.votes = list;
    }

    public void addVotes(Player player) {
        this.votes.add(player.getUniqueId());
    }

    public UUID getAuthorID() {
        return authorID;
    }

    public void setAuthorID(UUID authorID) {
        this.authorID = authorID;
    }
}
