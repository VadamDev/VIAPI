package net.vadamdev.viapi.tools.commands.smart.options;

/**
 * @author VadamDev
 * @since 14/08/2023
 */
public class OptionData {
    private final OptionType optionType;
    private final String name;

    private String invalidMessage;
    private boolean optional, autoComplete;

    public OptionData(OptionType optionType, String name) {
        this.optionType = optionType;
        this.name = name;

        this.invalidMessage = "Invalid argument ! (Must be " + optionType.name().toLowerCase() + ")";

        this.optional = false;
        this.autoComplete = false;
    }

    public OptionType getOptionType() {
        return optionType;
    }

    public String getName() {
        return name;
    }

    public String getInvalidMessage() {
        return invalidMessage;
    }

    public boolean isOptional() {
        return optional;
    }

    public boolean isAutoComplete() {
        return autoComplete;
    }

    public OptionData setInvalidMessage(String invalidMessage) {
        this.invalidMessage = invalidMessage;
        return this;
    }

    public OptionData setOptional(boolean optional) {
        this.optional = optional;
        return this;
    }

    public OptionData setAutoComplete(boolean autoComplete) {
        this.autoComplete = autoComplete;
        return this;
    }
}
