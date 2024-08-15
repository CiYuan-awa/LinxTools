package net.ciyuan.linxtools;

import net.ciyuan.linxtools.Commands.*;
import net.ciyuan.linxtools.Commands.TabExecutors.*;
import net.ciyuan.linxtools.Listeners.*;
import net.ciyuan.linxtools.Utils.ConfigurationUtil;
import net.ciyuan.linxtools.Wrappers.DataWrapper;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;
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

        Objects.requireNonNull(Instance.getCommand("linx")).setExecutor(new MainCommand());
        Objects.requireNonNull(Instance.getCommand("gm")).setExecutor(new SwitchGamemodeCommand());
        Objects.requireNonNull(Instance.getCommand("update")).setExecutor(new UpdateDataCommand());
        Objects.requireNonNull(Instance.getCommand("suicide")).setExecutor(new SuicideCommand());
        Objects.requireNonNull(Instance.getCommand("afk")).setExecutor(new AFKCommand());
        Objects.requireNonNull(Instance.getCommand("fly")).setExecutor(new FlyCommand());
        Objects.requireNonNull(Instance.getCommand("invsee")).setExecutor(new InvSeeCommand());
        Objects.requireNonNull(Instance.getCommand("back")).setExecutor(new BackCommand());
        Objects.requireNonNull(Instance.getCommand("warp")).setExecutor(new WarpCommand());
        Objects.requireNonNull(Instance.getCommand("setwarp")).setExecutor(new SetWarpCommand());
        Objects.requireNonNull(Instance.getCommand("delwarp")).setExecutor(new DeleteWarpCommand());

        Objects.requireNonNull(Instance.getCommand("linx")).setTabCompleter(new MainCmdExecutor());
        Objects.requireNonNull(Instance.getCommand("gm")).setTabCompleter(new SwitchGamemodeCmdExecutor());
        Objects.requireNonNull(Instance.getCommand("fly")).setTabCompleter(new FlyCmdExecutor());
        Objects.requireNonNull(Instance.getCommand("invsee")).setTabCompleter(new InvSeeCmdExecutor());
        Objects.requireNonNull(Instance.getCommand("warp")).setTabCompleter(new WarpCmdExecutor());
        Objects.requireNonNull(Instance.getCommand("delwarp")).setTabCompleter(new WarpCmdExecutor());

        Bukkit.getPluginManager().registerEvents(new ChatListener(), Instance);
        Bukkit.getPluginManager().registerEvents(new PingListener(), Instance);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), Instance);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), Instance);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), Instance);

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
