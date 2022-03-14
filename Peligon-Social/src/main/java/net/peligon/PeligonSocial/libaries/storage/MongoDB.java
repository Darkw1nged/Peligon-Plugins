package net.peligon.PeligonSocial.libaries.storage;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.peligon.PeligonSocial.libaries.Utils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.OfflinePlayer;

import static org.bukkit.Bukkit.getServer;

public class MongoDB {

    private static MongoDatabase database;

    public MongoDB(String username, String password, String cluster, String database) {
        connectDatabase(username, password, cluster, database);
    }

    public void connectDatabase(String username, String password, String cluster, String databaseName) {
        MongoClient client = MongoClients.create("mongodb+srv://" + username + ":" + password + "@" + cluster +
                ".sti2r.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
        database = client.getDatabase(databaseName);
        getServer().getConsoleSender().sendMessage(Utils.chatColor("&aConnection to database has been successful."));
    }

    public static void setDataDocument(OfflinePlayer player, String identifier, Object value, MongoCollection<Document> collection) {
        Document document = new Document("uuid", player.getUniqueId());
        Bson newValue = new Document(identifier, value);
        Bson updateOperation = new Document("$set", newValue);
        collection.updateOne(document, updateOperation);
    }

    public static Object getDataDocument(OfflinePlayer player, String identifier, MongoCollection<Document> collection) {
        Document document = new Document("uuid", player.getUniqueId());
        if (collection.find(document).first() != null) {
            Document found = collection.find().first();
            return found.get(identifier);
        }
        throw new Error("Connection to database could not be made.\nPlease notify the developer of this.\n");
    }

    public static MongoDatabase getDatabase() {
        return database;
    }

}
