package net.ciyuan.linxtools.Commands;

import net.ciyuan.linxtools.Utils.BukkitUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InvSeeCommand implements CommandExecutor
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
        if (Args.length != 1) return false;
        player = (Player) Sender;
        Player Target = BukkitUtil.GetPlayerByName(Args[0]);
        Inventory Inv = Target.getInventory();
        player.openInventory(Inv);
        return true;
    }
}
