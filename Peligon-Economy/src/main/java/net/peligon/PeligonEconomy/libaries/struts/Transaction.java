package net.peligon.PeligonEconomy.libaries.struts;

import net.peligon.PeligonEconomy.libaries.Utils;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;

public class Transaction {

    private final UUID transactionID;
    private final Player player;
    private final TransactionOperation operation;
    private final double amount;
    private final Date recorded;

    private final String logMessage;

    public Transaction(UUID transactionID, Player player, TransactionOperation operation, double amount, Date recorded, String logMessage) {
        this.transactionID = transactionID;
        this.player = player;
        this.operation = operation;
        this.amount = amount;
        this.recorded = recorded;
        this.logMessage = logMessage;
    }

    public UUID getTransactionID() {
        return this.transactionID;
    }

    public Player getPlayer() {
        return this.player;
    }

    public TransactionOperation getOperation() {
        return this.operation;
    }

    public double getAmount() {
        return this.amount;
    }

    public Date getRecorded() {
        return this.recorded;
    }

    public String getLogMessage() {
        return Utils.chatColor(this.logMessage)
                .replaceAll("%transactionID%", this.transactionID.toString())
                .replaceAll("%player%", this.player.getName())
                .replaceAll("%operation%", this.operation.toString())
                .replaceAll("%amount%", String.valueOf(this.amount))
                .replaceAll("%recorded%", Utils.formatTime(this.recorded));
    }

    public enum TransactionOperation {
        deposit,
        withdraw,
        transfer,
        override
    }

}
