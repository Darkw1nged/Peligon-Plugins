package net.peligon.PeligonCore.libaries;

import java.util.UUID;

public class TeleportRequest {

    private final UUID sender;
    private final UUID receiver;
    private final String type;

    public TeleportRequest(UUID sender, UUID receiver, String type) {
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public UUID getSender() {
        return sender;
    }

    public UUID getReceiver() {
        return receiver;
    }
}
