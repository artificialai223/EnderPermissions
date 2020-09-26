

package me.TechsCode.EnderPermissions.dependencies.nbt.utils;

import java.nio.charset.StandardCharsets;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import com.google.gson.JsonParser;
import org.bukkit.plugin.RegisteredServiceProvider;
import java.lang.reflect.Method;
import org.bukkit.entity.Player;
import java.util.Collection;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.TimerTask;
import java.util.Timer;
import java.util.Iterator;
import org.bukkit.plugin.ServicePriority;
import java.io.IOException;
import java.util.UUID;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class ApiMetricsLite
{
    private static final String PLUGINNAME = "ItemNBTAPI";
    public static final int B_STATS_VERSION = 1;
    public static final int NBT_BSTATS_VERSION = 1;
    private static final String URL = "https://bStats.org/submitData/bukkit";
    private boolean enabled;
    private static boolean logFailedRequests;
    private static boolean logSentData;
    private static boolean logResponseStatusText;
    private static String serverUUID;
    private Plugin plugin;
    
    public ApiMetricsLite() {
        final Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
        for (int length = plugins.length, i = 0; i < length; ++i) {
            this.plugin = plugins[i];
            if (this.plugin != null) {
                break;
            }
        }
        if (this.plugin == null) {
            return;
        }
        final File file = new File(new File(this.plugin.getDataFolder().getParentFile(), "bStats"), "config.yml");
        final YamlConfiguration loadConfiguration = YamlConfiguration.loadConfiguration(file);
        if (!loadConfiguration.isSet("serverUuid")) {
            loadConfiguration.addDefault("enabled", (Object)true);
            loadConfiguration.addDefault("serverUuid", (Object)UUID.randomUUID().toString());
            loadConfiguration.addDefault("logFailedRequests", (Object)false);
            loadConfiguration.addDefault("logSentData", (Object)false);
            loadConfiguration.addDefault("logResponseStatusText", (Object)false);
            loadConfiguration.options().header("bStats collects some data for plugin authors like how many servers are using their plugins.\nTo honor their work, you should not disable it.\nThis has nearly no effect on the server performance!\nCheck out https://bStats.org/ to learn more :)").copyDefaults(true);
            try {
                loadConfiguration.save(file);
            }
            catch (IOException ex) {}
        }
        ApiMetricsLite.serverUUID = loadConfiguration.getString("serverUuid");
        ApiMetricsLite.logFailedRequests = loadConfiguration.getBoolean("logFailedRequests", false);
        this.enabled = loadConfiguration.getBoolean("enabled", true);
        ApiMetricsLite.logSentData = loadConfiguration.getBoolean("logSentData", false);
        ApiMetricsLite.logResponseStatusText = loadConfiguration.getBoolean("logResponseStatusText", false);
        if (this.enabled) {
            boolean b = false;
            for (final Class clazz : Bukkit.getServicesManager().getKnownServices()) {
                try {
                    clazz.getField("NBT_BSTATS_VERSION");
                    return;
                }
                catch (NoSuchFieldException ex2) {
                    try {
                        clazz.getField("B_STATS_VERSION");
                        b = true;
                    }
                    catch (NoSuchFieldException ex3) {}
                }
                break;
            }
            final boolean b2 = b;
            if (Bukkit.isPrimaryThread()) {
                Bukkit.getServicesManager().register((Class)ApiMetricsLite.class, (Object)this, this.plugin, ServicePriority.Normal);
                if (!b2) {
                    MinecraftVersion.logger.info("[NBTAPI] Using the plugin '" + this.plugin.getName() + "' to create a bStats instance!");
                    this.startSubmitting();
                }
            }
            else {
                final boolean b3;
                Bukkit.getScheduler().runTask(this.plugin, () -> {
                    Bukkit.getServicesManager().register((Class)ApiMetricsLite.class, (Object)this, this.plugin, ServicePriority.Normal);
                    if (!b3) {
                        MinecraftVersion.logger.info("[NBTAPI] Using the plugin '" + this.plugin.getName() + "' to create a bStats instance!");
                        this.startSubmitting();
                    }
                });
            }
        }
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    private void startSubmitting() {
        final Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!ApiMetricsLite.this.plugin.isEnabled()) {
                    timer.cancel();
                    return;
                }
                Bukkit.getScheduler().runTask(ApiMetricsLite.this.plugin, () -> ApiMetricsLite.this.submitData());
            }
        }, 300000L, 1800000L);
    }
    
    public JsonObject getPluginData() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("pluginName", "ItemNBTAPI");
        jsonObject.addProperty("pluginVersion", "2.5.0");
        jsonObject.add("customCharts", (JsonElement)new JsonArray());
        return jsonObject;
    }
    
    private JsonObject getServerData() {
        int size;
        try {
            final Method method = Class.forName("org.bukkit.Server").getMethod("getOnlinePlayers", (Class<?>[])new Class[0]);
            size = (method.getReturnType().equals(Collection.class) ? ((Collection)method.invoke(Bukkit.getServer(), new Object[0])).size() : ((Player[])method.invoke(Bukkit.getServer(), new Object[0])).length);
        }
        catch (Exception ex) {
            size = Bukkit.getOnlinePlayers().size();
        }
        final int onlineMode = Bukkit.getOnlineMode() ? 1 : 0;
        final String version = Bukkit.getVersion();
        final String name = Bukkit.getName();
        final String property = System.getProperty("java.version");
        final String property2 = System.getProperty("os.name");
        final String property3 = System.getProperty("os.arch");
        final String property4 = System.getProperty("os.version");
        final int availableProcessors = Runtime.getRuntime().availableProcessors();
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("serverUUID", ApiMetricsLite.serverUUID);
        jsonObject.addProperty("playerAmount", (Number)size);
        jsonObject.addProperty("onlineMode", (Number)onlineMode);
        jsonObject.addProperty("bukkitVersion", version);
        jsonObject.addProperty("bukkitName", name);
        jsonObject.addProperty("javaVersion", property);
        jsonObject.addProperty("osName", property2);
        jsonObject.addProperty("osArch", property3);
        jsonObject.addProperty("osVersion", property4);
        jsonObject.addProperty("coreCount", (Number)availableProcessors);
        return jsonObject;
    }
    
    private void submitData() {
        final JsonObject serverData = this.getServerData();
        final JsonArray jsonArray = new JsonArray();
        for (final Class clazz : Bukkit.getServicesManager().getKnownServices()) {
            try {
                clazz.getField("B_STATS_VERSION");
                for (final RegisteredServiceProvider registeredServiceProvider : Bukkit.getServicesManager().getRegistrations((Class)clazz)) {
                    try {
                        final Object invoke = registeredServiceProvider.getService().getMethod("getPluginData", (Class[])new Class[0]).invoke(registeredServiceProvider.getProvider(), new Object[0]);
                        if (invoke instanceof JsonObject) {
                            jsonArray.add((JsonElement)invoke);
                        }
                        else {
                            try {
                                final Class<?> forName = Class.forName("org.json.simple.JSONObject");
                                if (!((JsonObject)invoke).getClass().isAssignableFrom(forName)) {
                                    continue;
                                }
                                final Method declaredMethod = forName.getDeclaredMethod("toJSONString", (Class<?>[])new Class[0]);
                                declaredMethod.setAccessible(true);
                                jsonArray.add((JsonElement)new JsonParser().parse((String)declaredMethod.invoke(invoke, new Object[0])).getAsJsonObject());
                            }
                            catch (ClassNotFoundException thrown) {
                                if (!ApiMetricsLite.logFailedRequests) {
                                    continue;
                                }
                                MinecraftVersion.logger.log(Level.WARNING, "[NBTAPI][BSTATS] Encountered exception while posting request!", thrown);
                            }
                        }
                    }
                    catch (NullPointerException ex) {}
                    catch (NoSuchMethodException ex2) {}
                    catch (IllegalAccessException ex3) {}
                    catch (InvocationTargetException ex4) {}
                }
            }
            catch (NoSuchFieldException ex5) {}
        }
        serverData.add("plugins", (JsonElement)jsonArray);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sendData(ApiMetricsLite.this.plugin, serverData);
                }
                catch (Exception thrown) {
                    if (ApiMetricsLite.logFailedRequests) {
                        MinecraftVersion.logger.log(Level.WARNING, "[NBTAPI][BSTATS] Could not submit plugin stats of " + ApiMetricsLite.this.plugin.getName(), thrown);
                    }
                }
            }
        }).start();
    }
    
    private static void sendData(final Plugin plugin, final JsonObject jsonObject) {
        if (jsonObject == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        if (Bukkit.isPrimaryThread()) {
            throw new IllegalAccessException("This method must not be called from the main thread!");
        }
        if (ApiMetricsLite.logSentData) {
            System.out.println("[NBTAPI][BSTATS] Sending data to bStats: " + jsonObject.toString());
        }
        final HttpsURLConnection httpsURLConnection = (HttpsURLConnection)new URL("https://bStats.org/submitData/bukkit").openConnection();
        final byte[] compress = compress(jsonObject.toString());
        httpsURLConnection.setRequestMethod("POST");
        httpsURLConnection.addRequestProperty("Accept", "application/json");
        httpsURLConnection.addRequestProperty("Connection", "close");
        httpsURLConnection.addRequestProperty("Content-Encoding", "gzip");
        httpsURLConnection.addRequestProperty("Content-Length", String.valueOf(compress.length));
        httpsURLConnection.setRequestProperty("Content-Type", "application/json");
        httpsURLConnection.setRequestProperty("User-Agent", "MC-Server/1");
        httpsURLConnection.setDoOutput(true);
        final DataOutputStream dataOutputStream = new DataOutputStream(httpsURLConnection.getOutputStream());
        dataOutputStream.write(compress);
        dataOutputStream.flush();
        dataOutputStream.close();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
        final StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        bufferedReader.close();
        if (ApiMetricsLite.logResponseStatusText) {
            MinecraftVersion.logger.info("[NBTAPI][BSTATS] Sent data to bStats and received response: " + sb.toString());
        }
    }
    
    private static byte[] compress(final String s) {
        if (s == null) {
            return new byte[0];
        }
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final GZIPOutputStream gzipOutputStream = new GZIPOutputStream(out);
        gzipOutputStream.write(s.getBytes(StandardCharsets.UTF_8));
        gzipOutputStream.close();
        return out.toByteArray();
    }
}
