package net.vadamdev.viapi.tools.commands.smart;

import net.vadamdev.viapi.tools.commands.smart.options.OptionData;
import net.vadamdev.viapi.tools.commands.smart.options.OptionType;
import net.vadamdev.viapi.tools.commands.smart.options.SmartCommandGroup;
import net.vadamdev.viapi.tools.commands.smart.options.SubSmartCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author VadamDev
 * @since 14/08/2023
 */
public class SmartCommandData {
    private final List<OptionData> options;
    private final List<SubSmartCommand> subCommands;
    private final List<SmartCommandGroup> subCommandGroups;

    private final boolean hasSubCommands;

    public SmartCommandData(List<OptionData> options, List<SubSmartCommand> subCommands, List<SmartCommandGroup> subCommandGroups, boolean hasSubCommands) {
        this.options = options;
        this.subCommands = subCommands;
        this.subCommandGroups = subCommandGroups;

        this.hasSubCommands = hasSubCommands;
    }

    public SmartCommandInteraction retrieveCommandInteraction(CommandSender sender, String[] args) {
        SmartCommandInteraction interaction = null;

        if(!hasSubCommands) {
            //TODO: sort options
        }else {
            Optional<SubSmartCommand> optionalSubCommand = fetchSubCommand(args);

            if(optionalSubCommand.isPresent()) {
                Bukkit.broadcastMessage("Found sub command: " + optionalSubCommand.get().getName());
            }else
                Bukkit.broadcastMessage("Nothing was found");
        }

        return interaction;
    }

    private Optional<SubSmartCommand> fetchSubCommand(String[] args) {
        if(!hasSubCommands)
            return Optional.empty();

        if(args.length < 1)
            return Optional.empty();

        Optional<SubSmartCommand> optionalCommand = Optional.empty();

        if(!subCommands.isEmpty()) {
            optionalCommand = subCommands.stream()
                    .filter(subCommand -> subCommand.getName().equalsIgnoreCase(args[0]))
                    .findFirst();
        }

        if(args.length >= 2 && !subCommandGroups.isEmpty() && !optionalCommand.isPresent()) {
            Optional<Optional<SubSmartCommand>> a = subCommandGroups.stream()
                    .filter(group -> group.getName().equalsIgnoreCase(args[0]))
                    .filter(group -> group.getSubCommands().stream().anyMatch(subCommand -> subCommand.getName().equalsIgnoreCase(args[1]))) //return a stream with every groups that has sub commands matching arg 1
                    .map(group -> group.getSubCommands().stream().filter(subCommand -> subCommand.getName().equalsIgnoreCase(args[1])).findFirst())
                    .findFirst();

            if(a.isPresent())
                optionalCommand = a.get();
        }

        return optionalCommand;
    }

    /*
       Builder
     */

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private final List<OptionData> options;
        private final List<SubSmartCommand> subCommands;
        private final List<SmartCommandGroup> subCommandGroups;

        private boolean allowOptions, allowSubcommands;

        private Builder() {
            this.options = new ArrayList<>();
            this.subCommands = new ArrayList<>();
            this.subCommandGroups = new ArrayList<>();

            this.allowOptions = true;
            this.allowSubcommands = true;
        }

        public Builder addOptions(OptionData... options) {
            if(!allowOptions)
                throw new IllegalArgumentException("You cannot mix subcommands with options");

            allowSubcommands = false;

            Collections.addAll(this.options, options);

            return this;
        }

        public Builder addSubcommands(SubSmartCommand... subCommands) {
            if(!allowSubcommands)
                throw new IllegalArgumentException("You cannot mix options with subcommands");

            allowOptions = false;

            Collections.addAll(this.subCommands, subCommands);

            return this;
        }

        public Builder addSubcommandGroups(SmartCommandGroup... subcommandGroups) {
            if(!allowSubcommands)
                throw new IllegalArgumentException("You cannot mix options with subcommands");

            allowOptions = false;

            Collections.addAll(this.subCommandGroups, subcommandGroups);

            return this;
        }

        public SmartCommandData build() {
            if(!options.isEmpty())
                OptionType.sortOptions(options);

            return new SmartCommandData(options, subCommands, subCommandGroups, !allowOptions);
        }
    }
}
