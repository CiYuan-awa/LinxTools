package net.ciyuan.linxtools.Commands.TabExecutors;

import net.ciyuan.linxtools.Utils.BukkitUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FlyCmdExecutor implements TabExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender Sender, @NotNull Command Cmd, @NotNull String Label, @NotNull String[] Args) { return false; }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender Sender, @NotNull Command Cmd, @NotNull String Label, @NotNull String[] Args)
    {
        return BukkitUtil.GetPlayerListForTabCompletion(Args, 0);
    }
}
