package net.ciyuan.linxtools.Commands.TabExecutors;

import net.ciyuan.linxtools.Utils.BukkitUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SwitchGamemodeCmdExecutor implements TabExecutor
{
    @Override
    public boolean onCommand(CommandSender Sender, Command Cmd, String Label, String[] Args)
    {
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender Sender, Command Cmd, String Label, String[] Args)
    {
        LinkedList<String> Tips = new LinkedList<>();
        if (Args.length == 1)
        {
            List<String> FirstArgTip = Arrays.asList("0", "1", "2", "3");
            if (Args[0].isEmpty())
            {
                Tips.addAll(FirstArgTip);
                return Tips;
            }
            else
            {
                for (String FirstArg : FirstArgTip)
                {
                    if (FirstArg.toLowerCase().startsWith(Args[0].toLowerCase()))
                    {
                        Tips.add(FirstArg);
                    }
                }
                return Tips;
            }
        }
        if (Args.length == 2)
        {
            return BukkitUtil.GetPlayerListForTabCompletion(Args, 1);
        }
        return Tips;
    }
}
