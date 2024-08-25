package net.ciyuan.linxtools.Listeners;

import net.ciyuan.linxtools.Wrappers.DataWrapper;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

public class PlayerJoinListener implements Listener
{

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void OnPlayerJoin(PlayerJoinEvent e)
    {
        DataWrapper.UpdateData();
        if (Bukkit.getPluginManager().getPlugin("ManHunt") != null)
        {
            return;
        }
        e.setJoinMessage(null);
        Server server = Bukkit.getServer();
        Player player = e.getPlayer();
        boolean AllowFlight = (boolean) DataWrapper.GetUserProfile(player.getUniqueId().toString()).get("IsFlying");
        player.setAllowFlight(AllowFlight);
        player.setFlying(AllowFlight);
        LuckPerms luckperms = LuckPermsProvider.get();
        User user = luckperms.getPlayerAdapter(Player.class).getUser(player);
        String Name = player.getName();
        String Prefix = "";
        String Suffix = "";
        try
        {
            Prefix = Objects.requireNonNull(user.getCachedData().getMetaData().getPrefix()).replace("&", "§");
            Suffix = Objects.requireNonNull(user.getCachedData().getMetaData().getSuffix()).replace("&", "§");
        } catch (Exception ignored) { }

        String PlayerName = Prefix + Name + Suffix;
        player.setDisplayName(PlayerName);
        player.setPlayerListName(PlayerName);
        Scoreboard board = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
        Team team = board.getTeam(player.getName());
        if (team == null) {
            team = board.registerNewTeam(Name);
        }
        team.setPrefix(Prefix);
        team.setSuffix(Suffix);
        team.addEntry(player.getName());
        String Header = "&c&l辞愿 ".replace("&", "§") + "§f私人" + " &b&lSMP\n".replace("&", "§");
        String Footer = "\n&c>&a>&b> &e&nmc.iqboost.net&f &b<&a<&c<".replace("&", "§");
        player.setPlayerListHeaderFooter(Header, Footer);

        if (player.getName().equals("IQ_Boost"))
        {
            server.broadcastMessage(ChatColor.YELLOW + "[LinxTools] " + ChatColor.GOLD + "本插件开发者 " + PlayerName + ChatColor.GREEN + " 加入了游戏！");
        }
        server.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.GRAY + "] " + PlayerName);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void OnPlayerQuit(PlayerQuitEvent e)
    {
        if (Bukkit.getPluginManager().getPlugin("ManHunt") != null)
        {
            return;
        }
        e.setQuitMessage(null);
        Server server = Bukkit.getServer();
        Player player = e.getPlayer();
        LuckPerms luckperms = LuckPermsProvider.get();
        User user = luckperms.getPlayerAdapter(Player.class).getUser(player);
        String Name = player.getName();
        String Prefix = "";
        String Suffix = "";
        try
        {
            Prefix = Objects.requireNonNull(user.getCachedData().getMetaData().getPrefix()).replace("&", "§");
            Suffix = Objects.requireNonNull(user.getCachedData().getMetaData().getSuffix()).replace("&", "§");
        } catch (Exception ignored) { }
        String PlayerName = Prefix + Name + Suffix;
        server.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.RED + "-" + ChatColor.GRAY + "] " + PlayerName);

        player.setDisplayName("");
        player.setPlayerListName("");
        BaseComponent Header = new TextComponent("");
        BaseComponent Footer = new TextComponent("");
        player.setPlayerListHeaderFooter(Header.toString(), Footer.toString());
    }
}
