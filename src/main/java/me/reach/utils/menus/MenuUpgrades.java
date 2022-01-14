package me.reach.utils.menus;

import dev.slickcollections.kiwizin.Core;
import dev.slickcollections.kiwizin.libraries.menu.PagedPlayerMenu;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.utils.StringUtils;
import dev.slickcollections.kiwizin.utils.enums.EnumSound;
import me.reach.utils.objects.Upgrade;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuUpgrades<T extends Upgrade> extends PagedPlayerMenu {

    private Map<ItemStack, Upgrade> cosmetics = new HashMap<>();

    public MenuUpgrades(Profile profile) {
        super(profile.getPlayer(), "Upgrades de VIP", (Upgrade.listUpgrades().size() / 7) + 3);
        this.previousPage = (this.rows * 9) - 9;
        this.nextPage = (this.rows * 9) - 1;
        this.onlySlots(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34);

        List<ItemStack> items = new ArrayList<>();
        List<Upgrade> upgrades = Upgrade.listUpgrades();
        for (Upgrade upgrade : upgrades) {
            ItemStack icon = upgrade.getIcon(profile);
            items.add(icon);
            this.cosmetics.put(icon, upgrade);
        }

        this.setItems(items);
        upgrades.clear();
        items.clear();

        this.register(Core.getInstance());
        this.open();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent evt) {
        if (evt.getInventory().equals(this.getCurrentInventory())) {
            evt.setCancelled(true);

            if (evt.getWhoClicked().equals(this.player)) {
                Profile profile = Profile.getProfile(this.player.getName());
                if (profile == null) {
                    this.player.closeInventory();
                    return;
                }

                if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getCurrentInventory())) {
                    ItemStack item = evt.getCurrentItem();

                    if (item != null && item.getType() != Material.AIR) {
                        if (evt.getSlot() == this.previousPage) {
                            EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                            this.openPrevious();
                        } else if (evt.getSlot() == this.nextPage) {
                            EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                            this.openNext();
                        } else {
                            Upgrade upgrade = this.cosmetics.get(item);
                            if (upgrade != null) {
                                if (!upgrade.has(player) && upgrade.canBuy(player) && profile.getStats("kCoreProfile", "cash") >= upgrade.getCash()) {
                                    EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                                    new MenuBuyUpgrade<>(profile, StringUtils.formatColors(upgrade.getName()), upgrade);
                                    return;
                                }

                                EnumSound.ENDERMAN_TELEPORT.play(player, 1.0F, 1.0F);
                            }
                        }
                    }
                }
            }
        }
    }

    public void cancel() {
        HandlerList.unregisterAll(this);
        this.cosmetics.clear();
        this.cosmetics = null;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent evt) {
        if (evt.getPlayer().equals(this.player)) {
            this.cancel();
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent evt) {
        if (evt.getPlayer().equals(this.player) && evt.getInventory().equals(this.getCurrentInventory())) {
            this.cancel();
        }
    }
}
