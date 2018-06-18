package com.chosencraft.www.eventworld.events;

import com.chosencraft.www.eventworld.utils.SettingsManager;
import org.bukkit.Location;

public class Event
{

    /**
     * Type of event from Events Enum Class
     **/
    private Events eventType;
    /**
     * Location of the event spawn
     */
    private Location location;

    /* General file config */
    private SettingsManager data = SettingsManager.getData();


    public Event (Events eventType)
    {
        this.eventType = eventType;
    }

    /**
     * Retrieves type of event
     * @return The type of event
     */
    public Events getEventType()
    {
        return eventType;
    }

    /**
     * Returns the location of the events spawn
     *
     * @return Returns location of event spawn, or null if unset
     */
    public Location getSpawnLocation()
    {
        return this.location;
    }

    /**
     * Sets the location of the event spawn
     * @param location The location to set the event spawn, can be set to null to disable spawning
     */
    public void setSpawnLocation(Location location)
    {
        this.location = location;
        save(location);
    }

    private void save(Location location)
    {
        String eventName = eventType.toString().toUpperCase();

        if (!(data.contains(eventName)))
        {
            data.createSection(eventName);
        }
        data.createSection(eventName + ".location");
        data.createSection(eventName + ".location.World");
        data.createSection(eventName + ".location.X");
        data.createSection(eventName + ".location.Y");
        data.createSection(eventName + ".location.Z");
        data.createSection(eventName + ".location.Yaw");
        data.createSection(eventName + ".location.Pitch");
        data.set(eventName + ".location.World", location.getWorld());
        data.set(eventName + ".location.X", location.getBlockX());
        data.set(eventName + ".location.Y", location.getBlockY());
        data.set(eventName + ".location.Z", location.getBlockZ());
        data.set(eventName + ".location.Yaw", location.getYaw());
        data.set(eventName + ".location.Pitch", location.getPitch());

    }


}
