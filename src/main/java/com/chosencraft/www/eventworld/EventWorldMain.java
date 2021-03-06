package com.chosencraft.www.eventworld;

import com.chosencraft.www.eventworld.listeners.BlockHandlingListener;
import com.chosencraft.www.eventworld.listeners.TeleportingListener;
import com.chosencraft.www.eventworld.utils.StaffNotifier;
import com.chosencraft.www.eventworld.commands.EventWorld;
import com.chosencraft.www.eventworld.listeners.FlyingListener;
import com.chosencraft.www.eventworld.listeners.PlayerDamageListener;
import com.chosencraft.www.eventworld.utils.Logger;
import com.chosencraft.www.eventworld.utils.OddUtilities;
import com.chosencraft.www.eventworld.world.GenerateWorlds;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class EventWorldMain extends JavaPlugin
{
    private Logger log = Logger.getInstance();
    private String eventWorldName = "EventWorld";
    public static UUID eventWorldUUID;
    private StaffNotifier notifier = new StaffNotifier();

    private static Plugin plugin;

    /**
     * First call method of when the plugin is enabled
     */
    public void onEnable()
    {
        this.plugin = this;
        saveDefaultConfig();

        // Check if the event world already exists
        if (Bukkit.getWorld(eventWorldName) == null && getConfig().getBoolean("GenerateEventWorld"))
        {
            eventWorldUUID = createEventWorld();
        }

        registerEvents();
        registerCommands();

    }

    /**
     * Last Call method of when the plugin is to be disabled
     * Usually caused by server shutdown, but can also
     * be caused by plugin
     */
    public void onDisable()
    {
        //  Send all players in the event world to main spawn
        World eventWorld = Bukkit.getWorld(eventWorldUUID);
        if (eventWorld != null)
        {
            List<Player> players = eventWorld.getPlayers();

            for (Player player : players)
            {
                OddUtilities.sendPlayerToSpawn(player);
            }

        }

        // unload the event world, important to do since without the plugin it isn't protected

        boolean unloadedSuccefully = Bukkit.unloadWorld(eventWorld, true);
        if (! unloadedSuccefully)
        {
            log.logError(eventWorldName + " WAS UNABLE TO BE UNLOADED PROPERLY! Consider full server shutdown");
            notifier.notifyAdmins(Level.SEVERE, eventWorldName + " WAS UNABLE TO BE UNLOADED PROPERLY! Consider full server shutdown");
            notifier.notifyModerators(Level.SEVERE, eventWorldName + " WAS UNABLE TO BE UNLOADED PROPERLY! Consider full server shutdown");
        }

    }

    /**
     * Registers all the plugin events to pass off to the Bukkit manager
     */
    private void registerEvents()
    {
        PluginManager manager = this.getServer().getPluginManager();

        manager.registerEvents(new FlyingListener(), this);
        manager.registerEvents(new PlayerDamageListener(), this);
        manager.registerEvents(new BlockHandlingListener(), this);
        manager.registerEvents(new TeleportingListener(), this);

    }


    /**
     * Registers the plugins commands
     */
    private void registerCommands()
    {
        this.getCommand("eventworld").setExecutor(new EventWorld());
    }


    /**
     * Creates the EventWorld
     *
     * @return The name of the newly created EventWorld
     */
    private UUID createEventWorld()
    {
        World eventWorld = GenerateWorlds.createWorld(
                eventWorldName,
                World.Environment.NORMAL,
                false,
                WorldType.CUSTOMIZED,
                true);

        if (eventWorld == null)
        {
            log.logError("Event World was unable to be created! consider contingency");
            return null;
        }

        eventWorld.setAmbientSpawnLimit(0);
        eventWorld.setAnimalSpawnLimit(0);
        eventWorld.setDifficulty(Difficulty.PEACEFUL);
        eventWorld.setPVP(true); // Needs to be true for CTF, will need to handle other things on a regional basis
        eventWorld.setMonsterSpawnLimit(0);

        return eventWorld.getUID();
    }


    public static final Plugin getThisPlugin()
    {
        return plugin;
    }

}

