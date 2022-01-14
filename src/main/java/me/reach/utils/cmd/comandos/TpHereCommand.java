package me.reach.utils.cmd.comandos;

import me.reach.utils.cmd.Commands;
import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import dev.slickcollections.kiwizin.player.role.*;

public class TpHereCommand extends Commands
{
    public TpHereCommand() {
        super("tphere", new String[0]);
    }

    @Override
    public void perform(final CommandSender sender, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return;
        }
        final Player player = (Player)sender;
        if (!player.hasPermission("utils.cmd.tphere")) {
            player.sendMessage("§cVocê não tem permissao para executar este comando.");
            return;
        }
        if (args.length == 0) {
            player.sendMessage("§cUtilize /tphere [jogador]");
            return;
        }
        if (args.length != 1) {
            return;
        }
        final Player target = Bukkit.getPlayerExact(args[0]);
        if (target == player) {
            player.sendMessage("§cVocê não pode se teleportar para si mesmo.");
            return;
        }
        if (target == null) {
            player.sendMessage("§cUsuario não encontrado.");
            return;
        }
        target.teleport((Entity)player);
        player.sendMessage("§aVocê teleportou o jogador " + Role.getColored(target.getName()) + "§a até você!");
    }
}

