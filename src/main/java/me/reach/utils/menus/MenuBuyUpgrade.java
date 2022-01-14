package me.reach.utils.menus;

import dev.slickcollections.kiwizin.cash.CashException;
import dev.slickcollections.kiwizin.cash.CashManager;
import dev.slickcollections.kiwizin.libraries.menu.PlayerMenu;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import dev.slickcollections.kiwizin.utils.StringUtils;
import dev.slickcollections.kiwizin.utils.enums.EnumSound;
import me.reach.utils.Main;
import me.reach.utils.objects.Upgrade;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class MenuBuyUpgrade<T extends Upgrade> extends PlayerMenu {

    private String name;
    private T upgrade;

    public MenuBuyUpgrade(Profile profile, String name, T upgrade) {
        super(profile.getPlayer(), "Confirmar compra", 3);
        this.name = name;
        this.upgrade = upgrade;

        this.setItem(12, BukkitUtils.deserializeItemStack(
                "DIAMOND : 1 : nome>&aConfirmar : desc>&7Comprar \"" + name + "\"\n&7por &b" + StringUtils.formatNumber(upgrade.getCash()) + " Cash&7."));

        this.setItem(14, BukkitUtils.deserializeItemStack("STAINED_GLASS_PANE:14 : 1 : nome>&cCancelar : desc>&7Voltar para Upgrades."));

        this.register(Main.getInstance());
        this.open();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent evt) {
        if (evt.getInventory().equals(this.getInventory())) {
            evt.setCancelled(true);

            if (evt.getWhoClicked().equals(this.player)) {
                Profile profile = Profile.getProfile(this.player.getName());
                if (profile == null) {
                    this.player.closeInventory();
                    return;
                }

                if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getInventory())) {
                    ItemStack item = evt.getCurrentItem();

                    if (item != null && item.getType() != Material.AIR) {
                        if (evt.getSlot() == 12) {
                            if (profile.getStats("kCoreProfile", "cash") < this.upgrade.getCash()) {
                                EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
                                this.player.sendMessage("§cVocê não possui Cash suficiente para completar esta transação.");
                                return;
                            }

                            try {
                                CashManager.removeCash(profile, this.upgrade.getCash());
                                this.upgrade.onBuy(profile);
                            } catch (CashException ignore) {}
                            new MenuUpgrades<>(profile);
                        } else if (evt.getSlot() == 14) {
                            EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
                            new MenuUpgrades<>(profile);
                        }
                    }
                }
            }
        }
    }

    public void cancel() {
        HandlerList.unregisterAll(this);
        this.name = null;
        this.upgrade = null;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent evt) {
        if (evt.getPlayer().equals(this.player)) {
            this.cancel();
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent evt) {
        if (evt.getPlayer().equals(this.player) && evt.getInventory().equals(this.getInventory())) {
            this.cancel();
        }
    }
}
