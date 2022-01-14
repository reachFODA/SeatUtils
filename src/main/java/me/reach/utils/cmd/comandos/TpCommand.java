package me.reach.utils.cmd.comandos;

import me.reach.utils.cmd.Commands;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import dev.slickcollections.kiwizin.player.role.*;
import org.bukkit.*;

public class TpCommand extends Commands
{
    public TpCommand() {
        super("teleport", new String[] { "tp" });
    }

    @Override
    public void perform(final CommandSender sender, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem utilizar este comando.");
            return;
        }
        final Player player = (Player)sender;
        if (!player.hasPermission("utils.cmd.tp")) {
            player.sendMessage("§cVocê não tem permissão para executar este comando.");
            return;
        }
        if (args.length == 0 || args.length == 2 || args.length > 3) {
            player.sendMessage("§cUtilize /" + label + " [jogador] ou /" + label + " [x] [y] [z]");
            return;
        }
        if (args.length == 1) {
            final Player target = Bukkit.getPlayerExact(args[0]);
            if (target == player) {
                player.sendMessage("§cVocê não pode dar tp em si mesmo.");
                return;
            }
            if (target == null) {
                player.sendMessage("§cUsuario não encontrado.");
                return;
            }
            player.teleport((Entity)target);
            player.sendMessage("§aVocê teleportou para " + Role.getColored(target.getName()) + "§a!");
        }
        else {
            if (!this.isInteger(args[0]) || !this.isInteger(args[1]) || !this.isInteger(args[2])) {
                player.sendMessage("§eUtilize numeros inteiros!");
                return;
            }
            final double x = Double.parseDouble(args[0]);
            final double y = Double.parseDouble(args[1]);
            final double z = Double.parseDouble(args[2]);
            player.teleport(new Location(player.getWorld(), x, y, z));
            player.sendMessage("§eVocê teleportou-se até §fx:" + args[0] + " y:" + args[1] + " z:" + args[2] + "§a!");
        }
    }

    public Boolean isInteger(final String str) {
        try {
            Integer.parseInt(str);
        }
        catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
}

