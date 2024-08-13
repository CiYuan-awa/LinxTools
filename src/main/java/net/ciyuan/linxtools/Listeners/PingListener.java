package net.ciyuan.linxtools.Listeners;

import net.ciyuan.linxtools.LinxTools;
import net.ciyuan.linxtools.Utils.ColorUtil;
import net.ciyuan.linxtools.Utils.ConfigurationUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import java.io.File;

public class PingListener implements Listener
{
    @EventHandler
    public void onServerListPing(ServerListPingEvent event)
    {
        ConfigurationUtil.RefreshConfig();
        if (!LinxTools.Config.getBoolean("LinxTools.ReplaceMotd"))
        {
            return;
        }
        String MotdLine1 = "辞愿的私人 SMP";
        String MotdLine2 = "哟 我现在来到中心岛 我的感觉非常好 我的韵脚能把你干倒";
        MotdLine1 = ColorUtil.Gradient(MotdLine1, "#FF0000", "#FFA500", "#FFFF00", "#008000", "#0000FF", "#4B0082", "#EE82EE");
        MotdLine2 = ColorUtil.Gradient(MotdLine2, "#FF0000", "#FFA500", "#FFFF00", "#008000", "#0000FF", "#4B0082", "#EE82EE");
        event.setMotd(MotdLine1 + "\n" + ChatColor.WHITE + MotdLine2);
        event.setMaxPlayers(LinxTools.Instance.getServer().getMaxPlayers());
        try {
            File iconFile = new File(LinxTools.Instance.getDataFolder(), "Icon.png");
            event.setServerIcon(Bukkit.getServer().loadServerIcon(iconFile));
        } catch (Exception e) {
            LinxTools.ConsoleLogger.warning("Failed to set server icon: " + e.getMessage());
        }
    }
}
