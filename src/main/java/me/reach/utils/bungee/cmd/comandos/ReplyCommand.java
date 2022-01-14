package me.reach.utils.bungee.cmd.comandos;

import dev.slickcollections.kiwizin.player.role.Role;
import dev.slickcollections.kiwizin.utils.StringUtils;
import me.reach.utils.bungee.cmd.Commands;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import static me.reach.utils.bungee.Main.TELL;
import static dev.slickcollections.kiwizin.utils.StringUtils.formatColors;

public class ReplyCommand extends Commands {

    public ReplyCommand() {
        super("r");
    }

    public void perform(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            return;
        }
        if (args.length == 0) {
            sender.sendMessage(TextComponent.fromLegacyText("§cUtilize /r [mensagem]"));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;
        ProxiedPlayer target = TELL.get(player);
        if (target == null || !target.isConnected()) {
            player.sendMessage(TextComponent.fromLegacyText("§cJogador offline."));
            return;
        }
        String join = StringUtils.join(args, " ");
        if (player.hasPermission("utils.tell.color")) {
            join = formatColors(join);
        }

        TELL.put(target, player);
        TELL.put(player, target);
        target.sendMessage(TextComponent.fromLegacyText("§8Mensagem de: " + Role.getPlayerRole(player).getPrefix() + player.getName() + "§8: §6" + join));
        player.sendMessage(TextComponent.fromLegacyText("§8Mensagem para: " + Role.getPlayerRole(target).getPrefix() + target.getName() + "§8: §6" + join));
    }
}

