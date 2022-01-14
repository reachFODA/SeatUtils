package me.reach.utils.listener.player;

import dev.slickcollections.kiwizin.utils.enums.EnumSound;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerMove(PlayerMoveEvent evt) {
        Player player = evt.getPlayer();
        if (evt.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SLIME_BLOCK) {
            player.setVelocity(player.getLocation().getDirection().multiply(2).setY(2));

            EnumSound.FIREWORK_LAUNCH.play(player, 0.5F, 1.0F);
        }
    }
}
