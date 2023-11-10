package net.vadamdev.viapi.tools.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.vadamdev.viapi.tools.enums.EnumAxis;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Implements & VadamDev
 * @since 30/12/2020
 */
public class Utils {
    public static String color(String str){
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static List<String> color(List<String> list) {
        return list.stream()
                .map(Utils::color)
                .collect(Collectors.toList());
    }

    public static Location str2loc(String str){
        final String[] split = str.split(":");
        return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]));
    }

    public static String loc2str(Location loc) {
        return loc.getWorld().getName() + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ();
    }

    public static Location str2locYAP(String str){
        final String[] split = str.split(":");
        return new Location(Bukkit.getWorld(split[0]),Double.parseDouble(split[1]),Double.parseDouble(split[2]),Double.parseDouble(split[3]), Float.parseFloat(split[4]), Float.parseFloat(split[5]));
    }

    public static String loc2strYAP(Location loc) {
        return loc.getWorld().getName() + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ() + ":" + loc.getYaw() + ":" + loc.getPitch();
    }

    public static void setPersistenceRequired(Entity entity) {
        final net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        final NBTTagCompound tag = nmsEntity.getNBTTag() == null ? nmsEntity.getNBTTag() : new NBTTagCompound();

        nmsEntity.c(tag);
        tag.setInt("PersistenceRequired", 1);
        nmsEntity.f(tag);
    }

    public static void setNoAI(Entity entity) {
        final net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        final NBTTagCompound tag = nmsEntity.getNBTTag() == null ? nmsEntity.getNBTTag() : new NBTTagCompound();

        nmsEntity.c(tag);
        tag.setInt("NoAI", 1);
        nmsEntity.f(tag);
    }

    public static void setSilent(Entity entity) {
        setSilent(entity, true);
    }

    public static void setSilent(Entity entity, boolean flag) {
        ((CraftEntity) entity).getHandle().b(flag);
    }

    public static List<Entity> getEntitiesAroundPoint(Location location, double radius) {
        return location.getWorld().getEntities().parallelStream()
                .filter(entity -> entity.getWorld().equals(location.getWorld()))
                .filter(entity -> entity.getLocation().distanceSquared(location) <= radius * radius)
                .collect(Collectors.toList());
    }

    public static List<Player> getPlayersAroundPoint(Location location, double radius) {
        return Utils.getEntitiesAroundPoint(location, radius).stream()
                .map(Player.class::cast)
                .collect(Collectors.toList());
    }

    public static String date2str(Date date){
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date);
    }

    public static Date str2date(String date) {
        Date d = null;

        try {
            d = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d;
    }

    public static void shuffleArray(Object[] array, Random random) {
        final int arrayLength = array.length;

        for (int i = 0; i < arrayLength; i++) {
            final int randomIndex = random.nextInt(arrayLength);
            final Object temp = array[randomIndex];

            array[randomIndex] = array[i];
            array[i] = temp;
        }
    }

    public static List<Block> getBlocksWithPattern(Location origin, String[][] pattern, EnumAxis structureAxis, int depth, boolean negativeExpansion) {
        ArrayUtils.reverse(pattern);

        final List<Block> blocks = new ArrayList<>();
        for(int i = 0; i < pattern.length; i++) {
            for(int j = 0; j < pattern[i].length; j++) {
                if(pattern[i][j].equals(" "))
                    continue;

                switch(structureAxis) {
                    case X:
                        for(int k = 0; k < depth; k++)
                            blocks.add(origin.clone().add(negativeExpansion ? -k : k, i, j).getBlock());

                        break;
                    case Y:
                        for(int k = 0; k < depth; k++)
                            blocks.add(origin.clone().add(i, negativeExpansion ? -k : k, j).getBlock());

                        break;
                    case Z:
                        for(int k = 0; k < depth; k++)
                            blocks.add(origin.clone().add(-j, i, negativeExpansion ? -k : k).getBlock());

                        break;
                    default:
                        break;
                }
            }
        }

        return blocks;
    }

    /**
     * @param location
     * @param entity
     * @return True if the provided Location is in the Entity bounding box
     */
    public static boolean isInEntityBoundingBox(Location location, Entity entity) {
        final AxisAlignedBB bb = ((CraftEntity) entity).getHandle().getBoundingBox();

        final double x = location.getX();
        final double y = location.getY();
        final double z = location.getZ();

        return x >= bb.a && x <= bb.d &&
                y >= bb.b && y <= bb.e &&
                z >= bb.c && z <= bb.f;
    }

    public static BufferedImage getImage(String path) throws IOException {
        if(path.startsWith("http://") || path.startsWith("https://"))
            return ImageIO.read(new URL(path));

        return ImageIO.read(new File(path));
    }

    public static String[] getSkinFromName(String name) {
        try {
            final String uuid = new JsonParser().parse(new InputStreamReader(new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openStream())).getAsJsonObject().get("id").getAsString();
            final JsonObject textureProperty = new JsonParser().parse(new InputStreamReader(new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false").openStream())).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();

            return new String[] {
                    textureProperty.get("value").getAsString(),
                    textureProperty.get("signature").getAsString()
            };
        }catch (IOException e) {
            e.printStackTrace();
            return new String[0];
        }
    }
}