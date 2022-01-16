package me.reach.utils.cmd;

import java.util.*;
import me.reach.utils.cmd.comandos.*;
import org.bukkit.*;
import org.bukkit.command.*;

public abstract class Commands extends Command
{
    public Commands(final String name, final String... aliases) {
        super(name);
        this.setAliases((List)Arrays.asList(aliases));
        try {
            final SimpleCommandMap simpleCommandMap = (SimpleCommandMap)Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap", (Class<?>[])new Class[0]).invoke(Bukkit.getServer(), new Object[0]);
            simpleCommandMap.register(this.getName(), "seatutils", (Command)this);
        }
        catch (ReflectiveOperationException ex) {
            Bukkit.getConsoleSender().sendMessage("Cannot register command: " + ex);
        }
    }

    public abstract void perform(final CommandSender p0, final String p1, final String[] p2);

    public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
        this.perform(sender, commandLabel, args);
        return true;
    }

    public static void setupCommands() {
        new SetGroupCommand();
        new FormsCommand();
        new InvseeCommand();
        new VanishCommand();
        new NPCUpgradeCommand();
        new FlyCommand();
        new TpHereCommand();
        new TpCommand();
        new ChatClearCommand();
        new GamemodeCommand();
        new LobbyCommand();
        new PingCommand();
    }
}

