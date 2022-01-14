package me.reach.utils.cmd.comandos;

import me.reach.utils.*;
import me.reach.utils.cmd.Commands;
import me.reach.utils.menus.FormsBook;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class FormsCommand extends Commands
{
    public FormsCommand() {
        super("forms", new String[] { "aplicar" });
    }

    @Override
    public void perform(final CommandSender sender, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return;
        }
        final Player player = (Player)sender;
        FormsBook.open(player);
    }
}
