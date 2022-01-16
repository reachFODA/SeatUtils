package me.reach.utils.bungee;

import me.reach.utils.api.BungeeMessage;
import me.reach.utils.bungee.cmd.Commands;
import me.reach.utils.bungee.listeners.Listeners;
import me.reach.utils.plugin.config.ReachLogger;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Main extends Plugin {

    public static Main instance;

    public Main() {
        try {
            Field field = Plugin.class.getDeclaredField("logger");
            field.setAccessible(true);
            field.set(this, new ReachLogger(this));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        instance = this;
    }

    public void onEnable() {
        BungeeMessage.registerChannel("SeatUtils");
        Listeners.setupListeners();
        Settings.setupSettings();
        Commands.setupCommands();

        this.getLogger().info("O plugin foi ativado.");
    }

    public void onDisable() {

        this.getLogger().info("O plugin foi desativado.");
    }

    public static final Map<ProxiedPlayer,
            ProxiedPlayer> TELL = new HashMap<>();

    public static Main getInstance() {
        return Main.instance;
    }
}

