package me.reach.utils.api;

import me.reach.utils.bungee.Main;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.connection.*;
import net.md_5.bungee.*;
import net.md_5.bungee.api.chat.*;
import com.google.common.io.*;
import net.md_5.bungee.event.*;
import java.util.*;

public class BungeeMessage implements Listener
{
    private static String registeredChannel;

    public static void registerChannel(final String channel) {
        BungeeMessage.registeredChannel = channel;
        ProxyServer.getInstance().registerChannel(channel);
        ProxyServer.getInstance().getPluginManager().registerListener((Plugin) Main.getInstance(), (Listener)new BungeeMessage());
    }

    public static void sendPluginMessage(final ProxiedPlayer player, final String subChannel) {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(subChannel);
        player.getServer().sendData(BungeeMessage.registeredChannel, out.toByteArray());
    }

    public static void sendPluginMessage(final ProxiedPlayer player, final String subChannel, final List<String> utfs) {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(subChannel);
        for (final String utf : utfs) {
            out.writeUTF(utf);
        }
        player.getServer().sendData(BungeeMessage.registeredChannel, out.toByteArray());
    }

    @EventHandler
    public void onPluginMessageReceived(final PluginMessageEvent evt) {
        if (evt.getTag().equals(BungeeMessage.registeredChannel) && evt.getSender() instanceof Server && evt.getReceiver() instanceof ProxiedPlayer) {
            final ProxiedPlayer receiver = (ProxiedPlayer)evt.getReceiver();
            final ByteArrayDataInput in = ByteStreams.newDataInput(evt.getData());
            final String utf;
            final String subChannel = utf = in.readUTF();
            switch (utf) {
                case "console": {
                    ProxyServer.getInstance().getPluginManager().dispatchCommand(BungeeCord.getInstance().getConsole(), in.readUTF());
                    break;
                }
                case "networkTitle": {
                    final String header = in.readUTF();
                    final String bottom = in.readUTF();
                    for (final ProxiedPlayer players : BungeeCord.getInstance().getPlayers()) {
                        sendTitle(players, header, bottom);
                    }
                    break;
                }
                case "sendMessage": {
                    final String name = in.readUTF();
                    final String value = in.readUTF();
                    final ProxiedPlayer player = BungeeCord.getInstance().getPlayer(name);
                    player.sendMessage(value);
                    break;
                }
            }
        }
    }

    public static void playSound(final ProxiedPlayer player, final String name, final String pitch, final String volume) {
        sendPluginMessage(player, "playSound", Arrays.asList(name, pitch, volume));
    }

    public static void sendTitle(final ProxiedPlayer player, final String header, final String bottom) {
        sendPluginMessage(player, "sendTitle", Arrays.asList(header, bottom));
    }

    public static void sendActionBar(final ProxiedPlayer player, final String message) {
        sendPluginMessage(player, "sendActionBar", Collections.singletonList(message));
    }

    public static void sendAd(final ProxiedPlayer player, final String type, final String message) {
        sendPluginMessage(player, "sendAd", Arrays.asList(type, message));
    }

    public static void sendTell(final ProxiedPlayer player, final ProxiedPlayer target, final String message) {
        sendPluginMessage(player, "sendTell", Arrays.asList(target.getName(), message));
    }
}

