package net.vadamdev.viaapi.tools.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * @author VadamDev
 * @since 28/03/2022
 */
public abstract class PermissionCommand extends Command {
    protected PermissionCommand(String name) {
        super(name);
        setPermission(getGlobalPermission());
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!sender.hasPermission(getGlobalPermission())) {
            sender.sendMessage(ChatColor.RED + "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.");
            return false;
        }

        return executePermissionCommand(sender, label, args);
    }

    public abstract boolean executePermissionCommand(CommandSender sender, String label, String[] args);
    public abstract String getGlobalPermission();
}
