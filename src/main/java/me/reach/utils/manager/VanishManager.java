package me.reach.utils.manager;

import dev.slickcollections.kiwizin.nms.NMS;
import me.reach.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.ArrayList;
import java.util.List;

public class VanishManager {

    public static List<Player> VANISHED = new ArrayList<>();

    public static boolean isVanished(Player player) {
        return VANISHED.contains(player);
    }

    public static void addInVanish(Player player) {
        VANISHED.add(player);
    }

    public static void removeFromVanish(Player player) {
        VANISHED.remove(player);
    }

    public static class VanishTask extends BukkitRunnable {
        public void run() {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (VanishManager.VANISHED.contains(player)) {
                    NMS.sendActionBar(player, "§aVocê está invisível para os outros jogadores!");
                    Bukkit.getOnlinePlayers().stream().filter((p) ->
                            !p.hasPermission("utils.cmd.vanish")).forEach((p) -> p.hidePlayer(player)
                    );
                }
            }
        }
    }

    public static void setupVanishManager() {
        (new VanishManager.VanishTask()).runTaskTimer(Main.getInstance(), 1L, 1L);
    }
}

