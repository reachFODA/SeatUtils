package me.reach.utils.cmd.comandos;

import me.reach.utils.api.BukkitMessage;
import me.reach.utils.cmd.Commands;
import me.reach.utils.objects.Group;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SetGroupCommand extends Commands {

    public SetGroupCommand() {
        super("setgroup");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("seatutils.cmd.setgroup")) {
            sender.sendMessage("§cVocê não possui permissão para executar este comando.");
            return;
        }

        if (args.length == 0) {
            sender.sendMessage("§cUtilize /setgroup [jogador] [grupo].");
            return;
        }

        if (args.length == 1) {
            sender.sendMessage("\n §eGrupos disponíveis: \n");
            for (Group group : Group.listGroups()) {
                sender.sendMessage(" ◾ " + group.getName());
            }
            sender.sendMessage("");
            return;
        }

        Group group = Group.fromName(args[1]);
        if (group == null) {
            sender.sendMessage("§cEste grupo não existe!");
            return;
        }

        if (!group.getPermission().isEmpty() && !sender.hasPermission(group.getPermission())) {
            sender.sendMessage("§cVocê não possui permissão para setar este grupo.");
            return;
        }

        if (group.isBungee()) {
            try {
                Player randomPlayer = (Player) Bukkit.getOnlinePlayers().toArray()[0];

                BukkitMessage.sendNetworkTitle(randomPlayer, group.getTitle().replace("{user}", args[0]), group.getSubtitle().replace("{user}", args[0]));
                BukkitMessage.executeBungeeConsoleCommand(randomPlayer, group.getCommand().replace("{user}", args[0]));
            } catch (IndexOutOfBoundsException ignored) {
                return;
            }
        } else {
            for (Player players : Bukkit.getOnlinePlayers()) {
                BukkitMessage.sendTitle(players, group.getTitle().replace("{user}", args[0]), group.getSubtitle().replace("{user}", args[0]));
            }

            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), group.getCommand().replace("{user}", args[0]));
        }

        sender.sendMessage("§aGrupo setado com sucesso!");
    }
}
