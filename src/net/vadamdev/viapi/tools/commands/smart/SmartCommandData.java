package net.vadamdev.viapi.tools.commands.smart;

import net.vadamdev.viapi.tools.commands.smart.options.IOption;
import net.vadamdev.viapi.tools.commands.smart.options.OptionData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author VadamDev
 * @since 14/08/2023
 */
public class SmartCommandData {
    private final List<IOption> options;

    private final boolean hasSubCommands;

    public SmartCommandData(List<IOption> options, boolean hasSubCommands) {
        this.options = options;
        this.hasSubCommands = hasSubCommands;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private final List<IOption> options;

        private boolean allowOptions, allowSubcommands;

        private Builder() {
            this.options = new ArrayList<>();

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

            Collections.addAll(this.options, subCommands);

            return this;
        }

        public Builder addSubcommandGroups(SmartCommandGroup... subcommandGroups) {
            if(!allowSubcommands)
                throw new IllegalArgumentException("You cannot mix options with subcommands");

            allowOptions = false;

            Collections.addAll(this.options, subcommandGroups);

            return this;
        }

        public SmartCommandData build() {
            return new SmartCommandData(options, !allowOptions);
        }
    }
}
