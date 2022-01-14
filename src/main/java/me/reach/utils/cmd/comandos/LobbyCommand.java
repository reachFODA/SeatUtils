package me.reach.utils.cmd.comandos;

import dev.slickcollections.kiwizin.Core;
import dev.slickcollections.kiwizin.game.FakeGame;
import dev.slickcollections.kiwizin.game.Game;
import dev.slickcollections.kiwizin.game.GameTeam;
import dev.slickcollections.kiwizin.player.Profile;
import me.reach.utils.cmd.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LobbyCommand extends Commands {

    public LobbyCommand() {
        super("lobby", new String[] { "l" });
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return;
        }
        Player player = (Player) sender;
        Profile profile = Profile.getProfile(player.getName());
        Game<? extends GameTeam> game = profile.getGame();
        if (game != null && !(game instanceof FakeGame)) {
            player.sendMessage("§aConectando...");
            game.leave(profile, null);
            return;
        }
        Core.sendServer(profile, "lobby");
    }
}

