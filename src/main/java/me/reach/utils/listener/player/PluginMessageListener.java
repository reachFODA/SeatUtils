package me.reach.utils.listener.player;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import dev.slickcollections.kiwizin.nms.NMS;
import dev.slickcollections.kiwizin.utils.enums.EnumSound;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PluginMessageListener implements org.bukkit.plugin.messaging.PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player receiver, byte[] data) {
        if (channel.equals("MESSAGE_UTILS")) {
            ByteArrayDataInput in = ByteStreams.newDataInput(data);

            String subChannel = in.readUTF();
            switch (subChannel) {
                case "STAFF_SOUND": {
                    Player player = Bukkit.getPlayerExact(in.readUTF());
                    if (player != null) {
                        if (true) {
                            EnumSound.ORB_PICKUP.play(player, 0.5F, 1.0F);
                        }
                        NMS.sendActionBar(player, "§eHá uma nova mensagem no chat staff!");
                    }
                    break;
                }
                case "PLAY_SOUND": {
                    Player player = Bukkit.getPlayerExact(in.readUTF());
                    if (player != null) {
                        String sound = in.readUTF();
                        float pitch = Float.parseFloat(in.readUTF());
                        float yaw = Float.parseFloat(in.readUTF());
                        EnumSound.valueOf(sound).play(player, pitch, yaw);
                    }
                    break;
                }
            }
        }
    }
}