package me.reach.utils.cmd.comandos;

import me.reach.utils.cmd.Commands;
import org.bukkit.command.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;

public class PingCommand extends Commands
{
    public PingCommand() {
        super("ping", new String[0]);
    }

    @Override
    public void perform(final CommandSender sender, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar esse comando.");
            return;
        }
        final Player player = (Player)sender;
        sender.sendMessage("§aSeu ping é de " + (sender instanceof Player ? ((CraftPlayer) sender).getHandle().ping : 0) + " §ams!");
    }
}

