package net.vadamdev.viapi.internal.nametag;

import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.ScoreboardTeam;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Field;

/**
 * @author VadamDev
 * @since 25/08/2023
 */
public class NameTag {
    private final Player player;
    private String prefix, suffix;

    private boolean visible;

    public NameTag(Player player, String prefix, String suffix) {
        this.player = player;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public void updateTag(String prefix, String suffix) {
        hide();

        this.prefix = prefix;
        this.suffix = suffix;

        show();
    }

    public void show() {
        Bukkit.getOnlinePlayers().stream()
                .filter(p -> !p.equals(player))
                .forEach(this::show);

        visible = true;
    }

    public void hide() {
        final PacketPlayOutScoreboardTeam packet = prepareHidePacket();
        Bukkit.getOnlinePlayers().stream()
                .filter(p -> !p.equals(player))
                .forEach(viewer -> ((CraftPlayer) viewer).getHandle().playerConnection.sendPacket(completeHidePacket(packet, viewer)));

        visible = false;
    }

    protected void show(Player viewer) {
        ScoreboardTeam team = new ScoreboardTeam(new Scoreboard(), "VTAG_" + viewer.getEntityId() + "-" + player.getEntityId());
        team.setCanSeeFriendlyInvisibles(false);
        team.setAllowFriendlyFire(true);
        team.setPrefix(prefix);
        team.setSuffix(suffix);

        team.getPlayerNameSet().add(player.getName());

        ((CraftPlayer) viewer).getHandle().playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(team, 0));
    }

    protected void hide(Player viewer) {
        ((CraftPlayer) viewer).getHandle().playerConnection.sendPacket(completeHidePacket(prepareHidePacket(), viewer));
    }

    public boolean shouldShowNametag() {
        if(player.hasPotionEffect(PotionEffectType.INVISIBILITY))
            return false;

        return !player.getGameMode().equals(GameMode.SPECTATOR);
    }

    public boolean isVisible() {
        return visible;
    }

    private PacketPlayOutScoreboardTeam completeHidePacket(PacketPlayOutScoreboardTeam packet, Player viewer) {
        try {
            Field a = packet.getClass().getDeclaredField("a");
            a.setAccessible(true);
            a.set(packet, "VTAG_" + viewer.getEntityId() + "-" + player.getEntityId());

            return packet;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private PacketPlayOutScoreboardTeam prepareHidePacket() {
        try {
            final PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();

            Field h = packet.getClass().getDeclaredField("h");
            h.setAccessible(true);
            h.set(packet, 1);

            return packet;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
