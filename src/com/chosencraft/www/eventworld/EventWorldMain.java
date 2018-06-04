package com.chosencraft.www.eventworld;

import com.chosencraft.www.eventworld.listeners.FlyingListener;
import com.chosencraft.www.eventworld.world.GenerateWorlds;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class EventWorldMain extends JavaPlugin
{

    private String eventWorldName = "EventWorld";
    public static UUID eventWorldUUID;

    public void onEnable()
    {
        if (Bukkit.getWorld(eventWorldName) == null)
        {
            createEventWorld();
        }
        registerEvents();

    }

    public void onDisable()
    {

    }

    private void registerEvents()
    {
        PluginManager manager = this.getServer().getPluginManager();

        manager.registerEvents(new FlyingListener(), this);
    }

    private String createEventWorld()
    {

        World eventWorld =  GenerateWorlds.createWorld(
                eventWorldName,
                World.Environment.NORMAL,
                false,
                WorldType.CUSTOMIZED,
                true);

        eventWorld.setAmbientSpawnLimit(0);
        eventWorld.setAnimalSpawnLimit(0);
        eventWorld.setDifficulty(Difficulty.PEACEFUL);
        eventWorld.setPVP(false);
        eventWorld.setMonsterSpawnLimit(0);

        eventWorldUUID = eventWorld.getUID();

        return eventWorld.getName();
    }
}

