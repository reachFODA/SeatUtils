package me.reach.utils.listener.player;

import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.utils.enums.EnumSound;
import me.reach.utils.Language;
import me.reach.utils.Main;
import me.reach.utils.menus.NoticeBook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static me.reach.utils.objects.Upgrade.QUEUE_VIPS;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt) {
        Player player = evt.getPlayer();
        Profile profile = Profile.getProfile(player.getName());
        if (QUEUE_VIPS.containsKey(player.getName())) {
            QUEUE_VIPS.get(player.getName()).dispatchPack(player);
        }
        if (Language.options$notice_book$enabled) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {

                NoticeBook.open(player);
                EnumSound.ORB_PICKUP.play(player, 0.5F, 1.0F);
            }, Language.options$notice_book$delay * 20);
        }
    }
    }
