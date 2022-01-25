package net.vadamdev.viaapi.tools.images.map;

import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class MapUtils {
    public static MapView clearMapRenderers(MapView map) {
        for (MapRenderer mapRenderer : map.getRenderers()) map.removeRenderer(mapRenderer);
        return map;
    }
}
