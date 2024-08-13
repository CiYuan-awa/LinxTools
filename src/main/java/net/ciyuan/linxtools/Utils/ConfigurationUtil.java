package net.ciyuan.linxtools.Utils;

import net.ciyuan.linxtools.LinxTools;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static net.ciyuan.linxtools.LinxTools.ConsoleLogger;

public class ConfigurationUtil
{
    public static void InitProfile()
    {
        //Init Data Folder
        File DataFolder = LinxTools.Instance.getDataFolder();
        if (!DataFolder.exists()) {
            if (DataFolder.mkdirs()) {
                ConsoleLogger.info("Data folder created: " + DataFolder.getAbsolutePath());
            } else {
                ConsoleLogger.warning("Failed to create data folder: " + DataFolder.getAbsolutePath());
            }
        }

        //Init Plugin Configuration
        LinxTools.Instance.saveDefaultConfig();

        //Init Server Icon
        InputStream Icon = LinxTools.Instance.getClass().getResourceAsStream("/Icon.png");
        File DestFile = new File(DataFolder, "Icon.png");
        Path DestPath = Paths.get(DestFile.toURI());
        try {
            if (!DestFile.exists()) Files.copy(Icon, DestPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void RefreshConfig()
    {
        LinxTools.Config = LinxTools.Instance.getConfig();
    }
}
