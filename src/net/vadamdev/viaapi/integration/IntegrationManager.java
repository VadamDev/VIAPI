package net.vadamdev.viaapi.integration;

import net.vadamdev.viaapi.VIAPI;
import net.vadamdev.viaapi.integration.utils.Futures;
import net.vadamdev.viaapi.integration.utils.MethodSignature;
import net.vadamdev.viaapi.tools.utils.FileUtil;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class IntegrationManager {
    private static final Set<MethodSignature> INTEGRATION_METHODS = Arrays.stream(VIAPIIntegration.class.getDeclaredMethods())
            .filter(method -> Modifier.isAbstract(method.getModifiers()))
            .map(method -> new MethodSignature(method.getName(), method.getParameterTypes()))
            .collect(Collectors.toSet());

    private final File integrationsFolder;

    private final Map<String, VIAPIIntegration> integrations = new HashMap<>();

    public IntegrationManager() {
        File file = new File(VIAPI.get().getDataFolder(), "integrations");
        if(!file.exists()) file.mkdirs();

        this.integrationsFolder = file;
    }

    public void loadAllIntegrations() throws ExecutionException, InterruptedException {
        List<Class<? extends VIAPIIntegration>> integrations = findIntegrationsOnDisk().get();

        integrations.forEach(integration -> loadIntegration(integration).ifPresent(VIAPIIntegration::onEnable));

        System.out.println("[VIAPI] Registered " + integrations.size() + " integrations !");
    }

    public void unloadAllIntegrations() {
        integrations.values().forEach(VIAPIIntegration::onDisable);
        integrations.clear();
    }

    /*
       Loader
     */
    private Optional<VIAPIIntegration> loadIntegration(Class<? extends VIAPIIntegration> clazz) {
        try {
            VIAPIIntegration integration = clazz.getDeclaredConstructor().newInstance();
            integrations.put(integration.getName(), integration);
            return Optional.of(integration);
        }catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private CompletableFuture<List<Class<? extends VIAPIIntegration>>> findIntegrationsOnDisk() {
        File[] files = integrationsFolder.listFiles((dir, name) -> name.endsWith(".jar"));

        if(files == null) return CompletableFuture.completedFuture(Collections.emptyList());

        return Arrays.stream(files).map(this::findExpansionInFile).collect(Futures.collector());
    }

    private CompletableFuture<Class<? extends VIAPIIntegration>> findExpansionInFile(File file) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                final Class<? extends VIAPIIntegration> integrationClass = FileUtil.findClass(file, VIAPIIntegration.class);

                if (integrationClass == null) {
                    System.out.println("Failed to load Integration: " + file.getName() + ", as it does not have" + " a class which extends VIAPIIntegration.");
                    return null;
                }

                Set<MethodSignature> expansionMethods = Arrays.stream(integrationClass.getDeclaredMethods()).map(method -> new MethodSignature(method.getName(), method.getParameterTypes())).collect(Collectors.toSet());

                if (!expansionMethods.containsAll(INTEGRATION_METHODS)) {
                    System.out.println("Failed to load Integration: " + file.getName() + ", as it does not have the" + " required methods declared for a VIAPI Integration.");
                    return null;
                }

                return integrationClass;
            }catch (final VerifyError e) {
                System.out.println("Failed to load Integration class " + file.getName() + " (Is a dependency missing?)");
                System.out.println("Cause: " + e.getClass().getSimpleName() + " " + e.getMessage());
                return null;
            }catch (final Exception ex) {
                throw new CompletionException(ex);
            }
        });
    }

    /*
       Getters
     */
    public VIAPIIntegration getIntegration(String name) {
        return integrations.get(name);
    }

    public File getIntegrationsFolder() {
        return integrationsFolder;
    }

    public Map<String, VIAPIIntegration> getIntegrationsMap() {
        return integrations;
    }
}
