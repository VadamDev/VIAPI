package net.vadamdev.viaapi.tools.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * @author VadamDev
 * @since 08/07/2022
 */
public class ConsumerCommand extends Command {
    private final Consumer<Player> action;

    public ConsumerCommand(String name, Consumer<Player> action) {
        super(name);
        this.action = action;
    }

    public ConsumerCommand(String name, Consumer<Player> action, String... aliases) {
        this(name, action);
        setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player) {
            action.accept((Player) sender);
            return true;
        }

        return false;
    }
}
