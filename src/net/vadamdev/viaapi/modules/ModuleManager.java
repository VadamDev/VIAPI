package net.vadamdev.viaapi.modules;

import net.vadamdev.viaapi.VIAPI;
import net.vadamdev.viaapi.tools.utils.FileUtil;
import org.bukkit.Bukkit;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Deprecated
public class ModuleManager {
    /**
     * @author VadamDev
     * @since 12.07.2021
     *
     * Currently abandonned
     */

    private File modulesFolder;
    private List<IVIAPIModule> loadedModules;

    private Logger logger;

    public ModuleManager() {
        this.modulesFolder = new File(VIAPI.get().getDataFolder(), "modules");
        if(!modulesFolder.exists()) modulesFolder.mkdirs();

        this.loadedModules = new ArrayList<>();
        this.logger = VIAPI.get().getLogger();
    }

    public void onEnable() {
        try {
            try {
                registerAll();
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void onDisable() {

    }

    private void registerAll() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        findModulesOnDisk().forEach(this::register);
    }

    private void register(IVIAPIModule module) {
        module.onEnable();
        loadedModules.add(module);
        Bukkit.broadcastMessage("Loaded " + module.getClass().getSimpleName());
    }

    /*

     */

    private List<IVIAPIModule> findModulesOnDisk() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        List<Class<? extends IVIAPIModule>> classModules = Arrays.stream(modulesFolder.listFiles((dir, name) -> name.endsWith(".jar"))).map(this::findMainModuleFile).collect(Collectors.toList());

        List<IVIAPIModule> modules = new ArrayList<>();
        for (Class<? extends IVIAPIModule> classModule : classModules) {
            modules.add(classModule.getDeclaredConstructor().newInstance());
        }

        return modules;
    }

    private Class<? extends IVIAPIModule> findMainModuleFile(File file) {
        try {
            Class<? extends IVIAPIModule> moduleClass = FileUtil.findClass(file, IVIAPIModule.class);

            if (moduleClass == null) {
                logger.severe("Failed to load Module: " + file.getName() + ", as it does not have a class which extends IVIAPIModule.");
                return null;
            }

            return moduleClass;

        }catch(VerifyError ex) {
            logger.severe("Failed to load Module class: " + file.getName());
            logger.severe("Cause: " + ex.getClass().getSimpleName() + " " + ex.getMessage());
            return null;
        }catch(Exception ex) {
            throw new CompletionException(ex);
        }
    }
}
