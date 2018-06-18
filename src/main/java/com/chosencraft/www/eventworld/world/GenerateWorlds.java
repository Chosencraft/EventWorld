package com.chosencraft.www.eventworld.world;

import com.chosencraft.www.eventworld.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

public class GenerateWorlds
{

    private static Logger log = Logger.getInstance();
    /**
     *
     * @param worldName Name of the Event World to be created to be created
     * @return Returns Null if failed, otherwise returns the World
     */
    public static World createWorld(String worldName, World.Environment environment, boolean generateStructures, WorldType worldType, boolean voidWorld)
    {
        if (Bukkit.getWorld(worldName) == null)
        {
            // create the com.chosencraft.www.eventworld.world
            WorldCreator worldCreator;
            try
            {
                 worldCreator = new WorldCreator(worldName);
            }
            catch (IllegalArgumentException exception)
            {
                log.logError("Name for generated event world cannot be null!");
                return null;
            }

            worldCreator.environment(environment);
            worldCreator.generateStructures(generateStructures);
            worldCreator.type(worldType);

            if (voidWorld)
            {
                worldCreator.generator(new VoidGenerator());
            }

            return Bukkit.createWorld(worldCreator);
        }
        else
        {
            return null;
        }
    }
}
