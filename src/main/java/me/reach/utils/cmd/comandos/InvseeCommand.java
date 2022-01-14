package me.reach.utils.cmd.comandos;

import me.reach.utils.cmd.Commands;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

public class InvseeCommand extends Commands
{
    public InvseeCommand() {
        super("invsee", new String[0]);
    }

    @Override
    public void perform(final CommandSender sender, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return;
        }
        final Player player = (Player)sender;
        if (!player.hasPermission("utils.cmd.invsee")) {
            player.sendMessage("§cVocê não tem permissão para executar este comando.");
            return;
        }
        if (args.length == 0) {
            player.sendMessage("§cUtilize /invsee [jogador]");
            return;
        }
        final Player target = Bukkit.getPlayerExact(args[0]);
        if (target == player) {
            player.sendMessage("§cVocê não pode inspecionar o seu próprio inventário");
            return;
        }
        if (target == null) {
            player.sendMessage("§cJogador offline.");
            return;
        }
        player.openInventory((Inventory)target.getInventory());
    }
}
