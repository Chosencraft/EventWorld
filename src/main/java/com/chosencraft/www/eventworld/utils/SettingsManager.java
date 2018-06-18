package com.chosencraft.www.eventworld.utils;

import com.chosencraft.www.eventworld.EventWorldMain;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Wrapper class for handling extra YAML files via the inbuilt YAML parser
 */
public class SettingsManager
{

    /**
     * Main plugin
     */
    private Plugin plugin = EventWorldMain.getThisPlugin();

    Logger log = Logger.getInstance();

    /**
        Instance of data file
     */
    public static final SettingsManager
        data = new SettingsManager("data");

    /**
        Getter for data file
     */
    public static SettingsManager getData()
    {
        return data;
    }

    private File file;
    private FileConfiguration config;


    /**
     * Private constructor for instantiating the file and directories
     * @param filename Name of the file to be created
     */
    private SettingsManager(String filename)
    {
        if (!(plugin.getDataFolder().exists()))
        {
            plugin.getDataFolder().mkdir();
        }

        file = new File(plugin.getDataFolder(), filename + ".yml");

        if (!(file.exists()))
        {
            try
            {
                // Special management for data.yml file
                if (file.getName().equals("data.yml"))
                {
                    File dataFile = new File("data.yml");
                    YamlConfiguration dataConfig = YamlConfiguration.loadConfiguration(dataFile);
                    try
                    {
                        dataConfig.save(dataFile);
                    }
                    catch (IOException ioException)
                    {
                        log.logWarning("Unable to save data.yml from the jar! ");
                        ioException.printStackTrace();
                    }
                }
                else
                {
                    file.createNewFile();
                }
            }
            catch (IOException ioException)
            {
                log.logError("Could not create new file! :" + file.getName());
                ioException.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }


    /**
     * Retrieves object at the given path
     *
     * @param path Path to retrieve in the config
     * @return Returns the object of the path, or null if non existing
     */
    @SuppressWarnings("unchecked")
    public <T> T get (String path)
    {
        return (T) config.get(path);
    }

    /**
     * Retrieves All key objects in the file
     *
     * @return Returns set of keys
     */
    public Set<String> getKeys()
    {
        return config.getKeys(false);
    }

    /**
     * Sets the value of a path
     * @param path Where to insert the value
     * @param value What to insert in the path
     */
    public void set(String path, Object value)
    {
        config.set(path, value);
        save();
    }

    /**
     * Checks if a path exists
     * @param path The path to check existence of
     * @return True if the path exists, false otherwise
     */
    public boolean contains(String path)
    {
        return config.contains(path);
    }

    /**
     * Creates a new section of the config to look through (Think keys)
     *
     * @param path path of the section to create
     * @return The newly created configuration, or null if it failed
     */
    public ConfigurationSection createSection(String path)
    {
        ConfigurationSection section = config.createSection(path);
        save();
        return section;
    }

    /**
     * Saves the configuration to a file
     */
    private void save()
    {
        try
        {
            config.save(file);
        }
        catch (IOException ioException)
        {
            log.logError("Unable to save the file " + file.getName());
            ioException.printStackTrace();
        }

    }
}
