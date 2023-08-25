package net.vadamdev.viapi.tools.commands.smart.options;

import java.util.List;

/**
 * @author VadamDev
 * @since 14/08/2023
 */
public enum OptionType {
    WORD,
    PARAGRAPH(1, false),
    INTEGER,
    BOOLEAN,
    PLAYER;

    private final int priority;
    private final boolean canBeOptional;

    OptionType() {
        this(0, true);
    }

    OptionType(int priority) {
        this(priority, true);
    }

    OptionType(int priority, boolean canBeOptional) {
        this.priority = priority;
        this.canBeOptional = canBeOptional;
    }

    public static void sortOptions(List<OptionData> options) {

    }
}
