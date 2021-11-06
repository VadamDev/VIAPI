package net.vadamdev.viaapi.startup;

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

            //TODO: Debug Commands
        }

        return true;
    }

    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(" §7§l§m-------------------------------\n§r" +
                "   §6§lVIAPI §f§l- §eInformations\n" +
                "   §7Authors: §f§nVadamDev§r §7and §f§nJava_Implements§r\n \n" +

                "   §6∎ §eVIAPI is a huge toolkit for plugin developers\n" +
                "   §6➥ §eVIAPI is required for (almost) all VadamDev plugins\n" +
                " §7§l§m-------------------------------");
    }
}
