package net.peligon.Teams.libaries.struts;

import net.peligon.Teams.libaries.lists.RequestType;

public class TeamRequest {

    private final RequestType type;
    private final Team sender;
    private final Team receiver;
    private final Long timeSent;

    public TeamRequest(RequestType type, Team sender, Team receiver) {
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.timeSent = System.currentTimeMillis();
    }

    public RequestType getType() {
        return type;
    }

    public Team getSender() {
        return sender;
    }

    public Team getReceiver() {
        return receiver;
    }

    public Long getTimeSent() {
        return timeSent;
    }

}
