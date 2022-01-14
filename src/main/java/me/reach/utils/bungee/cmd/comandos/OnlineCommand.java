package me.reach.utils.bungee.cmd.comandos;

import dev.slickcollections.kiwizin.utils.StringUtils;
import me.reach.utils.bungee.Settings;
import me.reach.utils.bungee.cmd.Commands;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Map;

public class OnlineCommand extends Commands {

    public OnlineCommand() {
        super("online");
    }

    @Override
    public void perform(CommandSender sender, String[] strings) {
        if (sender.hasPermission("utils.cmd.online")) {
            Map<String, Integer> count = new HashMap<>();
            for (ProxiedPlayer players : BungeeCord.getInstance().getPlayers()) {
                count.merge(players.getServer().getInfo().getName(), 1, Integer::sum);
            }

            String current = null;
            sender.sendMessage(new TextComponent(""));
            if (sender instanceof ProxiedPlayer) {
                String serverName = ((ProxiedPlayer) sender).getServer().getInfo().getName();

                sender.sendMessage(new TextComponent("§eHá §f" + count.get(serverName) + " §ejogador" + (count.get(serverName) == 1 ? "" : "es") + " online no momento em seu servidor atual."));
                current = serverName;

                sender.sendMessage(new TextComponent(""));
            }

            for (ServerInfo server : BungeeCord.getInstance().getServers().values()) {
                String serverName = server.getName();
                if (!Settings.options$online_servers$blacklist.contains(serverName.toLowerCase())) {
                    if (current != null && current.equalsIgnoreCase(serverName)) {
                        continue;
                    }

                    int size = count.get(serverName) == null ? 0 : count.get(serverName);
                    sender.sendMessage(new TextComponent("§eHá §f" + size + " §ejogador" + (size == 1 ? "" : "es") + " online no §f" + StringUtils.capitalise(serverName) + "§e!"));
                }
            }
            int size = BungeeCord.getInstance().getPlayers().size();

            sender.sendMessage(new TextComponent(""));
            sender.sendMessage(new TextComponent("§eUm total de §f" + size + " §ejogador" + (size == 1 ? "" : "es") + " na network!"));
            sender.sendMessage(new TextComponent(""));
        }
    }
}
