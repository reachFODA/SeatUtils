package me.reach.utils.lobby;

import dev.slickcollections.kiwizin.libraries.holograms.HologramLibrary;
import dev.slickcollections.kiwizin.libraries.holograms.api.Hologram;
import dev.slickcollections.kiwizin.libraries.npclib.NPCLibrary;
import dev.slickcollections.kiwizin.libraries.npclib.api.npc.NPC;
import dev.slickcollections.kiwizin.plugin.config.KConfig;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import me.reach.utils.Main;
import me.reach.utils.lobby.trait.NPCSkinTrait;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UpgradeNPC {

    private static final KConfig CONFIG = Main.getInstance().getConfig("npcs");
    private static final List<UpgradeNPC> NPCS = new ArrayList<>();

    private String id;
    private Location location;
    private NPC npc;
    private Hologram hologram;

    public UpgradeNPC(Location location, String id) {
        this.location = location;
        this.id = id;
        if (!this.location.getChunk().isLoaded()) {
            this.location.getChunk().load(true);
        }

        this.spawn();
    }

    public static void setupNPCs() {
        if (!CONFIG.contains("upgrade")) {
            CONFIG.set("upgrade", new ArrayList<>());
        }

        for (String serialized : CONFIG.getStringList("upgrade")) {
            if (serialized.split("; ").length > 6) {
                String id = serialized.split("; ")[6];

                NPCS.add(new UpgradeNPC(BukkitUtils.deserializeLocation(serialized), id));
            }
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> listNPCs().forEach(UpgradeNPC::update), 20, 20);
    }

    public static void add(String id, Location location) {
        NPCS.add(new UpgradeNPC(location, id));
        List<String> list = CONFIG.getStringList("upgrade");
        list.add(BukkitUtils.serializeLocation(location) + "; " + id);
        CONFIG.set("upgrade", list);
    }

    public static void remove(UpgradeNPC npc) {
        NPCS.remove(npc);
        List<String> list = CONFIG.getStringList("upgrade");
        list.remove(BukkitUtils.serializeLocation(npc.getLocation()) + "; " + npc.getId());
        CONFIG.set("upgrade", list);

        npc.destroy();
    }

    public static UpgradeNPC getById(String id) {
        return NPCS.stream().filter(npc -> npc.getId().equals(id)).findFirst().orElse(null);
    }

    public static Collection<UpgradeNPC> listNPCs() {
        return NPCS;
    }

    public void spawn() {
        if (this.npc != null) {
            this.npc.destroy();
            this.npc = null;
        }

        if (this.hologram != null) {
            HologramLibrary.removeHologram(this.hologram);
            this.hologram = null;
        }

        this.hologram = HologramLibrary.createHologram(this.location.clone().add(0, 0.5, 0), Arrays.asList("§e§lCLIQUE DIREITO!", "§bUpar seu VIP!"));

        this.npc = NPCLibrary.createNPC(EntityType.PLAYER, "§8[NPC] ");
        this.npc.data().set("utils-upgrade", true);
        this.npc.data().set(NPC.HIDE_BY_TEAMS_KEY, true);
        this.npc.addTrait(new NPCSkinTrait(this.npc, "ewogICJ0aW1lc3RhbXAiIDogMTYxODM2NDkwNjU5MSwKICAicHJvZmlsZUlkIiA6ICIyNzc1MmQ2ZTUyYmM0MzVjYmNhOWQ5NzY1MjQ2YWNhNSIsCiAgInByb2ZpbGVOYW1lIiA6ICJkZW1pbWVkIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzQ3NmNhMTBlYmQ4M2ZmN2NmZTNkYjQ0NThkNzEyNzE4ZmNiZTE1NjMxNmVmZWNlYmYxNDcxZDkzM2YzYmFjMWIiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==", "GF7IjE0iUW8lTOaBgu7SexFZ0Ic2GARE21UH+TwmHJUynL79lJt1vIhdgmjPKXVGmrDrAs+n3t/jPiFRnWVr6eJWVM6dQkYmtWFCxVscab5dXyaMoWDVhTfmGNTj/tNBHk4BpuF64eNgCGSgfKYxM34Wbkj316FB+JScy5GOfe6ZaeW/mNnIl1pQp8jvE22lGs4IK7J3BVciSzs+IjAjcTSLIlag9WCg5dA22m7CaClHSRN3ZwmQy0JzdGHwLrQ/3UO5uVKoJ8dwVm1i94WLa6a3id3pjH2U4Uhgzcc8xhhICHz/XdxOO4GUPTPumPGB2SEy5mUPelwKfoaOt79isHBMhfQVMQhy/ze/aLo1dcITUxwDa1NRjuDUX9YhTetD6fi3CJ1rbau6FJrK+WFQClH1J4PkUHrXNWEDDo077ZvK6hTezVyGwRzNHF62gCfvoewib+0f2ThY6GuWkKzPrfgbrI2FBVLqgsYgFxoqF+WH7KAvIqwvp5VKGWh/KYPGkLQTRrRSE9KZ3IBvyRIgYK6I8rL1TqBpbjp4EFDSBXdVfsHzN9RgmP9heHHFMlzMeNyfzJj+7LzA6vKMZ9wkxGONlvcF+Fd32nIeSMKlu3kmc+nukXEJSEbLjU6eVOZrYtJt/Ar79CRo+3/MUNIDEZxbQWVi8TzRbkYmwJDc5iU="));

        this.npc.spawn(this.location);
    }

    public void update() {}

    public void destroy() {
        this.id = null;
        this.location = null;

        this.npc.destroy();
        this.npc = null;
        HologramLibrary.removeHologram(this.hologram);
        this.hologram = null;
    }

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return this.location;
    }
}

