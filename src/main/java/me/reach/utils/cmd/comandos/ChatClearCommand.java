package me.reach.utils.cmd.comandos;

import me.reach.utils.cmd.Commands;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import dev.slickcollections.kiwizin.player.role.*;

public class ChatClearCommand extends Commands
{
    public ChatClearCommand() {
        super("chatclear", new String[] { "clearchat", "cc" });
    }

    @Override
    public void perform(final CommandSender sender, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            if (!player.hasPermission("utils.cmd.chatclear")) {
                player.sendMessage("§cVocê não tem permissão para executar este comando.");
                return;
            }
            for (int i = 0; i < 200; ++i) {
                Bukkit.broadcastMessage("");
            }
            Bukkit.broadcastMessage("§aO chat foi limpo por " + Role.getColored(player.getName())+ ".");
        }
        else {
            for (int j = 0; j < 200; ++j) {
                Bukkit.broadcastMessage("");
            }
            Bukkit.broadcastMessage("§aChat foi limpo pelo console.");
        }
    }
}

