package net.ciyuan.linxtools.Listeners;

import net.ciyuan.linxtools.Wrappers.DataWrapper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener
{
    @EventHandler
    public void OnPlayerMove(PlayerMoveEvent Event)
    {
        Event.setCancelled((boolean) DataWrapper.GetUserProfile(Event.getPlayer().getUniqueId().toString()).get("IsAFK"));
    }
}
