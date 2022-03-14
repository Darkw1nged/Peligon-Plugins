package net.peligon.PeligonSocial.manager;

import com.mongodb.client.MongoCollection;
import net.peligon.PeligonSocial.libaries.storage.MongoDB;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class mgrMongoDB {

    public static mgrMongoDB getInstance;
    public mgrMongoDB() {
        getInstance = this;
    }

    public void createUser(OfflinePlayer player) {
        List<Player> playerList = new ArrayList<>();
        MongoCollection<Document> collection = MongoDB.getDatabase().getCollection("Peligon Testing");
        MongoDB.setDataDocument(player, "friends", playerList, collection);
    }

}