package net.ciyuan.linxtools.Commands;

import net.ciyuan.linxtools.LinxTools;
import net.ciyuan.linxtools.Wrappers.DataWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender Sender, Command Cmd, String Label, String[] Args)
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
            String[] WarpLocation = DataWrapper.GetWarpsMap().get(Args[0]).split(" ");
            Location Loc = new Location(Bukkit.getServer().getWorld(WarpLocation[0]), Double.parseDouble(WarpLocation[1]), Double.parseDouble(WarpLocation[2]), Double.parseDouble(WarpLocation[3]), Float.parseFloat(WarpLocation[4]), Float.parseFloat(WarpLocation[5]));
            player.teleport(Loc);
            player.sendMessage("§a成功传送到地标 §6" + Args[0] + "§a！");
            return true;
        }
        catch (Exception e)
        {
            player.sendMessage("§c传送地标时出错！");
            LinxTools.ConsoleLogger.warning(e.toString());
            return false;
        }
    }
}
