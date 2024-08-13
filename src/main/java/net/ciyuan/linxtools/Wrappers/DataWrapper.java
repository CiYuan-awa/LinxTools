package net.ciyuan.linxtools.Wrappers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import net.ciyuan.linxtools.LinxTools;
import net.ciyuan.linxtools.Utils.ConfigurationUtil;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static net.ciyuan.linxtools.LinxTools.ConsoleLogger;


public class DataWrapper
{
    public static MongoClient Cli;
    public static MongoDatabase DB;
    public static MongoCollection<Document> MainCollection;
    public static void InitConnection()
    {
        ConfigurationUtil.RefreshConfig();
        String Connection = "mongodb://" + LinxTools.Config.getString("LinxTools.MongoDB.IP") + ":" + LinxTools.Config.getString("LinxTools.MongoDB.Port");
        ConsoleLogger.info(Connection);
        try
        {
            Cli = MongoClients.create(Connection);
            DB = Cli.getDatabase(LinxTools.Config.getString("LinxTools.MongoDB.DataBase"));
            MainCollection = DB.getCollection(LinxTools.Config.getString("LinxTools.MongoDB.Collection"));
        }
        catch (Exception ex)
        {
            ConsoleLogger.warning(ex.toString());
        }
    }

    public static void CreateUserProfile(HashMap<String, Object> ValueList)
    {
        Document Doc = new Document();
        for (Map.Entry<String, Object> Value : ValueList.entrySet())
        {
            Doc.append(Value.getKey(), Value.getValue());
        }
        MainCollection.insertOne(Doc);
        ConsoleLogger.info("Successfully updated user profiles.");
    }

    public static HashMap<String, Object> GetUserProfile(String UUID)
    {
        Bson Filter = Filters.eq("UUID", UUID);
        Document Doc = MainCollection.find(Filter).first();
        return new HashMap<>(Doc);
    }

    public static List<String> GetDeathLocation(String UUID)
    {
        Bson Filter = Filters.eq("UUID", UUID);
        Document Doc = MainCollection.find(Filter).first();

        if (Doc != null && Doc.containsKey("DeathLocations"))
        {
            return (List<String>) Doc.get("DeathLocations");
        }
        else
        {
            ConsoleLogger.warning("Document not found or DeathPoints field is missing.");
            return null;
        }
    }

    public static void UpdateData()
    {
        HashMap<String, Object> PlayersInfo = new HashMap<>();
        for (Player player : Bukkit.getOnlinePlayers())
        {
            String PlayerUUID = player.getUniqueId().toString();
            if (DataWrapper.IsUserExists(PlayerUUID))
            {
                UpdateDeaths(PlayerUUID, GetDeathLocation(PlayerUUID).size() + 1);
                continue;
            }
            PlayersInfo.put("UUID", PlayerUUID);
            PlayersInfo.put("InGameName", player.getName());
            PlayersInfo.put("Deaths", 0);
            PlayersInfo.put("DeathLocations", new LinkedList<String>());
            DataWrapper.CreateUserProfile(PlayersInfo);
            PlayersInfo.clear();
        }
    }

    public static boolean IsUserExists(String UUID)
    {
        Document Temp = MainCollection.find(Filters.eq("UUID", UUID)).first();
        if (Temp != null) return true;
        else return false;
    }

    public static void ModifyUserProfile(String Key, Object Value, String UUID)
    {
        MainCollection.findOneAndUpdate(Filters.eq("UUID", UUID), Updates.push(Key, Value));
    }

    private static void UpdateDeaths(String UUID, int Count)
    {
        MainCollection.findOneAndUpdate(Filters.eq("UUID", UUID), Updates.set("Deaths", Count));
    }
}
