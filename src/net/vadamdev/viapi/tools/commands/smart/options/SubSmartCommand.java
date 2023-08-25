package net.vadamdev.viapi.tools.commands.smart.options;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author VadamDev
 * @since 14/08/2023
 */
public class SubSmartCommand {
    private final String name;
    private final List<OptionData> options;

    public SubSmartCommand(String name) {
        this.name = name;
        this.options = new ArrayList<>();

        OptionType.sortOptions(options);
    }

    public SubSmartCommand addOptions(OptionData... options) {
        Collections.addAll(this.options, options);
        return this;
    }

    public String getName() {
        return name;
    }
}
