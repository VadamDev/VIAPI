package net.vadamdev.viaapi.tools.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Implements & VadamDev
 * @since 30.12.2020
 */
public class Utils {
    public static String color(String str){
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static List<String> color(List<String> str){
        List<String> nstr = new ArrayList<>();
        for (String s : str) nstr.add(ChatColor.translateAlternateColorCodes('&', s));
        return nstr;
    }

    public static Location str2loc(String str){
        String[] str2loc = str.split("\\:");
        return new Location(Bukkit.getWorld(str2loc[0]), Double.parseDouble(str2loc[1]), Double.parseDouble(str2loc[2]), Double.parseDouble(str2loc[3]));
    }

    public static String loc2str(Location loc) {
        return loc.getWorld().getName() + ":" + loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ();
    }

    public static Location str2locYAP(String str){
        String[] str2loc = str.split("\\:");
        return new Location(Bukkit.getWorld(str2loc[0]),Double.parseDouble(str2loc[1]),Double.parseDouble(str2loc[2]),Double.parseDouble(str2loc[3]), Float.parseFloat(str2loc[4]), Float.parseFloat(str2loc[5]));
    }

    public static String loc2strYAP(Location loc) {
        return loc.getWorld().getName() + ":" + loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ() + ":" + loc.getYaw() + ":" + loc.getPitch();
    }

    public static void setPersistenceRequired(Entity entity) {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();

        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null) tag = new NBTTagCompound();

        nmsEntity.c(tag);
        tag.setInt("PersistenceRequired", 1);
        nmsEntity.f(tag);
    }

    public static void setNoAI(Entity entity) {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();

        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null) tag = new NBTTagCompound();

        nmsEntity.c(tag);
        tag.setInt("NoAI", 1);
        nmsEntity.f(tag);
    }

    public static void setSilent(Entity entity) {
        net.minecraft.server.v1_8_R3.Entity e = ((CraftEntity) entity).getHandle();
        if(!e.R()) e.b(true);
    }

    public static List<Entity> getEntitiesAroundPoint(Location location, double radius) {
        return location.getWorld().getEntities().stream().filter(entity -> entity.getLocation().distanceSquared(location) <= radius * radius).collect(Collectors.toList());
    }

    public static List<Player> getPlayersAroundPoint(Location location, double radius) {
        List<Player> players = new ArrayList<>();
        Utils.getEntitiesAroundPoint(location, radius).stream().filter(entity -> entity instanceof Player).forEach(player -> players.add((Player) player));
        return players;
    }

    public static String date2str(Date date){
        DateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return f.format(date);
    }

    public static Date str2date(String date) {
        DateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date d = null;
        try {
            d = f.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    public static boolean isInEntityBoundingBox(Location location, Entity entity) {
        AxisAlignedBB bb = ((CraftEntity) entity).getHandle().getBoundingBox();

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        return x >= bb.a && x <= bb.d &&
                y >= bb.b && y <= bb.e &&
                z >= bb.c && z <= bb.f;
    }

    public static String[] getSkinFromName(String name) {
        try {
            String uuid = new JsonParser().parse(new InputStreamReader(new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openStream())).getAsJsonObject().get("id").getAsString();
            JsonObject textureProperty = new JsonParser().parse(new InputStreamReader(new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false").openStream())).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();

            return new String[] {textureProperty.get("value").getAsString(), textureProperty.get("signature").getAsString()};
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}