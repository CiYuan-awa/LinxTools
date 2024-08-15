package net.ciyuan.linxtools.Commands;

import net.ciyuan.linxtools.LinxTools;
import net.ciyuan.linxtools.Utils.ProjectUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MainCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender Sender, @NotNull Command Cmd, @NotNull String Label, @NotNull String[] Args)
    {
        if (Args.length == 0)
        {
            Sender.sendMessage("§aRunning LinxTools Version §6§l" + ProjectUtil.GetVersion() + "\n" + "§aType §6\"/linx help\" §afor more helps.");
        }

        if (Args.length == 1)
        {
            switch (Args[0].toLowerCase())
            {
                case "reload" ->
                {
                    long Before = System.currentTimeMillis();
                    LinxTools.Instance.reloadConfig();
                    long After = System.currentTimeMillis();
                    Sender.sendMessage("§a配置文件已重载！(Took " + (After - Before) + " ms)");
                }
                case "help" ->
                {
                    LinxTools.ConsoleLogger.info("A player tried to get help.");
                    //写帮助内容！
                }
                default -> throw new IllegalStateException("Unexpected value: " + Args[0].toLowerCase());
            }
        }
        return false;
    }
}
