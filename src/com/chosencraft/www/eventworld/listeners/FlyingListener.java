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
     * @param moveEvent
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
                    Location landingSpot = getSkyFacingBlock(location).add(0,1,0);
                    player.setFlying(false);
                    if (landingSpot == null)
                    {
                        // Return to main world spawm
                        player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                    }
                    else
                    {
                        player.teleport(landingSpot);
                    }
                }
            }

        }


    }

    private Location getSkyFacingBlock(Location location)
    {
        Location currentLocation = location;
        while (location.getBlock().getType().equals(Material.AIR))
        {
            if (location.getY() < 0)
            {
                return null;
            }

            currentLocation = currentLocation.subtract(0, 1, 0);

        }
        return currentLocation;
    }
}
