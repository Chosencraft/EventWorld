package com.chosencraft.www.eventworld.listeners;

import com.chosencraft.www.eventworld.EventWorldMain;
import com.chosencraft.www.eventworld.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FlyingListener implements Listener
{


    /**
     * Handles players flying in the event world
     *
     * @param moveEvent PlayerMoveEvent passed down by Bukkit
     */
    @EventHandler
    public void onFlying(PlayerMoveEvent moveEvent)
    {
        Player player = moveEvent.getPlayer();

        // Check if in event world
        if (player.getWorld().getUID().equals(EventWorldMain.eventWorldUUID))
        {
            // permission check
            if (player.hasPermission(Permissions.FLY_IN_EVENT_WORLD))
            {
                return;
            }
            else
            {
                /*
                 * If player is flying, disable flying and
                 * teleport them to the nearest ground spot,
                 * if that fails teleport them back to the main
                 * spawn
                 */
                if (player.isFlying())
                {
                    Location location = player.getLocation();
                    Location landingSpot = getGroundingBlock(location);
                    player.setFlying(false);
                    if (landingSpot == null)
                    {
                        // Return to main world spawm
                        player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                    }
                    else
                    {
                        player.teleport(landingSpot.add(0,1,0));
                    }
                }
            }

        }


    }

    /**
     * Get the nearest solid block player can land on
     * @param playerLocation Location to find block of
     * @return Location to spawn on
     */
    private Location getGroundingBlock(Location playerLocation)
    {

        while (playerLocation.getBlock().isEmpty())
        {
            playerLocation.subtract(0, 1, 0);
            if ( playerLocation.getBlockY() < 0)
            {
                // unable to find replacement, sending to spawn
                return Bukkit.getWorlds().get(0).getSpawnLocation();
            }
        }
        return playerLocation.add(0,1,0);
    }

}
