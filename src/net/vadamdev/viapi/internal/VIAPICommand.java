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
            }
        }

        sendHelpMessage(sender);
        return true;
    }

    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(
                "§7§m-------------§r §fInformations §7§m-------------\n§r" +
                "   §7∎ §fAuthors: §7§nVadamDev§r §fand §7§nJava_Implements§r\n \n" +

                "   §7• §fVersion: §7" + pluginDesc.getVersion() + "\n" +
                "   §7• §fGithub: §7§n" + pluginDesc.getWebsite() + "§r\n \n" +

                "   §7∎ §fSee /viapi help for a command list\n" +
                "§7§m-------------------------------------"
        );
    }
}
