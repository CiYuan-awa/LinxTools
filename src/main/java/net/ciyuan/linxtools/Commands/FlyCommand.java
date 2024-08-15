package net.ciyuan.linxtools.Commands;

import net.ciyuan.linxtools.Utils.BukkitUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor
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
        if (Args.length == 0)
        {
            boolean AllowFlight = player.getAllowFlight();
            player.setAllowFlight(!AllowFlight);
            player.setFlying(!AllowFlight);
            player.sendMessage(AllowFlight ? "§c已关闭飞行模式！" : "§a已开启飞行模式！");
            return true;
        }
        if (Args.length == 1)
        {
            Player player1 = null;
            if (BukkitUtil.GetPlayerByName(Args[0]) != null)
            {
                player1 = BukkitUtil.GetPlayerByName(Args[0]);
            }
            if (player1 == null) return false;
            boolean AllowFlight = player1.getAllowFlight();
            player1.setAllowFlight(!AllowFlight);
            player1.setFlying(!AllowFlight);
            player.sendMessage(AllowFlight ? ("§c已关闭 §6" + player1.getName() + " §c的飞行模式！") : ("§a已开启 §6" + player1.getName() + " §a的飞行模式！"));
            player1.sendMessage(AllowFlight ? ("§6" + player.getName() + " §c已关闭你的飞行模式！") : ("§6" + player.getName() + " §a已开启你的飞行模式！"));
            return true;
        }
        return false;
    }
}
