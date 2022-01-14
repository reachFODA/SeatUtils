package me.reach.utils.cmd.comandos;

import dev.slickcollections.kiwizin.player.Profile;
import me.reach.utils.cmd.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand extends Commands {

    public FlyCommand() {
        super("fly", "voar");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            if (!player.hasPermission("utils.cmd.fly")) {
                player.sendMessage("§cVocê não tem permissão para executar esse comando.");
                return;
            }
            if (Profile.getProfile(player.getName()).playingGame()) {
                player.sendMessage("§cVocê não pode voar em game!");
                return;
            }
            player.setAllowFlight(!player.getAllowFlight());
            player.sendMessage(player.getAllowFlight() ? "§aAgora você pode voar." : "§cAgora você não pode mais voar.");
        }
    }
}

