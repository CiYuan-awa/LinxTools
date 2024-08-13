package net.ciyuan.linxtools.Listeners;

import net.ciyuan.linxtools.LinxTools;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ChatListener implements Listener
{
    private static final long CHAT_COOLDOWN = 1500L;
    private static String FormattedMessage;

    @EventHandler(priority = EventPriority.HIGHEST)
    void OnChat(AsyncPlayerChatEvent e) {
        if (Bukkit.getPluginManager().getPlugin("ManHunt") != null)
        {
            return;
        }
        e.setCancelled(true);

        Player player = e.getPlayer();
        LuckPerms luckperms = LuckPermsProvider.get();
        User user = luckperms.getPlayerAdapter(Player.class).getUser(player);
        String Name = player.getName();
        String Prefix = "";
        String Suffix = "";
        try
        {
            Prefix = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
            Suffix = user.getCachedData().getMetaData().getSuffix().replace("&", "§");
        } catch (Exception ignored) { }
        String PlayerName = Prefix + Name + Suffix;

        if (!player.hasPermission("linxtools.chatbypass")) {
            if (LinxTools.ChatCooldown.containsKey(player.getUniqueId())) {
                long lastChatTime = LinxTools.ChatCooldown.get(player.getUniqueId());
                long currentTime = System.currentTimeMillis();

                if (currentTime - lastChatTime < CHAT_COOLDOWN) {
                    player.sendMessage(ChatColor.RED + "请不要频繁发送消息！");
                    return;
                }
            }

            LinxTools.ChatCooldown.put(player.getUniqueId(), System.currentTimeMillis());
        }

        String Message = e.getMessage();
        FormattedMessage = PlayerName + ChatColor.GRAY + " >>> " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Message);
        if (Name.equals("IQ_Boost"))
        {
            FormattedMessage = PlayerName + ChatColor.WHITE + " >>> " + ChatColor.GOLD + ChatColor.translateAlternateColorCodes('&', Message);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getServer().broadcastMessage(FormattedMessage);
            }
        }.runTaskAsynchronously(LinxTools.Instance);
    }
}
