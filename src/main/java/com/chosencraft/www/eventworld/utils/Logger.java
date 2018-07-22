package com.chosencraft.www.eventworld.utils;

import com.chosencraft.www.eventworld.EventWorldMain;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class Logger
{
    private static java.util.logging.Logger logger = java.util.logging.Logger.getGlobal();

    private String logMessage =
            ChatColor.YELLOW + "[" +
                    ChatColor.AQUA + "EventWorld" +
                    ChatColor.YELLOW + "]" +
                    ChatColor.RED +  " [%s]" +
                    ChatColor.AQUA  + " %s" +
                    ChatColor.RESET;

    private Logger()
    {
        logger.setLevel(Level.ALL);
    }

    private static Logger instance = new Logger();

    public static Logger getInstance()
    {
        return instance;
    }


    public void logWarning(String... message)
    {
        for (String line : message)
        {
            logger.log(Level.WARNING, String.format(logMessage, "WARNING", line));
        }
    }

    public void logError(String... message)
    {
        for (String line : message)
        {
            logger.log(Level.SEVERE, String.format(logMessage, "ERROR", line));
        }
    }

    public void logInfo(String... message)
    {
        for (String line : message)
        {
            logger.log(Level.INFO, String.format(logMessage, "INFO", line));
        }
    }
}
