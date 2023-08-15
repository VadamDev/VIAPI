package net.vadamdev.viapi.internal;

import net.vadamdev.viapi.APIVersion;
import net.vadamdev.viapi.VIAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * @author VadamDev
 */
public class VIAPICommand extends Command {
    public VIAPICommand() {
        super("viapi");
        setPermission("viapi.admin");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!testPermission(sender))
            return true;

        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("help")) {
                    sender.sendMessage("§7§l§m-------------------------------\n§r" +
                            "   §6§lVIAPI §f§l- §eCommands List\n" +
                            "   §6Authors: §e§nVadamDev§r §6and §e§nJava_Implements§r\n \n" +

                            "   §6• §7/viapi (depends / listDepends) : List all plugins that currently uses VIAPI\n" +
                            "   §6• §7/viapi credits for some credits\n" +
                            " §7§l§m-------------------------------");

                    return true;
                }else if(args[0].equalsIgnoreCase("credits")) {
                    sender.sendMessage("§7§l§m-------------------------------\n§r" +
                            "   §6§lVIAPI §f§l- §eCredits\n" +
                            "   §6Authors: §e§nVadamDev§r §6and §e§nJava_Implements§r\n \n" +

                            "   §6• §eThanks to §6DarkBlade12 §efor making §6ParticleEffect§r\n" +
                            "   §6• §eThanks to §6MinusKube §efor making §6SmartInvs§r\n" +
                            "   §6• §eThanks to §6WesJD §efor making §6AnvilGUI§r\n" +
                            "   §6• §eThanks to §6Jaxon Brown §efor making §6GuardianBeamAPI§r\n \n" +

                            "   §6• §eA huge thanks to §6JetBrains §efor their free license\n \n" +

                            "   §6• §eSee /viapi help for a command list\n" +
                            " §7§l§m-------------------------------");

                    return true;
                }else if(args[0].equalsIgnoreCase("listDepends") || args[0].equalsIgnoreCase("depends")) {
                    final StringBuilder message = new StringBuilder();
                    final Map<String, APIVersion> dependsMap = VIAPI.get().getDependsMap();

                    dependsMap.forEach((pluginName, apiVersion) -> message.append("§a" + pluginName + " §7(" + apiVersion.name().replace("_", ".") + ")§f, "));

                    player.sendMessage("Depends (" +  dependsMap.size() + "): " + message);

                    return true;
                }
            }

            sendHelpMessage(sender);
            return true;
        }

        return false;
    }

    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(" §7§l§m-------------------------------\n§r" +
                "   §6§lVIAPI §f§l- §eInformations\n" +
                "   §6Authors: §e§nVadamDev§r §6and §e§nJava_Implements§r\n \n" +

                "   §6∎ §eVIAPI is a huge toolkit for plugin developers\n" +
                "   §6➥ §eVIAPI is required for (almost) all VadamDev's plugins\n \n" +

                "   §6∎ §eSee /viapi help for a command list\n" +
                " §7§l§m-------------------------------");
    }
}
