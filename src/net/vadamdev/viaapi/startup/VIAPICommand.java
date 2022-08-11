package net.vadamdev.viaapi.startup;

import net.vadamdev.viaapi.VIAPI;
import net.vadamdev.viaapi.tools.entitystructure.impl.RangeEntityStructure;
import net.vadamdev.viaapi.tools.entitystructure.impl.WrappedEntityStructure;
import net.vadamdev.viaapi.tools.math.Cuboid;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author VadamDev
 */
public class VIAPICommand extends Command {
    public VIAPICommand() {
        super("viapi");
    }

    private Location firstloc, secondLoc;

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("viapi.admin")) {
                player.sendMessage("§cError : You don't have the permission to perform this command !");
                return true;
            }
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("d1")) {
                    firstloc = player.getLocation();
                    return true;
                }else if(args[0].equalsIgnoreCase("d2")) {
                    secondLoc = player.getLocation();
                    return true;
                }else if(args[0].equalsIgnoreCase("finish")) {
                    Cuboid cuboid = new Cuboid(firstloc, secondLoc);
                    WrappedEntityStructure entityStructure = new WrappedEntityStructure(firstloc, player.getLocation(), cuboid.getBlocks());

                    RangeEntityStructure rangeEntityStructure = new RangeEntityStructure(entityStructure, 16, 20);
                    rangeEntityStructure.spawn();

                    VIAPI.getScheduler().runTaskTimerAsynchronously(VIAPI.get(), r -> {
                        rangeEntityStructure.updateLocalRotationAroundAxisZ(0.05);
                        rangeEntityStructure.updateLocationAndRotationForViewers();
                    }, 20, 2);

                    return true;
                }

                if(args[0].equalsIgnoreCase("help")) {
                    sender.sendMessage(" §7§l§m-------------------------------\n§r" +
                            "   §6§lVIAPI §f§l- §eCommands List\n" +
                            "   §6Authors: §e§nVadamDev§r §6and §e§nJava_Implements§r\n \n" +

                            "   §6• §7/viapi (listDepends or depends) : List all plugins that use VIAPI\n" +
                            "   §6• §7/viapi integrations : List all present integrations of VIAPI\n" +
                            "   §6• §7/viapi credits for some credits\n" +
                            " §7§l§m-------------------------------");
                }else if(args[0].equalsIgnoreCase("credits")) {
                    sender.sendMessage(" §7§l§m-------------------------------\n§r" +
                            "   §6§lVIAPI §f§l- §eCredits\n" +
                            "   §6Authors: §e§nVadamDev§r §6and §e§nJava_Implements§r\n \n" +

                            "   §6• §eThanks to §6DarkBlade12 §efor making §6ParticleEffect§r\n" +
                            "   §6• §eThanks to §6MinusKube §efor making §6SmartInvs§r\n" +
                            "   §6• §eThanks to §6Wesley Smith §efor making §6AnvilGUI§r\n" +
                            "   §6• §eThanks to §6Jaxon Brown §efor making §6GuardianBeamAPI§r\n \n" +

                            "   §6• §eA huge thanks to §6JetBrains §efor their free license\n \n" +

                            "   §6• §eSee /viapi help for a command list\n" +
                            " §7§l§m-------------------------------");
                }else if(args[0].equalsIgnoreCase("listDepends") || args[0].equalsIgnoreCase("depends")) {
                    StringBuilder stringBuilder = new StringBuilder();

                    VIAPI.get().getDependsMap().forEach((pluginName, apiVersion) -> stringBuilder.append("§a" + pluginName + " §7(" + apiVersion.name().replace("_", ".") + ")§f, "));

                    player.sendMessage("Depends (" +  VIAPI.get().getDependsMap().size() + "): " + stringBuilder);
                }else if(args[0].equalsIgnoreCase("integrations")) {
                    StringBuilder stringBuilder = new StringBuilder();

                    VIAPI.get().getIntegrationManager().getIntegrationsMap().forEach((integrationName, integration) -> stringBuilder.append("§a" + integrationName + " §7(" + integration.getVersion() + " - By " + integration.getAuthor() + ")§f, "));

                    player.sendMessage("Integrations (" +  VIAPI.get().getIntegrationManager().getIntegrationsMap().size() + "): " + stringBuilder);
                }else
                    sendHelpMessage(sender);
            }else
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
                "   §6➥ §eVIAPI is required for (almost) all VadamDev plugins\n \n" +

                "   §6∎ §eSee /viapi help for a command list\n" +
                " §7§l§m-------------------------------");
    }
}
