package net.vadamdev.viapi.tools.commands.smart;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author VadamDev
 * @since 14/08/2023
 */
public abstract class SmartCommand extends Command {
    private final SmartCommandData smartCommandData;

    public SmartCommand(String name) {
        super(name);

        this.smartCommandData = createSmartCommandData();
    }

    public SmartCommand(String name, String description, String usageMessage, List<String> aliases) {
        super(name, description, usageMessage, aliases);

        this.smartCommandData = createSmartCommandData();
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!testPermission(sender))
            return true;

        //TODO: execute command

        return false;
    }

    public abstract SmartCommandData createSmartCommandData();
}
