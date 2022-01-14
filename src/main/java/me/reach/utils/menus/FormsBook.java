package me.reach.utils.menus;

import dev.slickcollections.kiwizin.utils.BukkitUtils;
import me.reach.utils.Language;
import me.reach.utils.plugin.*;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.*;

public class FormsBook {

    public static void open(Player player){
        ItemStack book = BukkitUtils.deserializeItemStack("WRITTEN_BOOK : 1 : autor>AlexFODA");

        List<String> bookComponents = new ArrayList<>();
        for(String page : Language.commands$forms$pages){
            bookComponents.add(ComponentSerializer.toString(PlayerUtils.toTextComponent(page)));
        }

        book = BukkitUtils.setNBTList(book, "pages", bookComponents);
        BukkitUtils.openBook(player, book);
    }
}

