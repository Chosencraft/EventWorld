package com.chosencraft.www.eventworld.commands;

import com.chosencraft.www.eventworld.EventWorldMain;
import com.chosencraft.www.eventworld.Permissions;
import com.chosencraft.www.eventworld.events.Event;
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
                if ((args.length == 0) || args[0] == null || args[0].isEmpty())
                {
                    sendHelpMessage(player);
                }
                else
                {
                    switch (args[0])
                    {
                        case "help":
                            sendHelpMessage(player);
                            break;
                        case "setspawn":
                            setEventWorldSpawn(player);
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
            for (String n : args)
            {
                System.out.println(n);
            }
            if (args[0] == null || args[0].isEmpty() || args.length <= 1)
            {
                player.sendMessage(response("Need to specify the event name!", true));
                return;
            }
            else
            {
                Events event = OddUtilities.stringToEvent(args[1]);
                if (event.equals(Events.UNKNOWN))
                {
                    player.sendMessage(response("The event name " + args[1] + " doesn't exist!", true));
                    return;
                }
                else
                {
                    saveLocation(player.getLocation(), event);
                    player.sendMessage(response("The event " + args[1] + "was set at " +
                            player.getLocation().getBlockX() +  "," +
                            player.getLocation().getBlockY() +  "," +
                            player.getLocation().getBlockZ() , false));
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
        try
        {
            parseEventClass(event).setSpawnLocation(location);
        }
        catch (NullPointerException nullPointerException)
        {
            log.logError("NPE on saving " + event.toString() + "!");
            nullPointerException.printStackTrace();
        }
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
    private void setEventWorldSpawn(Player player)
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

    /**
     * Retrieves a new instance of the class equivelent of the enum
     * @param events The class specifier to retrieve
     * @return A new instance of the class needed.
     */
    private Event parseEventClass(Events events)
    {
        switch (events)
        {
            case BATTLESHIP:
                return new Battleship();
            case CTF:
                return new CTF();
            case DODGE:
                return new Dodge();
            case PARKOUR:
                return new Parkour();
            case PAYLOAD:
                return new Payload();
            case SPLEEF:
                return new Spleef();
            case UNKNOWN:
                return null;
                default:
                    return null;
        }

    }
}
