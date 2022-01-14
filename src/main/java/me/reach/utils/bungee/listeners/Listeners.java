package me.reach.utils.bungee.listeners;

import me.reach.utils.bungee.Main;
import me.reach.utils.plugin.config.ReachConfig;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.ServerPing.PlayerInfo;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

import static dev.slickcollections.kiwizin.utils.StringUtils.formatColors;

public class Listeners implements Listener {

    public static final ReachConfig config = ReachConfig.getConfig("motd");

    public static void setupListeners() {
        ProxyServer.getInstance().getPluginManager().registerListener((Plugin) Main.getInstance(), (Listener) new Listeners());
    }

    @EventHandler
    public void onProxyPing(ProxyPingEvent evt) {
        ServerPing ping = evt.getResponse();
        ServerPing.Players pingPlayers = ping.getPlayers();
        ServerPing.Protocol pingVersion = ping.getVersion();

        if (config.getBoolean("version_change_enabled")) {
            pingPlayers.setSample(new PlayerInfo[] {new PlayerInfo(formatColors(config.getString("version_name")), UUID.randomUUID())});
            pingVersion.setName(formatColors(config.getString("version")));
            evt.getResponse().setVersion(new ServerPing.Protocol(formatColors(config.getString("version")), 2));
        }
        ping.setDescription(formatColors(config.getString("motd").replace("\\n", "\n")));

        evt.setResponse(ping);
    }
}
