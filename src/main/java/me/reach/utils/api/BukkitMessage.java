package me.reach.utils.api;

import me.reach.utils.Main;
import me.reach.utils.plugin.PlayerUtils;
import org.bukkit.event.*;
import org.bukkit.plugin.messaging.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import org.bukkit.command.*;
import dev.slickcollections.kiwizin.utils.*;
import net.md_5.bungee.api.chat.*;
import dev.slickcollections.kiwizin.player.*;
import dev.slickcollections.kiwizin.player.enums.*;
import java.util.*;
import com.google.common.io.*;
import dev.slickcollections.kiwizin.utils.enums.*;
import dev.slickcollections.kiwizin.nms.*;

public class BukkitMessage implements Listener, PluginMessageListener {
    
    private static String registeredChannel;

    public static void registerChannel(final String channel) {
        BukkitMessage.registeredChannel = channel;
        Bukkit.getPluginManager().registerEvents((Listener)new BukkitMessage(), (Plugin)Main.getInstance());
        Bukkit.getMessenger().registerOutgoingPluginChannel((Plugin)Main.getInstance(), channel);
        Bukkit.getMessenger().registerIncomingPluginChannel((Plugin)Main.getInstance(), channel, (PluginMessageListener)new BukkitMessage());
    }

    public static void sendPluginMessage(final Player player, final String subChannel) {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(subChannel);
        player.sendPluginMessage((Plugin)Main.getInstance(), BukkitMessage.registeredChannel, out.toByteArray());
    }

    public static void sendPluginMessage(final Player player, final String subChannel, final List<String> utfs) {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(subChannel);
        for (final String utf : utfs) {
            out.writeUTF(utf);
        }
        player.sendPluginMessage((Plugin) Main.getInstance(), BukkitMessage.registeredChannel, out.toByteArray());
    }

    public void onPluginMessageReceived(final String channel, final Player receiver, final byte[] msg) {
        if (channel.equals(BukkitMessage.registeredChannel)) {
            final ByteArrayDataInput in = ByteStreams.newDataInput(msg);
            final String utf;
            final String subChannel = utf = in.readUTF();
            switch (utf) {
                case "console": {
                    Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), in.readUTF());
                    break;
                }
                case "playSound": {
                    final String name = in.readUTF();
                    final String pitch = in.readUTF();
                    final String volume = in.readUTF();
                    playSound(receiver, name, pitch, volume);
                    break;
                }
                case "sendTitle": {
                    final String header = in.readUTF();
                    final String bottom = in.readUTF();
                    sendTitle(receiver, header, bottom);
                    break;
                }
                case "sendActionBar": {
                    final String actionbar = in.readUTF();
                    sendActionBar(receiver, actionbar);
                    break;
                }
            }
        }
    }

    public static void playSound(final Player player, final String name, final String str1, final String str2) {
        try {
            final EnumSound sound = EnumSound.valueOf(name.toUpperCase());
            final double volume = Double.valueOf(str2);
            final double speed = Double.valueOf(str1);
            sound.play(player, (float)volume, (float)speed);
        }
        catch (Exception ex) {}
    }

    public static void sendTitle(final Player player, final String header, final String bottom) {
        NMS.sendTitle(player, header, bottom);
    }

    public static void sendNetworkTitle(final Player player, final String header, final String bottom) {
        sendPluginMessage(player, "networkTitle", Arrays.asList(header, bottom));
    }

    public static void sendActionBar(final Player player, final String message) {
        NMS.sendActionBar(player, message);
    }

    public static void executeBungeeConsoleCommand(final Player player, final String command) {
        sendPluginMessage(player, "console", Arrays.asList(command));
    }
}

