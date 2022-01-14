package me.reach.utils.listener;

import me.reach.utils.Main;
import me.reach.utils.listener.player.PlayerInteractListener;
import me.reach.utils.listener.player.PlayerJoinListener;
import me.reach.utils.listener.player.PlayerMoveListener;
import org.bukkit.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.*;

public class Listeners
{
    public static void setupListeners() {
        try {
            final PluginManager pm = Bukkit.getPluginManager();
            pm.getClass().getDeclaredMethod("registerEvents", Listener.class, Plugin.class).invoke(pm, new PlayerMoveListener(), Main.getInstance());
            pm.getClass().getDeclaredMethod("registerEvents", Listener.class, Plugin.class).invoke(pm, new PlayerJoinListener(), Main.getInstance());
            pm.getClass().getDeclaredMethod("registerEvents", Listener.class, Plugin.class).invoke(pm, new PlayerInteractListener(), Main.getInstance());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
