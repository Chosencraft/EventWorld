package com.chosencraft.www.eventworld.commands;

import com.chosencraft.www.eventworld.EventWorldMain;
import com.chosencraft.www.eventworld.Permissions;
import com.chosencraft.www.eventworld.events.Events;
import com.chosencraft.www.eventworld.utils.Logger;
import com.chosencraft.www.eventworld.utils.OddUtilities;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EventWorld implements CommandExecutor
{

    private Logger log = Logger.getInstance();
    private World eventWorld = Bukkit.getWorld(EventWorldMain.eventWorldUUID);
    private String badPermission = response("Sorry, you don't have permission for this!", true);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            log.logWarning(response( "Cannot change event world properties from console! ", true));
        }
        else {
            Player player = (Player) sender;

            if (!player.hasPermission(Permissions.COMMAND_GENERAL))
            {
                player.sendMessage(badPermission);
            }
            else {
                if (args[1] == null || args[1].isEmpty())
                {
                    sendHelpMessage(player);
                }
                else
                {
                    switch (args[1])
                    {
                        case "help":
                            sendHelpMessage(player);
                            break;
                        case "setspawn":
                            setEventSpawn(player);
                            break;
                        case "spawn":
                            sendToEventSpawn(player);
                            break;
                        case "setlocation":
                            setEventLocation(player, args);
                            break;

                            default:
                                sendHelpMessage(player);
                                break;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Sends the player the help message
     * @param player Player to send the help message
     */
    private void sendHelpMessage(Player player)
    {
        player.sendMessage(response("----------------------------", false));
        player.sendMessage(ChatColor.AQUA + "help: " + ChatColor.GREEN + "Displays this help menu" );
        player.sendMessage(ChatColor.AQUA + "setspawn: " + ChatColor.GREEN + "Sets the your current location spawn of the Event World" );
        player.sendMessage(ChatColor.AQUA + "spawn: " + ChatColor.GREEN + "Teleport to the Event World spawn!" );
        player.sendMessage(ChatColor.AQUA + "setlocation <event>" + ChatColor.GREEN + "Displays this help menu" );

    }

    /**
     * Sets the location for the various events
     * @param player Player who's setting the location
     * @param args Args passed down from the main command
     */
    private void setEventLocation(Player player, String[] args)
    {
        if (player.hasPermission(Permissions.COMMAND_SET_EVENT_LOCATION))
        {
            if (args[2] == null || args[2].isEmpty())
            {
                player.sendMessage(response("Need to specify the event name!", true));
                return;
            }
            else
            {
                Events event = OddUtilities.stringToEvent(args[2]);
                if (event.equals(Events.UNKNOWN))
                {
                    player.sendMessage(response("The event name " + args[2] + " doesn't exist!", true));
                    return;
                }
                else
                {
                    saveLocation(player.getLocation(), event);
                }
            }
        }
        else
        {
            player.sendMessage(badPermission);
        }
    }

    /**
     * Saves the event locations to a file
     * @param location Location to save
     * @param event Event to save as
     */
    private void saveLocation(Location location, Events event )
    {
        //TODO: save the location of the event to file
    }

    /**
     * Sends the player to the EventWorld spawn
     * @param player Player to send to the EventWorld spawn
     */
    private void sendToEventSpawn(Player player)
    {
        if (player.hasPermission(Permissions.COMMAND_SPAWN))
        {
            player.teleport(eventWorld.getSpawnLocation());
        }
        else
        {
            player.sendMessage(badPermission);
        }
    }

    /**
     * Sets the spawn of the event world
     * @param player Player setting the location
     */
    private void setEventSpawn(Player player)
    {
        if (player.hasPermission(Permissions.COMMAND_SET_SPAWN))
        {
            if (player.getWorld().equals(eventWorld))
            {
                eventWorld.setSpawnLocation(player.getLocation());
                player.sendMessage(response("Event world has been set!" , false));
            }
            else
            {
                player.sendMessage(response("You are not in the event world! ", true));
            }


        }
        else
        {
            player.sendMessage(badPermission);
        }

    }

    /**
     * Formats a response to send back to a player
     * @param message Message to format in
     * @return The formatted message
     */
    private String response (String message, boolean bad )
    {
        ChatColor status;

        if (bad)
        {
            status = ChatColor.DARK_RED;
        }
        else
        {
            status = ChatColor.GREEN;
        }

        return String.format(
                ChatColor.GOLD + "[" +
                        ChatColor.AQUA + "EventWorld" +
                        ChatColor.GOLD + "] " +
                        status + "%s" ,
                message);
    }
}
