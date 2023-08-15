package net.vadamdev.viapi.internal;

import net.vadamdev.viapi.tools.commands.smart.SmartCommand;
import net.vadamdev.viapi.tools.commands.smart.SmartCommandData;

public class DebugSmartCommand extends SmartCommand {
    public DebugSmartCommand() {
        super("smartdebug");
    }

    @Override
    public SmartCommandData createSmartCommandData() {
        return SmartCommandData.builder()
                .build();
    }
}
