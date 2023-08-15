package net.vadamdev.viapi.tools.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.function.Consumer;

/**
 * @author VadamDev
 * @since 15/08/2023
 */
public final class ConsumerCommand<T extends CommandSender> extends Command {
    private final Consumer<T> action;
    private final Class<T> clazz;

    public ConsumerCommand(Class<T> clazz, String name, Consumer<T> action) {
        super(name);

        this.clazz = clazz;
        this.action = action;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(!testPermission(commandSender) || !(commandSender.getClass().isInstance(clazz)))
            return true;

        action.accept((T) commandSender);

        return true;
    }
}
