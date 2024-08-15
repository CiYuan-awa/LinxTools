package net.ciyuan.linxtools.Commands.TabExecutors;

import net.ciyuan.linxtools.Utils.BukkitUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;

public class InvSeeCmdExecutor implements TabExecutor
{
    @Override
    public boolean onCommand(CommandSender Sender, Command Cmd, String Label, String[] Args)
    {
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender Sender, Command Cmd, String Label, String[] Args)
    {
        return BukkitUtil.GetPlayerListForTabCompletion(Args, 0);
    }
}
