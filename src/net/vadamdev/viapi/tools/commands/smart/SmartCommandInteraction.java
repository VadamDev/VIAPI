package net.vadamdev.viapi.tools.commands.smart;

import net.vadamdev.viapi.tools.commands.smart.options.OptionMapping;
import net.vadamdev.viapi.tools.commands.smart.options.OptionType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author VadamDev
 * @since 16/08/2023
 */
public class SmartCommandInteraction {
    private final String subCommandName, subCommandGroup;
    private final List<OptionMapping> options;

    public SmartCommandInteraction(String subCommandName, String subCommandGroup, List<OptionMapping> options) {
        this.subCommandName = subCommandName;
        this.subCommandGroup = subCommandGroup;
        this.options = options;
    }

    @Nullable
    public String getSubcommandName() {
        return subCommandName;
    }

    @Nullable
    public String getSubcommandGroup() {
        return subCommandGroup;
    }

    public List<OptionMapping> getOptions() {
        return options;
    }

    @Nonnull
    public List<OptionMapping> getOptionsByName(String name) {
        return getOptions().stream()
                .filter(option -> option.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Nonnull
    public List<OptionMapping> getOptionsByType(OptionType type) {
        return getOptions().stream()
                .filter(option -> option.getType().equals(type))
                .collect(Collectors.toList());
    }

    @Nullable
    public OptionMapping getOption(@Nonnull String name) {
        List<OptionMapping> options = getOptionsByName(name);
        return options.isEmpty() ? null : options.get(0);
    }

    @Nullable
    public <T> T getOption(@Nonnull String name, @Nonnull Function<? super OptionMapping, ? extends T> mapper) {
        return getOption(name, null, mapper);
    }

    public <T> T getOption(@Nonnull String name, @Nullable T fallback, @Nonnull Function<? super OptionMapping, ? extends T> mapper) {
        OptionMapping mapping = getOption(name);
        if (mapping != null)
            return mapper.apply(mapping);

        return fallback;
    }

    public <T> T getOption(@Nonnull String name, @Nullable Supplier<? extends T> fallback, @Nonnull Function<? super OptionMapping, ? extends T> mapper) {
        OptionMapping mapping = getOption(name);
        if (mapping != null)
            return mapper.apply(mapping);

        return fallback == null ? null : fallback.get();
    }
}
