package net.ciyuan.linxtools.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.ciyuan.linxtools.Listeners.PlayerDeathListener.GetDeaths;

public class SuicideCommand implements CommandExecutor
{
    public static boolean DisplayDeathMessage = false;
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
        if (Args.length != 0)
        {
            Sender.sendMessage("§c该命令不需要输入参数。");
            return false;
        }
        DisplayDeathMessage = false;
        Bukkit.broadcastMessage("§c" + player.getName() + " §f结束了自己的生命！喵呜~好疼！"+ " §f这是Ta的第 §e#" + GetDeaths(player.getUniqueId().toString()) + " §f次死亡！");
        player.setHealth(0);
        DisplayDeathMessage = true;
        return true;
    }

}
