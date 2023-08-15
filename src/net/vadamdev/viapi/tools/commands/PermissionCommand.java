package net.vadamdev.viapi.tools.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author VadamDev
 * @since 15/08/2023
 */
public abstract class PermissionCommand extends Command {
    public PermissionCommand(String name) {
        super(name);
    }

    public PermissionCommand(String name, String description, String usageMessage, List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!testPermission(sender))
            return true;

        return executePermissionCommand(sender, label, args);
    }

    protected abstract boolean executePermissionCommand(CommandSender sender, String label, String[] args);

    protected boolean testPermission(CommandSender target, String permission) {
        if(testPermissionSilent(target, permission))
            return true;
        else {
            final String permissionMessage = getPermissionMessage();

            if(permissionMessage == null) {
                target.sendMessage(ChatColor.RED + "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.");
            }else if (permissionMessage.length() != 0) {
                String[] split = permissionMessage.replace("<permission>", permission).split("\n");

                for (String line : split)
                    target.sendMessage(line);
            }

            return false;
        }
    }

    protected boolean testPermissionSilent(CommandSender target, String permission) {
        if(permission == null || permission.length() == 0)
            return true;

        final String[] split = permission.split(";");
        for (String p : split) {
            if(target.hasPermission(p))
                return true;
        }

        return false;
    }
}
