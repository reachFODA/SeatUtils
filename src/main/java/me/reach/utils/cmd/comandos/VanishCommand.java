package me.reach.utils.cmd.comandos;

import dev.slickcollections.kiwizin.player.Profile;
import me.reach.utils.cmd.Commands;
import me.reach.utils.manager.VanishManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand extends Commands {

    public VanishCommand() {
        super("v");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return;
        }
        Player player = (Player)sender;

        if (!player.hasPermission("utils.cmd.vanish")) {
            player.sendMessage("§cVocê não tem permissão para executar este comando.");
            return;
        }

        if (VanishManager.isVanished(player)) {
            VanishManager.removeFromVanish(player);
            if (Profile.getProfile(player.getName()).playingGame()) {
                Profile.listProfiles().stream().filter(Profile::playingGame).forEach((pf) -> {
                    if (!Profile.getProfile(pf.getName()).getGame().isSpectator(player)) {
                        pf.getPlayer().showPlayer(player);
                    }

                });
            } else {
                Profile.listProfiles().stream().filter((pf) -> !pf.playingGame()).forEach((pf) -> pf.getPlayer().showPlayer(player));
            }

            player.sendMessage("§aModo invisível desativado!");
        } else if (!VanishManager.isVanished(player)) {
            VanishManager.addInVanish(player);
            player.sendMessage("§aModo invisível ativado!");
        }
    }
}


