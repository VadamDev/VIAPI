package net.vadamdev.viaapi.tools.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    /**
     * @author Implements & VadamDev
     * @since 03.11.2020
     */

    public static String color(String str){
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static List<String> color(List<String> str){
        List<String> nstr = new ArrayList<>();
        for (String s : str) nstr.add(ChatColor.translateAlternateColorCodes('&', s));
        return nstr;
    }

    public static Location str2loc(String str){
        String str2loc[] = str.split("\\:");

        Location loc = new Location(Bukkit.getWorld(str2loc[0]),0,0,0);

        loc.setX(Double.parseDouble(str2loc[1]));
        loc.setY(Double.parseDouble(str2loc[2]));
        loc.setZ(Double.parseDouble(str2loc[3]));

        return loc;
    }

    public static String loc2str(Location loc) {
        return loc.getWorld().getName()+":"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ();
    }

    public static Location str2locYAP(String str){
        String str2loc[] = str.split("\\:");

        Location loc = new Location(Bukkit.getWorld(str2loc[0]),0,0,0);

        loc.setX(Double.parseDouble(str2loc[1]));
        loc.setY(Double.parseDouble(str2loc[2]));
        loc.setZ(Double.parseDouble(str2loc[3]));
        loc.setYaw(Float.parseFloat(str2loc[4]));
        loc.setPitch(Float.parseFloat(str2loc[5]));

        return loc;
    }

    public static String loc2strYAP(Location loc) {
        return loc.getWorld().getName()+":"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ()+":"+loc.getYaw()+":"+loc.getPitch();
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
        List<Entity> entities = new ArrayList<>();

        for (Entity entity : location.getWorld().getEntities()) {
            if (entity.getLocation().distanceSquared(location) <= radius * radius) {
                entities.add(entity);
            }
        }
        return entities;
    }

    public static String[] getSkinFromName(String name) {
        try {
            URL url0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStream in;
            InputStreamReader reader_0 = new InputStreamReader(url0.openStream());
            String uuid = new JsonParser().parse(reader_0).getAsJsonObject().get("id").getAsString();

            URL url1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader reader_1 = new InputStreamReader(url1.openStream());
            JsonObject textureProperty = new JsonParser().parse(reader_1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();

            String value = textureProperty.get("value").getAsString();
            String signature = textureProperty.get("signature").getAsString();

            return new String[] {value, signature};
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
