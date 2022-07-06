package net.vadamdev.viaapi.tools.nametag;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.ScoreboardTeam;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.InvocationTargetException;

/**
 * @author VadamDev
 * @since 01/07/2022
 */
public class NameTag {
    private static final ProtocolManager pm = ProtocolLibrary.getProtocolManager();

    private final Player player;
    private final String prefix, suffix;

    private boolean hidden;

    public NameTag(Player player, String prefix, String suffix) {
        this.player = player;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public void showCustomNametag() {
        Bukkit.getOnlinePlayers().parallelStream()
                .filter(p -> p != player)
                .forEach(this::applyNameTag);
    }

    public void hideCustomNametag() {
        PacketContainer packet = pm.createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);
        packet.getIntegers().write(1, 1); //Removing the team

        Bukkit.getOnlinePlayers().parallelStream()
                .filter(p -> p != player)
                .forEach(pls -> {
                    packet.getStrings().write(0, "TAG_" + pls.getEntityId() + "." + player.getEntityId());

                    try {
                        ProtocolLibrary.getProtocolManager().sendServerPacket(pls, packet);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
    }

    public void applyNameTag(Player viewer) {
        ScoreboardTeam team = new ScoreboardTeam(new Scoreboard(), "TAG_" + viewer.getEntityId() + "." + player.getEntityId());
        team.setCanSeeFriendlyInvisibles(false);
        team.setAllowFriendlyFire(true);
        team.setPrefix(prefix);
        team.setSuffix(suffix);

        team.getPlayerNameSet().add(player.getName());

        ((CraftPlayer) viewer).getHandle().playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(team, 0));
    }

    public boolean shouldShowNametag() {
        return !player.getGameMode().equals(GameMode.SPECTATOR) && !player.hasPotionEffect(PotionEffectType.INVISIBILITY);
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
