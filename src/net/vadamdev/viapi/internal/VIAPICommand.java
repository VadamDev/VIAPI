package net.vadamdev.viapi.internal;

import net.vadamdev.viapi.APIVersion;
import net.vadamdev.viapi.tools.commands.PermissionCommand;
import org.bukkit.ChatColor;
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

    private int cooldown = 0;
    private int ipCharIndex = 0;
    private String colorTEXT() {
        String ip = "Custom Bossbar";

        if (cooldown > 0) {
            cooldown--;
            return ChatColor.YELLOW + ip;
        }

        StringBuilder formattedIp = new StringBuilder();

        if (ipCharIndex > 0) {
            formattedIp.append(ip, 0, ipCharIndex - 1);
            formattedIp.append(ChatColor.GOLD).append(ip.substring(ipCharIndex - 1, ipCharIndex));
        }else
            formattedIp.append(ip, 0, ipCharIndex);

        formattedIp.append(ChatColor.RED).append(ip.charAt(ipCharIndex));

        if (ipCharIndex + 1 < ip.length()) {
            formattedIp.append(ChatColor.GOLD).append(ip.charAt(ipCharIndex + 1));

            if (ipCharIndex + 2 < ip.length())
                formattedIp.append(ChatColor.YELLOW).append(ip.substring(ipCharIndex + 2));

            ipCharIndex++;
        }else {
            ipCharIndex = 0;
            cooldown = 15;
        }

        return ChatColor.YELLOW + formattedIp.toString();
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
                dependsMap.forEach((pluginName, apiVersion) -> message.append("§a" + pluginName + " §7(" + apiVersion.name().replace("_", ".") + ")§f, "));

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
