

package me.TechsCode.EnderPermissions.base.source;

import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.util.Collection;
import com.google.common.base.Preconditions;
import java.util.HashSet;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import java.util.Set;
import java.util.List;
import me.TechsCode.EnderPermissions.base.TechPlugin;

public class Maven
{
    private static boolean initialized;
    private static TechPlugin plugin;
    private static final List<String> repositories;
    private static JarClassLoader classLoader;
    private static LocalRepository localRepository;
    private static Set<Dependency> loadedDependencies;
    private static Set<Dependency> failedDependencies;
    
    public static void initialize(final SpigotTechPlugin plugin) {
        if (Maven.initialized) {
            return;
        }
        Maven.plugin = plugin;
        Maven.classLoader = new JarClassLoader(plugin);
        Maven.localRepository = new LocalRepository(plugin);
        Maven.loadedDependencies = new HashSet<Dependency>();
        Maven.failedDependencies = new HashSet<Dependency>();
        Maven.initialized = true;
    }
    
    public static void addRepository(final String s) {
        Preconditions.checkArgument(Maven.initialized, (Object)"Call #initialize before using Maven");
        if (!Maven.repositories.contains(s)) {
            Maven.repositories.add(s);
        }
    }
    
    public static boolean loadDependency(final String s, final String s2, final String s3) {
        return loadDependency(new Dependency(s, s2, s3));
    }
    
    public static boolean loadDependency(final Dependency dependency) {
        Preconditions.checkArgument(Maven.initialized, (Object)"Call #initialize before using Maven");
        if (Maven.failedDependencies.contains(dependency)) {
            return false;
        }
        if (Maven.loadedDependencies.contains(dependency)) {
            return true;
        }
        File file = Maven.localRepository.getIfPresent(dependency);
        if (file == null) {
            Maven.localRepository.load(dependency, Maven.repositories);
            file = Maven.localRepository.getIfPresent(dependency);
        }
        if (file != null) {
            Maven.classLoader.load(file);
            Maven.loadedDependencies.add(dependency);
            Maven.plugin.log("Loaded dependency " + dependency.getGroupId() + ":" + dependency.getArtifactId() + ":" + dependency.getVersion());
        }
        else {
            Maven.failedDependencies.add(dependency);
            Maven.plugin.log("Could not load " + dependency.getGroupId() + " / " + dependency.getArtifactId() + " on " + dependency.getVersion());
        }
        return file != null;
    }
    
    public static boolean isLoaded(final Dependency dependency) {
        return Maven.loadedDependencies.contains(dependency);
    }
    
    static {
        Maven.initialized = false;
        repositories = new ArrayList<String>(Collections.singletonList("https://repo1.maven.org/maven2"));
    }
}
