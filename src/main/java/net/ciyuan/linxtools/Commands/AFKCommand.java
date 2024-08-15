package net.ciyuan.linxtools.Commands;

import net.ciyuan.linxtools.Wrappers.DataWrapper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AFKCommand implements CommandExecutor
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
        if (Args.length != 0)
        {
            Sender.sendMessage("§c该命令不需要输入参数。");
            return false;
        }
        boolean IsAFK = (boolean) DataWrapper.GetUserProfile(player.getUniqueId().toString()).get("IsAFK");
        player.setInvulnerable(!IsAFK);
        Bukkit.broadcastMessage(IsAFK ? "§6" + player.getName() + " §a回来了。" : "§6" + player.getName() + " §c开始挂机。");
        DataWrapper.ModifyUserProfile(player.getUniqueId().toString(), "IsAFK", !IsAFK);
        return true;
    }
}
