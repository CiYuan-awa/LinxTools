package net.ciyuan.linxtools.Commands;

import net.ciyuan.linxtools.Wrappers.DataWrapper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UpdateDataCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender Sender, Command Cmd, String Label, String[] Args)
    {
        if (Args.length != 0)
        {
            Sender.sendMessage("§c该命令不需要输入参数。");
            return false;
        }
        DataWrapper.UpdateData();
        Sender.sendMessage("§a成功更新所有数据。");
        return true;
    }
}
