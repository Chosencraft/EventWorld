package com.chosencraft.www.eventworld.utils;

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
}
