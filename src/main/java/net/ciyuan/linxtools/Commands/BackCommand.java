package net.ciyuan.linxtools.Commands;

import net.ciyuan.linxtools.Wrappers.DataWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackCommand implements CommandExecutor
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
        if (Args.length != 0)
        {
            Sender.sendMessage("§c该命令不需要输入参数。");
            return false;
        }
        String[] DeathLocation = DataWrapper.GetDeathLocation(player.getUniqueId().toString()).getLast().split(" ");
        Location Loc = new Location(Bukkit.getServer().getWorld(DeathLocation[0]), Double.parseDouble(DeathLocation[1]), Double.parseDouble(DeathLocation[2]), Double.parseDouble(DeathLocation[3]), Float.parseFloat(DeathLocation[4]), Float.parseFloat(DeathLocation[5]));
        player.teleport(Loc);
        player.sendMessage("§a已成功回到死亡地点。");
        return true;
    }
}
