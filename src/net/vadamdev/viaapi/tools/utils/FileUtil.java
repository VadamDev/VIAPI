package net.vadamdev.viaapi.tools.utils;

import com.sun.istack.internal.NotNull;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class FileUtil {
    @Nullable
    public static <T> Class<? extends T> findClass(File file, Class<T> clazz) throws IOException, ClassNotFoundException {
        if (!file.exists()) return null;

        URL jar = file.toURI().toURL();
        URLClassLoader loader = new URLClassLoader(new URL[]{jar}, clazz.getClassLoader());
        List<String> matches = new ArrayList<>();
        List<Class<? extends T>> classes = new ArrayList<>();

        try(JarInputStream stream = new JarInputStream(jar.openStream())) {
            JarEntry entry;
            while ((entry = stream.getNextJarEntry()) != null) {
                String name = entry.getName();
                if (name.isEmpty() || !name.endsWith(".class")) continue;
                matches.add(name.substring(0, name.lastIndexOf('.')).replace('/', '.'));
            }

            for (final String match : matches) {
                try {
                    final Class<?> loaded = loader.loadClass(match);
                    if (clazz.isAssignableFrom(loaded)) {
                        classes.add(loaded.asSubclass(clazz));
                    }
                }catch (final NoClassDefFoundError ignored) {}
            }
        }

        if (classes.isEmpty()) {
            loader.close();
            return null;
        }

        return classes.get(0);
    }
}
