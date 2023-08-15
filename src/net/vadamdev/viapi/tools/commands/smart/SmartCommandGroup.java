package net.vadamdev.viapi.tools.commands.smart;

import net.vadamdev.viapi.tools.commands.smart.options.IOption;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author VadamDev
 * @since 15/08/2023
 */
public class SmartCommandGroup implements IOption {
    private final String name;
    private final List<SubSmartCommand> subCommands;

    public SmartCommandGroup(String name) {
        this.name = name;
        this.subCommands = new ArrayList<>();
    }

    public SmartCommandGroup addSubcommands(SubSmartCommand... subCommands) {
        Collections.addAll(this.subCommands, subCommands);
        return this;
    }

    public String getName() {
        return name;
    }

    public List<SubSmartCommand> getSubCommands() {
        return subCommands;
    }
}
