package me.reach.utils.plugin.config;

import me.reach.utils.bungee.Main;
import me.reach.utils.plugin.FileUtils;
import me.reach.utils.plugin.config.compatibility.BungeeYamlConfiguration;
import me.reach.utils.plugin.config.compatibility.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;

public class ReachConfig {

    private File file;
    private YamlConfiguration config;

    private ReachConfig(String path, String name) {
        this.file = new File(path + "/" + name + ".yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            InputStream in = Main.getInstance().getResourceAsStream(name + ".yml");
            if (in != null) {
                FileUtils.copyFile(in, file);
            } else {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "Unexpected error ocurred creating file " + file.getName() + ": ", e);
                }
            }
        }

        try {
            this.config = new BungeeYamlConfiguration(file);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unexpected error ocurred creating config " + file.getName() + ": ", e);
        }
    }

    public boolean createSection(String path) {
        return this.config.createSection(path);
    }

    public boolean set(String path, Object obj) {
        return this.config.set(path, obj);
    }

    public boolean contains(String path) {
        return this.config.contains(path);
    }

    public Object get(String path) {
        return this.config.get(path);
    }

    public int getInt(String path) {
        return this.config.getInt(path);
    }

    public int getInt(String path, int def) {
        return this.config.getInt(path, def);
    }

    public double getDouble(String path) {
        return this.config.getDouble(path);
    }

    public double getDouble(String path, double def) {
        return this.config.getDouble(path, def);
    }

    public String getString(String path) {
        return this.config.getString(path);
    }

    public String getString(String path, String def) {
        return this.config.getString(path, def);
    }

    public boolean getBoolean(String path) {
        return this.config.getBoolean(path);
    }

    public boolean getBoolean(String path, boolean def) {
        return this.config.getBoolean(path, def);
    }

    public List<String> getStringList(String path) {
        return this.config.getStringList(path);
    }

    public Collection<String> getKeys() {
        return this.config.getKeys();
    }

    public Set<String> getKeys(boolean flag) {
        return this.config.getKeys(flag);
    }

    public boolean reload() {
        return this.config.reload();
    }

    public boolean save() {
        return this.config.save();
    }

    public File getFile() {
        return file;
    }

    public static final ReachLogger LOGGER = ((ReachLogger) Main.getInstance().getLogger()).getModule("CONFIG");
    private static Map<String, ReachConfig> cache = new HashMap<>();

    public static ReachConfig getConfig(String name) {
        return getConfig(name, "plugins/SeatUtils");
    }

    public static ReachConfig getConfig(String name, String path) {
        if (!cache.containsKey(path + "/" + name)) {
            cache.put(path + "/" + name, new ReachConfig(path, name));
        }

        return cache.get(path + "/" + name);
    }
}
