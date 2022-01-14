package me.reach.utils.bungee.cmd;

import me.reach.utils.bungee.Main;
import me.reach.utils.bungee.cmd.comandos.*;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;

public abstract class Commands extends Command {

    public Commands(String name, String... aliases) {
        super(name, null, aliases);
        ProxyServer.getInstance().getPluginManager().registerCommand(Main.getInstance(), this);
    }

    public abstract void perform(CommandSender sender, String[] args);

    @Override
    public void execute(CommandSender sender, String[] args) {
        this.perform(sender, args);
    }

    public static void setupCommands(){
        new OnlineCommand();
        new WarningCommand();
        new StaffChatCommand();
        new TellCommand();
        new ReplyCommand();
    }
}