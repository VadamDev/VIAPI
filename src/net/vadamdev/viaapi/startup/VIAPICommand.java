package net.vadamdev.viaapi.startup;

import net.vadamdev.viaapi.VIAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VIAPICommand extends Command {
    public VIAPICommand() {
        super("viapi");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length == 0) {
                sendHelpMessage(player);
                return true;
            }

            if (!player.hasPermission("viapi.admin")) {
                player.sendMessage("§cError : You don't have the permission to perform this command !");
                return true;
            }

            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("help")) {
                    sender.sendMessage(" §7§l§m-------------------------------\n§r" +
                            "   §6§lVIAPI §f§l- §eCommands List\n" +
                            "   §7Authors: §f§nVadamDev§r §7and §f§nJava_Implements§r\n \n" +

                            "   §6• §7§m/viapi debug (key) : Trigger debug§r\n \n" +

                            "   §6• §7/viapi (listDepends or depends) : List all plugins that use VIAPI\n" +
                            " §7§l§m-------------------------------");
                }else if(args[0].equalsIgnoreCase("listDepends") || args[0].equalsIgnoreCase("depends")) {
                    StringBuilder stringBuilder = new StringBuilder();

                    VIAPI.get().getDependsMap().forEach((pluginName, apiVersion) -> stringBuilder.append("§a" + pluginName + " §7(" + apiVersion.name().replace("_", ".") + ")§f, "));

                    player.sendMessage("Depends (" +  VIAPI.get().getDependsMap().size() + "): " + stringBuilder);
                }
            }else if(args.length == 2) {
                if(args[0].equalsIgnoreCase("debug")) {
                    if(!player.getName().equals("VadamDev") || !player.getName().equals("Java_Implements")) {
                        player.sendMessage("§6VIAPI §f» §cOnly VIAPI developers can use this command !");
                        return true;
                    }

                    //TODO: Debug Commands
                }
            }
        }

        return true;
    }

    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(" §7§l§m-------------------------------\n§r" +
                "   §6§lVIAPI §f§l- §eInformations\n" +
                "   §7Authors: §f§nVadamDev§r §7and §f§nJava_Implements§r\n \n" +

                "   §6∎ §eVIAPI is a huge toolkit for plugin developers\n" +
                "   §6➥ §eVIAPI is required for (almost) all VadamDev plugins\n \n" +

                "   §6∎ §eSee /viapi help for a command list\n" +
                " §7§l§m-------------------------------");
    }
}
