package com.chosencraft.www.eventworld.commands;

import com.chosencraft.www.eventworld.events.Event;
import com.chosencraft.www.eventworld.events.Events;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Payload extends Event implements CommandExecutor
{
    public Payload ()
    {
        super(Events.PAYLOAD);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        return false;
    }
}
