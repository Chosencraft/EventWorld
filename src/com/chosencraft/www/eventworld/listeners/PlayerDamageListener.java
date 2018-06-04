package com.chosencraft.www.eventworld.listeners;

import com.chosencraft.www.eventworld.EventWorldMain;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;


public class PlayerDamageListener implements Listener
{

    /**
     * Disables players from taking damage in the event world
     *
     * @param damageEvent EntityDamageEvent Passed down by Bukkit
     */
    @EventHandler
    public void onPlayerDamageEvent(EntityDamageEvent damageEvent)
    {
        if (damageEvent.getEntityType().equals(EntityType.PLAYER))
        {
            Player player = (Player) damageEvent.getEntity();

            if (player.getWorld().getUID().equals(EventWorldMain.eventWorldUUID))
            {
                damageEvent.setCancelled(true);
            }

        }
    }
}
