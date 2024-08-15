package net.ciyuan.linxtools.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BukkitUtil
{
    public static Player GetPlayerByName(String Name)
    {
        return Bukkit.getOnlinePlayers().stream()
                .filter(P -> P.getName().equalsIgnoreCase(Name))
                .findFirst()
                .orElse(null);
    }

    public static LinkedList<String> GetPlayerListForTabCompletion(String[] Args, int Pos)
    {
        LinkedList<String> Tips = new LinkedList<>();
        List<String> ArgTip = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers())
        {
            ArgTip.add(p.getName());
        }
        if (Args[Pos].isEmpty())
        {
            Tips.addAll(ArgTip);
        }
        else
        {
            for (String Arg : ArgTip)
            {
                if (Arg.toLowerCase().startsWith(Args[Pos].toLowerCase()))
                {
                    Tips.add(Arg);
                }
            }
        }
        return Tips;
    }
}
