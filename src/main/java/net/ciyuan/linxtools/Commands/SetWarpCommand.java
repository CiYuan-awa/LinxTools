package net.ciyuan.linxtools.Commands;

import net.ciyuan.linxtools.LinxTools;
import net.ciyuan.linxtools.Wrappers.DataWrapper;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SetWarpCommand implements CommandExecutor
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
        if (DataWrapper.IsWarpExists(Args[0]))
        {
            Sender.sendMessage("§c地标 §6" + Args[0] + " §c已存在！");
            return true;
        }
        try
        {
            Location Loc = player.getLocation();
            String WarpLocation = Objects.requireNonNull(Loc.getWorld()).getName() + " " + Loc.getX() + " " + Loc.getY() + " " + Loc.getZ() + " " + Loc.getYaw() + " " + Loc.getPitch();
            DataWrapper.AddData("Warps", Args[0], WarpLocation);
            player.sendMessage("§a成功设置地标！");
            return true;
        }
        catch (Exception e)
        {
            player.sendMessage("§c设置地标时出错！");
            LinxTools.ConsoleLogger.warning(e.toString());
            return false;
        }
    }
}
