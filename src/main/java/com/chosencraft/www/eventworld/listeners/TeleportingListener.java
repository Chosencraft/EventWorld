package com.chosencraft.www.eventworld.listeners;

import com.chosencraft.www.eventworld.EventWorldMain;
import com.chosencraft.www.eventworld.Permissions;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportingListener implements Listener
{

    @EventHandler
    public void onTeleport(PlayerTeleportEvent teleportEvent)
    {
        Player player = teleportEvent.getPlayer();

        // return if not in event world
        if (!(teleportEvent.getTo().getWorld().getUID().equals(EventWorldMain.eventWorldUUID)))
        {
            return;
        }

        if (player.hasPermission(Permissions.EVENT_WORLD_TELEPORT_OVERRIDE))
        {
            return;
        }

        if (teleportEvent.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL))
        {
            //TODO: check if region allows pearl teleporting
            //TODO: permission?
            teleportEvent.setCancelled(true);

        }
        if (teleportEvent.getCause().equals(PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT))
        {
            //TODO: check if region allows chorus fruit teleporting
            //TODO: permission?
            teleportEvent.setCancelled(true);
        }
        player.sendMessage(ChatColor.RED + "You can't use that here!");
    }
}
