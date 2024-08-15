package net.ciyuan.linxtools.Commands;

import net.ciyuan.linxtools.LinxTools;
import net.ciyuan.linxtools.Wrappers.DataWrapper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DeleteWarpCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender Sender, @NotNull Command Cmd, @NotNull String Label, @NotNull String[] Args)
    {
        Player player;
        if (!(Sender instanceof Player))
        {
            Sender.sendMessage("§c该命令只能由玩家使用。");
            return false;
        }
        player = (Player) Sender;
        if (Args.length != 1) return false;
        if (!DataWrapper.IsWarpExists(Args[0]))
        {
            Sender.sendMessage("§c地标 §6" + Args[0] + " §c不存在！");
            return true;
        }
        try
        {
            DataWrapper.RemoveData("Warps", Args[0]);
            player.sendMessage("§a成功删除地标！");
            return true;
        }
        catch (Exception e)
        {
            player.sendMessage("§c删除地标时出错！");
            LinxTools.ConsoleLogger.warning(e.toString());
            return false;
        }
    }
}
