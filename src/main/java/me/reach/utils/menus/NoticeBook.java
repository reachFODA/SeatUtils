package me.reach.utils.menus;

import dev.slickcollections.kiwizin.utils.BukkitUtils;
import me.reach.utils.Language;
import me.reach.utils.plugin.PlayerUtils;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NoticeBook {

    public static void open(Player player){
        ItemStack book = BukkitUtils.deserializeItemStack("WRITTEN_BOOK : 1 : autor>SeatDec");

        List<String> bookComponents = new ArrayList<>();
        for(String page : Language.options$notice_book$pages){
            bookComponents.add(ComponentSerializer.toString(PlayerUtils.toTextComponent(page)));
        }

        book = BukkitUtils.setNBTList(book, "pages", bookComponents);
        BukkitUtils.openBook(player, book);
    }
}
