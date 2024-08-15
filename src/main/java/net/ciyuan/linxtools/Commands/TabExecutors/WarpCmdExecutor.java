package net.ciyuan.linxtools.Commands.TabExecutors;

import net.ciyuan.linxtools.Wrappers.DataWrapper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class WarpCmdExecutor implements TabExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender Sender, @NotNull Command Cmd, @NotNull String Label, @NotNull String[] Args) { return false; }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender Sender, @NotNull Command Cmd, @NotNull String Label, @NotNull String[] Args)
    {
        LinkedList<String> Tips = new LinkedList<>();
        if (Args.length == 1)
        {
            List<String> FirstArgTip = DataWrapper.GetWarpsMap().keySet().stream().toList();
            DataWrapper.UpdateData();
            if (Args[0].isEmpty())
            {
                Tips.addAll(FirstArgTip);
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
            }
            return Tips;
        }
        return Tips;
    }
}
