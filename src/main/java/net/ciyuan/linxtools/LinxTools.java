package net.ciyuan.linxtools;

import net.ciyuan.linxtools.Commands.*;
import net.ciyuan.linxtools.Commands.TabExecutors.MainCmdExecutor;
import net.ciyuan.linxtools.Commands.TabExecutors.SwitchGamemodeCmdExecutor;
import net.ciyuan.linxtools.Commands.TabExecutors.WarpCmdExecutor;
import net.ciyuan.linxtools.Listeners.ChatListener;
import net.ciyuan.linxtools.Listeners.PingListener;
import net.ciyuan.linxtools.Listeners.PlayerDeathListener;
import net.ciyuan.linxtools.Listeners.PlayerJoinListener;
import net.ciyuan.linxtools.Utils.ConfigurationUtil;
import net.ciyuan.linxtools.Wrappers.DataWrapper;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

public final class LinxTools extends JavaPlugin
{
    public static LinxTools Instance;
    public static Logger ConsoleLogger;
    public static FileConfiguration Config;
    public static HashMap<UUID, Long> ChatCooldown;

    @Override
    public void onEnable()
    {
        Instance = this;
        ConsoleLogger = Instance.getLogger();
        ChatCooldown = new HashMap<>();
        ConfigurationUtil.RefreshConfig();
        ConfigurationUtil.InitProfile();
        DataWrapper.InitConnection();

        Instance.getCommand("linx").setExecutor(new MainCommand());
        Instance.getCommand("gm").setExecutor(new SwitchGamemodeCommand());
        Instance.getCommand("update").setExecutor(new UpdateDataCommand());
        Instance.getCommand("suicide").setExecutor(new SuicideCommand());
        Instance.getCommand("back").setExecutor(new BackCommand());
        Instance.getCommand("warp").setExecutor(new WarpCommand());
        Instance.getCommand("setwarp").setExecutor(new SetWarpCommand());
        Instance.getCommand("delwarp").setExecutor(new DeleteWarpCommand());

        Instance.getCommand("linx").setTabCompleter(new MainCmdExecutor());
        Instance.getCommand("gm").setTabCompleter(new SwitchGamemodeCmdExecutor());
        Instance.getCommand("warp").setTabCompleter(new WarpCmdExecutor());
        Instance.getCommand("delwarp").setTabCompleter(new WarpCmdExecutor());

        Bukkit.getPluginManager().registerEvents(new ChatListener(), Instance);
        Bukkit.getPluginManager().registerEvents(new PingListener(), Instance);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), Instance);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), Instance);

        ConsoleLogger.info("Welcome to use Linx Tools!");
        ConsoleLogger.info("By CiYuan!");
    }

    @Override
    public void onDisable()
    {
        Instance = null;
        ConsoleLogger.info("Linx Tools has been disabled. Welcome to use again!");
    }

}
