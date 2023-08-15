package net.vadamdev.viapi.tools.commands.smart.options;

/**
 * @author VadamDev
 * @since 14/08/2023
 */
public class OptionData implements IOption {
    private final OptionType optionType;
    private final String name;

    private boolean required, autoComplete;

    public OptionData(OptionType optionType, String name) {
        this.optionType = optionType;
        this.name = name;
    }

    public OptionData setRequired(boolean required) {
        this.required = required;
        return this;
    }

    public OptionData setAutoComplete(boolean autoComplete) {
        this.autoComplete = autoComplete;
        return this;
    }
}
