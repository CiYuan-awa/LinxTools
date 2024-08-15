package net.ciyuan.linxtools.Commands;

import net.ciyuan.linxtools.LinxTools;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SwitchGamemodeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender Sender, Command Cmd, String Label, String[] Args)
    {
        Player player;
        if (!(Sender instanceof Player))
        {
            Sender.sendMessage(ChatColor.DARK_RED + "该命令只有游戏内玩家可用！");
            return false;
        } else { player = (Player) Sender; }
        if (Args.length == 0) return false;
        else if (Args.length == 1)
        {
            switch (Args[0]) {
                case "0" -> {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(ChatColor.GREEN + "已成功将你的游戏模式设置为" + ChatColor.GOLD + "生存模式");
                }
                case "1" -> {
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(ChatColor.GREEN + "已成功将你的游戏模式设置为" + ChatColor.GOLD + "创造模式");
                }
                case "2" -> {
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(ChatColor.GREEN + "已成功将你的游戏模式设置为" + ChatColor.GOLD + "冒险模式");
                }
                case "3" -> {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(ChatColor.GREEN + "已成功将你的游戏模式设置为" + ChatColor.GOLD + "旁观模式");
                }
                default -> {
                }
            }
            return true;
        }
        else if (Args.length == 2)
        {
            try {
                Player player1 = Bukkit.getPlayer(Args[1]);
                if (player1 == null)
                {
                    player.sendMessage(ChatColor.RED + "玩家 " + ChatColor.GOLD + Args[1] + ChatColor.RED + " 不存在或不在线！");
                    return true;
                }
                switch (Args[0]) {
                    case "0" -> {
                        player1.setGameMode(GameMode.SURVIVAL);
                        player.sendMessage(ChatColor.GREEN + "已成功将" + ChatColor.YELLOW + player1.getName() + ChatColor.GREEN + "的游戏模式设置为" + ChatColor.GOLD + "生存模式");
                        player1.sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + "已将你的游戏模式设置为" + ChatColor.GOLD + "生存模式");
                    }
                    case "1" -> {
                        player1.setGameMode(GameMode.CREATIVE);
                        player.sendMessage(ChatColor.GREEN + "已成功将" + ChatColor.YELLOW + player1.getName() + ChatColor.GREEN + "的游戏模式设置为" + ChatColor.GOLD + "创造模式");
                        player1.sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + "已将你的游戏模式设置为" + ChatColor.GOLD + "创造模式");
                    }
                    case "2" -> {
                        player1.setGameMode(GameMode.ADVENTURE);
                        player.sendMessage(ChatColor.GREEN + "已成功将" + ChatColor.YELLOW + player1.getName() + ChatColor.GREEN + "的游戏模式设置为" + ChatColor.GOLD + "冒险模式");
                        player1.sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + "已将你的游戏模式设置为" + ChatColor.GOLD + "冒险模式");
                    }
                    case "3" -> {
                        player1.setGameMode(GameMode.SPECTATOR);
                        player.sendMessage(ChatColor.GREEN + "已成功将" + ChatColor.YELLOW + player1.getName() + ChatColor.GREEN + "的游戏模式设置为" + ChatColor.GOLD + "旁观模式");
                        player1.sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + "已将你的游戏模式设置为" + ChatColor.GOLD + "旁观模式");
                    }
                    default -> {
                        player.sendMessage(ChatColor.RED + "格式错误！用法：");
                        return false;
                    }
                }
                return true;
            } catch (Exception exception) {
                player.sendMessage(ChatColor.RED + "格式错误！用法：");
                return false;
            }
        }
        else return false;
    }
}
