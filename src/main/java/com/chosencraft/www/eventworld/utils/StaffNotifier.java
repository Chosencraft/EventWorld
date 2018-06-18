package com.chosencraft.www.eventworld.utils;

import com.chosencraft.www.eventworld.Permissions;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.logging.Level;

public class StaffNotifier
{

    /**
     * Notifies all Admins online
     *
     * @param logLevel Log Level to notify with
     * @param notificationMessage Message to notify with
     */
    public void notifyAdmins(Level logLevel, String notificationMessage)
    {
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();

        for (Player player : onlinePlayers)
        {
            if (player.hasPermission(Permissions.NOTIFY_ADMIN))
            {
                sendMessage(player, logLevel, notificationMessage);
            }
        }
    }

    /**
     * Notify All Moderators Online
     * @param logLevel  Log Level to notify with
     * @param notificationMessage Message to notify with
     */
    public void notifyModerators(Level logLevel, String notificationMessage)
    {
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();

        for (Player player : onlinePlayers)
        {
            if (player.hasPermission(Permissions.NOTIFY_MODERATOR))
            {
                sendMessage(player, logLevel, notificationMessage);
            }
        }
    }

    /**
     * Notify All notifiable players Online (Likely all in the event world)
     * @param logLevel  Log Level to notify with
     * @param notificationMessage Message to notify with
     */
    public void notifyOthers(Level logLevel, String notificationMessage)
    {
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();

        for (Player player : onlinePlayers)
        {
            if (player.hasPermission(Permissions.NOTIFY))
            {
                sendMessage(player, logLevel, notificationMessage);
            }
        }
    }

    /**
     * Sends notification Message to player
     *
     * @param player Player to send notification to
     * @param logLevel Level of notification to send
     * @param message The message to send
     */
    private void sendMessage(Player player, Level logLevel, String message)
    {
       ChatColor level = getNotificationColor(logLevel);

       String formattedMessage = String.format(
               ChatColor.GOLD + "[" +
                       ChatColor.AQUA + "EventWorld" +
                       ChatColor.GOLD + "] " +
                       level + "[%s] " +
                       ChatColor.AQUA + "%s" +
                       ChatColor.RESET

               ,logLevel.toString(), message);

       player.sendMessage(formattedMessage);
    }

    /**
     * Retrieves chat color equivelent of log level
     *
     * @param logLevel Level to find equivalent color for
     * @return Color equivalent of logLevel
     */
    private ChatColor getNotificationColor(Level logLevel)
    {
        // Can't use a switch case on this since Levels aren't intialized constants
        if (logLevel.equals(Level.WARNING))
        {
            return ChatColor.RED;
        }
        else if (logLevel.equals(Level.INFO))
        {
            return ChatColor.GREEN;
        }
        else if (logLevel.equals(Level.CONFIG))
        {
            return ChatColor.LIGHT_PURPLE;
        }
        else if (logLevel.equals(Level.FINE))
        {
            return ChatColor.BLUE;
        }
        else if (logLevel.equals(Level.FINEST))
        {
            return ChatColor.DARK_BLUE;
        }
        else if (logLevel.equals(Level.SEVERE))
        {
            return ChatColor.DARK_RED;
        }
        else
        {
            return ChatColor.GOLD;
        }
    }
}
