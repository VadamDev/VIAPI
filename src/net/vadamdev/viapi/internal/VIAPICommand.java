package net.vadamdev.viapi.internal;

import net.vadamdev.viapi.APIVersion;
import net.vadamdev.viapi.tools.commands.PermissionCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.Map;

/**
 * @author VadamDev
 */
public class VIAPICommand extends PermissionCommand {
    private final PluginDescriptionFile pluginDesc;

    public VIAPICommand() {
        super("viapi");
        setPermission("viapi.command");

        this.pluginDesc = VIAPIPlugin.instance.getDescription();
    }

    @Override
    protected boolean executePermissionCommand(CommandSender sender, String label, String[] args) {
        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(
                        "§7§m--------------§r §fHelp §7§m--------------\n§r" +
                        "   §7• §f/viapi depends : §7List loaded plugins \n                that currently use the VIAPI\n \n" +
                        "   §7• §f/viapi credits : §7Show credits\n" +
                        " §7§m--------------------------------"
                );

                return true;
            }else if(args[0].equalsIgnoreCase("credits")) {
                sender.sendMessage(
                        "§7§m---------------§r §fCredits §7§m---------------\n§r" +
                           "   §7• §fThanks to §7§lMinusKube§r §ffor making §7§lSmartInvs§r\n" +
                           "   §7• §fThanks to §7§lWesJD§r §ffor making §7§lAnvilGUI§r\n" +
                           "   §7• §fThanks to §7§lDarkBlade12§r §ffor making §7§lParticleEffect§r\n" +
                           "   §7• §fThanks to §7§lSamaGames§r §ffor making §7§lSamaGamesAPI§r\n \n" +

                           "   §7• §fA huge thanks to §7§nJetBrains§r §ffor their free license\n \n" +

                           "   §7∎ §fSee /viapi help for a command list\n" +
                           "§7§m-------------------------------------"
                );

                return true;
            }else if(args[0].equalsIgnoreCase("depends")) {
                final StringBuilder message = new StringBuilder();

                final Map<String, APIVersion> dependsMap = VIAPIPlugin.instance.getDependsMap();
                dependsMap.forEach((pluginName, apiVersion) -> message.append((apiVersion.isLatest() ? "§a" : "§e") + pluginName + " §7(" + apiVersion.name().replace("_", ".") + ")§f, "));

                sender.sendMessage("Depends (" +  dependsMap.size() + "): " + message);

                return true;
            }else if(args[0].equalsIgnoreCase("ram") || args[0].equalsIgnoreCase("mem")) {
                final long[] memory = getMemoryData(false);
                sender.sendMessage("§7[§aUsed §7/ §aTotal §7/ §aFree§7] : §a" + memory[0] + " §7MB / §a" + memory[1] + " §7MB / §a" + memory[2] + " §7MB");

                return true;
            }
        }else if(args.length == 2 && (args[0].equalsIgnoreCase("ram") || args[0].equalsIgnoreCase("mem")) && (args[1].equalsIgnoreCase("-gc") || args[1].equalsIgnoreCase("gc"))) {
            final long[] memory = getMemoryData(true);
            sender.sendMessage("§7[§aUsed §7/ §aTotal §7/ §aFree§7] - §cGC §7: §a" + memory[0] + " §7MB / §a" + memory[1] + " §7MB / §a" + memory[2] + " §7MB");

            return true;
        }

        sendHelpMessage(sender);
        return true;
    }

    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(
                "§7§m-------------§r §fInformations §7§m-------------\n§r" +
                "   §7∎ §fAuthors: §7§nVadamDev§r§f, §7§nJava_Implements§r§f, §7§nEstxbxn§r\n \n" +

                "   §7• §fVersion: §7" + pluginDesc.getVersion() + "\n" +
                "   §7• §fGithub: §7§n" + pluginDesc.getWebsite() + "§r\n \n" +

                "   §7∎ §fSee /viapi help for a command list\n" +
                "§7§m-------------------------------------"
        );
    }

    private long[] getMemoryData(boolean gc) {
        final Runtime runtime = Runtime.getRuntime();

        if(gc)
            runtime.gc();

        final long total = runtime.totalMemory() / 1048576;
        final long free = runtime.freeMemory() / 1048576;

        return new long[] { total - free, total, free };
    }
}
