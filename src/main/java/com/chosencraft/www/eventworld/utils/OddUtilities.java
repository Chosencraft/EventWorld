package com.chosencraft.www.eventworld.utils;

import com.chosencraft.www.eventworld.events.Events;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class OddUtilities
{

    public static void sendPlayerToSpawn(Player player)
    {
        Location spawn = Bukkit.getWorlds().get(0).getSpawnLocation();
        player.teleport(spawn);
    }

    public static Events stringToEvent(String string)
    {
        switch (string.toLowerCase())
        {
            case "spleef":
                return Events.SPLEEF;
            case "parkour":
                return Events.PARKOUR;
            case "ctf":
                return Events.CTF;
            case "dodge":
                return Events.DODGE;
            case "battleship":
                return Events.BATTLESHIP;
            case "payload":
                return Events.PAYLOAD;
                default:
                    return Events.UNKNOWN;
        }
    }
}
