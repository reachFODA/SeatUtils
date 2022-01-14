package me.reach.utils.cmd.comandos;

import dev.slickcollections.kiwizin.player.role.Role;
import me.reach.utils.cmd.Commands;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand extends Commands {

    public GamemodeCommand() {
        super("gamemode", "gm");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem utilizar este comando.");
            return;
        }

        Player player = (Player)sender;

        if(!player.hasPermission("utils.cmd.gamemode")){
            player.sendMessage("§cVocê não tem permissão para executar este comando.");
            return;
        }

        if(args.length == 0){
            player.sendMessage("§cUtilize /" + label + " [modo] [jogador]");
            return;
        }

        if(args.length == 1){
            player.chat("/gm " + args[0] + " "+player.getName());
            return;
        }

        if(args.length == 2){
            Player target = Bukkit.getPlayerExact(args[1]);

            if(target == null){
                player.sendMessage("§cO jogador " + args[1] + " está offline no momento!");
                return;
            }

            if(args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")){

                if(target == player){
                    player.sendMessage("§aSeu modo de jogo foi alterado para Sobrevivência!");
                } else {
                    target.sendMessage("§aSeu modo de jogo foi alterado para Sobrevivência!");
                    player.sendMessage("§aO modo de jogo de "+ Role.getColored(target.getName()) + "§afoi alterado para Sobrevivência!");
                }
                target.setGameMode(GameMode.SURVIVAL);

            } else if(args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")){

                if(target == player){
                    player.sendMessage("§aSeu modo de jogo foi alterado para Criativo!");
                } else {
                    target.sendMessage("§aSeu modo de jogo foi alterado para Criativo!");
                    player.sendMessage("§aO modo de jogo de " + Role.getColored(target.getName()) + "§afoi alterado para Criativo!");
                }
                target.setGameMode(GameMode.CREATIVE);

            } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")){

                if(target == player){
                    player.sendMessage("§aSeu modo de jogo foi alterado para Aventura!");
                } else {
                    target.sendMessage("§aSeu modo de jogo foi alterado para Aventura!");
                    player.sendMessage("§aO modo de jogo de " + Role.getColored(target.getName()) + "§afoi alterado para Aventura!");
                }
                target.setGameMode(GameMode.ADVENTURE);

            } else if(args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")){

                if(target == player){
                    player.sendMessage("§aSeu modo de jogo foi alterado para Espectador!");
                } else {
                    target.sendMessage("§aSeu modo de jogo foi alterado para Espectador!");
                    player.sendMessage("§aO modo de jogo de "+Role.getColored(target.getName())+"§afoi alterado para Espectador!");
                }
                target.setGameMode(GameMode.SPECTATOR);

            } else {
                player.sendMessage("§aO modo de jogo "+args[0]+" §aé inválido!");
            }

        }
    }
}
