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

import java.util.*;

import static net.ciyuan.linxtools.LinxTools.ConsoleLogger;


public class DataWrapper
{
    public static MongoClient Cli;
    public static MongoDatabase DB;
    public static MongoCollection<Document> MainCollection;
    public static MongoCollection<Document> DataCollection;
    public static void InitConnection()
    {
        ConfigurationUtil.RefreshConfig();
        String Connection = "mongodb://" + LinxTools.Config.getString("LinxTools.MongoDB.IP") + ":" + LinxTools.Config.getString("LinxTools.MongoDB.Port");
        ConsoleLogger.info(Connection);
        try
        {
            Cli = MongoClients.create(Connection);
            DB = Cli.getDatabase(Objects.requireNonNull(LinxTools.Config.getString("LinxTools.MongoDB.DataBase")));
            DataCollection = DB.getCollection("Data");
            MainCollection = DB.getCollection(Objects.requireNonNull(LinxTools.Config.getString("LinxTools.MongoDB.Collection")));
        }
        catch (Exception ex)
        {
            ConsoleLogger.warning(ex.toString());
        }
    }


    public static void UpdateData()
    {
        if (!IsDataExists("Warps"))
        {
            HashMap<String, String> DataInfo = new HashMap<>();
            CreateData("Warps", DataInfo);
        }
        for (Player player : Bukkit.getOnlinePlayers())
        {
            HashMap<String, Object> PlayersInfo = new HashMap<>();
            String PlayerUUID = player.getUniqueId().toString();
            if (DataWrapper.IsUserExists(PlayerUUID))
            {
                ModifyUserProfile(PlayerUUID, "Deaths", Objects.requireNonNull(GetDeathLocation(PlayerUUID)).size() + 1);
                continue;
            }
            PlayersInfo.put("UUID", PlayerUUID);
            PlayersInfo.put("InGameName", player.getName());
            PlayersInfo.put("Deaths", 0);
            PlayersInfo.put("DeathLocations", new LinkedList<String>());
            PlayersInfo.put("IsAFK", false);
            PlayersInfo.put("IsFlying", false);
            DataWrapper.CreateUserProfile(PlayersInfo);
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
        if (Doc != null)
        {
            return new HashMap<>(Doc);
        }
        return new HashMap<>();
    }

    public static boolean IsUserExists(String UUID)
    {
        Document Temp = MainCollection.find(Filters.eq("UUID", UUID)).first();
        return Temp != null;
    }

    public static boolean IsDataExists(String Key)
    {
        Document Temp = DataCollection.find(Filters.exists(Key)).first();
        return Temp != null;
    }

    public static void AddUserProfile(String Key, Object Value, String UUID)
    {
        MainCollection.findOneAndUpdate(Filters.eq("UUID", UUID), Updates.push(Key, Value));
    }

    public static void ModifyUserProfile(String UUID, String Key, int Value)
    {
        MainCollection.findOneAndUpdate(Filters.eq("UUID", UUID), Updates.set(Key, Value));
    }

    public static void ModifyUserProfile(String UUID, String Key, boolean Value)
    {
        MainCollection.findOneAndUpdate(Filters.eq("UUID", UUID), Updates.set(Key, Value));
    }

    public static void CreateData(String Key, Object Value)
    {
        Document Doc = new Document();
        Doc.append(Key, Value);
        DataCollection.insertOne(Doc);
        ConsoleLogger.info("Successfully updated data profiles.");
    }

    public static void AddData(String ListName, String Key, Object Value)
    {
        DataCollection.updateOne(Filters.exists(ListName), Updates.set(ListName + "." + Key, Value));
    }

    public static void RemoveData(String ListName, String Key)
    {
        DataCollection.updateOne(Filters.exists(ListName), Updates.unset(ListName + "." + Key));
    }

    public static boolean IsWarpExists(String Warp)
    {
        return GetWarpsMap().containsKey(Warp);
    }

    public static HashMap<String, String> GetWarpsMap()
    {
        Bson Filter = Filters.exists("Warps");
        Document Doc = DataCollection.find(Filter).first();

        if (Doc != null && Doc.containsKey("Warps"))
        {
            Document WarpsDoc = (Document) Doc.get("Warps");
            HashMap<String, String> WarpsMap = new HashMap<>();
            for (Map.Entry<String, Object> Entry : WarpsDoc.entrySet())
            {
                WarpsMap.put(Entry.getKey(), Entry.getValue().toString());
            }
            return WarpsMap;
        }
        else
        {
            return new HashMap<>();
        }
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
}
