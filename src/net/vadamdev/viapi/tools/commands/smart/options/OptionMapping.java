package net.vadamdev.viapi.tools.commands.smart.options;

import org.bukkit.entity.Player;

/**
 * @author VadamDev
 * @since 16/08/2023
 */
public class OptionMapping {
    private final OptionType type;
    private final String name;

    private final Object data;

    public OptionMapping(OptionType type, String name, Object data) {
        this.type = type;
        this.name = name;

        this.data = data;
    }

    public String getAsString() {
        if(!type.equals(OptionType.WORD) && !type.equals(OptionType.PARAGRAPH))
            throw new IllegalStateException("Cannot convert option of type " + type + " to String");

        return (String) data;
    }

    public int getAsInt() {
        if(!type.equals(OptionType.INTEGER))
            throw new IllegalStateException("Cannot convert option of type " + type + " to Integer");

        return (int) data;
    }

    public boolean getAsBoolean() {
        if(!type.equals(OptionType.BOOLEAN))
            throw new IllegalStateException("Cannot convert option of type " + type + " to Boolean");

        return (boolean) data;
    }

    public Player getAsPlayer() {
        if(!type.equals(OptionType.PLAYER))
            throw new IllegalStateException("Cannot convert option of type " + type + " to Player");

        return (Player) data;
    }

    public OptionType getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
