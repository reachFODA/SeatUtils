package me.reach.utils.listener.player;

import dev.slickcollections.kiwizin.libraries.holograms.api.Hologram;
import dev.slickcollections.kiwizin.libraries.npclib.api.event.NPCRightClickEvent;
import dev.slickcollections.kiwizin.libraries.npclib.api.npc.NPC;
import dev.slickcollections.kiwizin.player.Profile;
import me.reach.utils.menus.MenuUpgrades;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerInteractListener implements Listener {

    private static final Map<Block, Hologram> hologramMap = new HashMap<>();

    @EventHandler
    public void onNPCRightClick(NPCRightClickEvent evt) {
        Player player = evt.getPlayer();
        Profile profile = Profile.getProfile(player.getName());

        if (profile != null) {
            NPC npc = evt.getNPC();
            if (npc.data().has("utils-upgrade")) {
                new MenuUpgrades<>(profile);
            }
        }
    }

    @EventHandler
    public void onPluginDisableEvent(PluginDisableEvent evt) {
        if (evt.getPlugin().getName().equalsIgnoreCase("SeatUtils")) {
            hologramMap.values().forEach(Hologram::despawn);
            hologramMap.keySet().forEach(b -> b.setType(Material.AIR));
            hologramMap.clear();
        }
    }
}
