package net.peligon.Teams.libaries.struts;

import org.bukkit.entity.Player;

public class InviteRequest {

    private final Team sender;
    private final Player receiver;
    private final long timeSent;

    public InviteRequest(Team sender, Player receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.timeSent = System.currentTimeMillis();
    }

    public Team getSender() {
        return sender;
    }

    public Player getReceiver() {
        return receiver;
    }

    public long getTimeSent() {
        return timeSent;
    }

}
