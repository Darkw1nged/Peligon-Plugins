package net.peligon.PeligonEconomy.libaries.struts;

import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;

public class Transaction {

    private final UUID transactionID;
    private final Player player;
    private final Operation operation;
    private final double amount;
    private final Date recorded;

    public Transaction(UUID transactionID, Player player, Operation operation, double amount, Date recorded) {
        this.transactionID = transactionID;
        this.player = player;
        this.operation = operation;
        this.amount = amount;
        this.recorded = recorded;
    }

    public UUID getTransactionID() {
        return this.transactionID;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Operation getOperation() {
        return this.operation;
    }

    public double getAmount() {
        return this.amount;
    }

    public Date getRecorded() {
        return this.recorded;
    }

    enum Operation {
        deposit,
        withdraw,
        transfer,
        override
    }

}
