package me.reach.utils.objects;

import dev.slickcollections.kiwizin.cash.CashException;
import dev.slickcollections.kiwizin.cash.CashManager;
import dev.slickcollections.kiwizin.nms.NMS;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import dev.slickcollections.kiwizin.utils.StringUtils;
import dev.slickcollections.kiwizin.utils.enums.EnumSound;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import java.util.*;

@SuppressWarnings("all")
public class Upgrade {

    private static final List<Upgrade> UPGRADES = new ArrayList<>();
    public static Map<String, Upgrade> QUEUE_VIPS = new HashMap<>();

    protected String name;
    protected String icon;
    protected String cmd;
    protected String rolePermission;
    protected String permission;
    protected long cash;

    public Upgrade(String name, String permission, long cash, String icon, String cmd, String rolePermission) {
        this.name = name;
        this.icon = icon;
        this.permission = permission;
        this.cmd = cmd;
        this.cash = cash;
        this.rolePermission = rolePermission;

        UPGRADES.add(this);
    }

    public static void setupUpgrades() {
        new Upgrade("§6MVP", "role.vip", 5000, "35:1 : 1", "lp user {player} parent set mvp", "role.mvp");
        new Upgrade("§bMVP§6+", "role.mvp", 10000, "35:3 : 1", "lp user {player} parent set mvpplus", "role.mvpplus");
    }

    public static List<Upgrade> listUpgrades() {
        return new ArrayList<>(UPGRADES);
    }

    public boolean canBuy(Player player) {
        return this.permission.isEmpty() || player.hasPermission(this.permission);
    }

    public String getPermission() {
        return this.permission;
    }

    public String getCommand() {
        return this.cmd;
    }

    public String getName() {
        return this.name;
    }

    public long getCash() {
        return this.cash;
    }

    public boolean has(Player player) {
        return player.hasPermission(this.rolePermission);
    }

    public ItemStack getIcon(Profile profile) {
        long cash = profile.getStats("kCoreProfile", "cash");
        boolean has = this.has(profile.getPlayer());
        boolean canBuy = this.canBuy(profile.getPlayer());

        String color = has ? "§e" : (CashManager.CASH && cash >= this.cash) && canBuy ? "§6" : "§c";

        String desc = "§7Faça um belo upgrade no seu\n&7VIP para ter vantagens melhores!" +
                (has && canBuy(profile.getPlayer()) ?
                        "\n \n§fCash: §b" + StringUtils.formatNumber(this.cash) + "\n \n§eVocê já possui um cargo melhor." : (!has && canBuy(profile.getPlayer()) &&
                        CashManager.CASH && cash >= this.getCash()) ? "\n \n§fCash: §b" + StringUtils.formatNumber(this.cash) +
                        "\n \n§eClique para comprar!" : "\n \n§fCash: §b" + StringUtils.formatNumber(this.cash) + "\n \n§cVocê não possui saldo suficiente.");
        return BukkitUtils.deserializeItemStack(this.icon + " : nome>" + color + StringUtils.formatColors(this.name) + " : desc>" + desc);
    }

    public void onBuy(Profile profile) {
        Player player = profile.getPlayer();

        if (QUEUE_VIPS.containsKey(player.getName()) && QUEUE_VIPS.get(player.getName()).equals(this)) {
            try {
                CashManager.addCash(player.getName(), this.cash);
            } catch (CashException ignored) {}
            EnumSound.VILLAGER_NO.play(player, 1.0f, 1.0f);
            player.sendMessage("§cVocê já tem uma compra em andamento!");
            return;
        }
        player.sendMessage("§aCompra realizada com sucesso, relogue para fazer efeito.");
        QUEUE_VIPS.put(player.getName(), this);
    }

    public void dispatchPack(Player player) {
        QUEUE_VIPS.remove(player.getName());
        TextComponent component = new TextComponent("");
        EnumSound.LEVEL_UP.play(player, 1.0F, 1.0F);
        for (BaseComponent baseComponent : TextComponent.fromLegacyText("§a§lYAY! §aSua compra foi aprovada!\n \n§7Agora você possui o VIP " + StringUtils.formatColors(this.name) + "\n \n§7Caso você precise de\n§7ajuda entre em\n§7contato, através de\n§7nosso suporte\n§7clicando ")) {
            component.addExtra(baseComponent);
        }

        TextComponent urlDiscord = new TextComponent("§0§laqui§7!");
        urlDiscord.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://wwww.discord.redefastly.net"));
        component.addExtra(urlDiscord);

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.cmd.replace("{player}", player.getName()));
        player.sendMessage("\n §aAgora você é " + StringUtils.formatColors(this.getName()) + "§a!\n §7Caso seu grupo ainda não esteja atualizado, basta relogar!\n \n");
        Bukkit.getOnlinePlayers().forEach(toSend -> NMS.sendTitle(toSend, StringUtils.getFirstColor(this.name) + player.getName(), "§ftornou-se " + StringUtils.formatColors(this.name)));

        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();
        meta.setAuthor("DecDlc");
        meta.setTitle("Upgrade de VIP");
        book.setItemMeta(meta);
        book = BukkitUtils.setNBTList(book, "pages", Collections.singletonList(ComponentSerializer.toString(component)));

        BukkitUtils.openBook(player, book);
    }
}
