package com.chosencraft.www.eventworld.listeners;

import com.chosencraft.www.eventworld.EventWorldMain;
import com.chosencraft.www.eventworld.Permissions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockHandlingListener implements Listener
{

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent placeEvent)
    {
        if (!(placeEvent.getBlock().getWorld().getUID().equals(EventWorldMain.eventWorldUUID)))
        {
            // Ignore if not in the event world
            return;
        }
        Player player = placeEvent.getPlayer();
        if (player.hasPermission(Permissions.EVENT_WORLD_BLOCK_PLACE_OVERRIDE))
        {
            return;
        }
        //TODO: iteratore over areas
        placeEvent.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent breakEvent)
    {
        if (!(breakEvent.getBlock().getWorld().getUID().equals(EventWorldMain.eventWorldUUID)))
        {
            // Ignore if not in the event world
            return;
        }
        Player player = breakEvent.getPlayer();
        if (player.hasPermission(Permissions.EVENT_WORLD_BLOCK_BREAK_OVERRIDE))
        {
            return;
        }
        //TODO: iteratore over areas
        breakEvent.setCancelled(true);
    }
}
