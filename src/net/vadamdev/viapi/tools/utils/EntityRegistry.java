package net.vadamdev.viapi.tools.utils;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityTypes;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class EntityRegistry {
    private EntityRegistry() {}

    public static void registerCustomEntity(String name, int id, Class<? extends EntityInsentient> customClass) {
        try {
            List<Map<?, ?>> dataMaps = new ArrayList<>();

            for(Field f : EntityTypes.class.getDeclaredFields()) {
                if(f.getType().isAssignableFrom(Map.class)) {
                    f.setAccessible(true);
                    dataMaps.add((Map<?, ?>) f.get(null));
                }
            }

            if(dataMaps.get(2).containsKey(id)) {
                dataMaps.get(0).remove(name);
                dataMaps.get(2).remove(id);
            }

            Method method = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
            method.setAccessible(true);
            method.invoke(null, customClass, name, id);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
