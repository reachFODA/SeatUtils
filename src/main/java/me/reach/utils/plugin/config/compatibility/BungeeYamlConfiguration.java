package me.reach.utils.plugin.config.compatibility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import me.reach.utils.bungee.Main;
import net.md_5.bungee.config.Configuration;

public class BungeeYamlConfiguration extends YamlConfiguration {

    private Configuration config;

    public BungeeYamlConfiguration(File file) throws IOException {
        super(file);
        this.config =
                net.md_5.bungee.config.YamlConfiguration.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(new InputStreamReader(new FileInputStream(file), "UTF-8"));
    }

    @Override
    public boolean createSection(String path) {
        return this.set(path, new HashMap<>());
    }

    @Override
    public boolean set(String path, Object obj) {
        this.config.set(path, obj);
        return save();
    }

    @Override
    public boolean contains(String path) {
        return this.config.getKeys().contains(path);
    }

    @Override
    public Object get(String path) {
        return this.config.get(path);
    }

    @Override
    public int getInt(String path) {
        return this.getInt(path, 0);
    }

    @Override
    public int getInt(String path, int def) {
        return this.config.getInt(path, def);
    }

    @Override
    public double getDouble(String path) {
        return this.getDouble(path, 0);
    }

    @Override
    public double getDouble(String path, double def) {
        return this.config.getDouble(path, 0);
    }

    @Override
    public String getString(String path) {
        return this.config.getString(path);
    }

    @Override
    public boolean getBoolean(String path) {
        return this.config.getBoolean(path);
    }

    @Override
    public List<String> getStringList(String path) {
        return this.config.getStringList(path);
    }

    @Override
    public Set<String> getKeys(boolean flag) {
        return new HashSet<>(this.config.getKeys());
    }

    @Override
    public boolean reload() {
        try {
            this.config =
                    net.md_5.bungee.config.YamlConfiguration.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            return true;
        } catch (IOException ex) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Unexpected error ocurred reloading file " + file.getName() + ": ", ex);
            return false;
        }
    }

    @Override
    public boolean save() {
        try {
            net.md_5.bungee.config.YamlConfiguration.getProvider(net.md_5.bungee.config.YamlConfiguration.class).save(config, file);
            return true;
        } catch (IOException ex) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Unexpected error ocurred saving file " + file.getName() + ": ", ex);
            return false;
        }
    }

}
