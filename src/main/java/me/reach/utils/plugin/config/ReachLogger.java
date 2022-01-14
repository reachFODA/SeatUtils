package me.reach.utils.plugin.config;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginLogger;

import java.util.logging.Level;
import java.util.logging.LogRecord;

public class ReachLogger extends PluginLogger {

    private String prefix;
    private Plugin plugin;
    private CommandSender sender;

    public ReachLogger(Plugin plugin) {
        super(plugin);
        this.plugin = plugin;
        this.prefix = "[" + plugin.getDescription().getName() + "] ";
        this.sender = ProxyServer.getInstance().getConsole();
    }

    public ReachLogger(ReachLogger parent, String prefix) {
        super(parent.plugin);
        this.plugin = parent.plugin;
        this.prefix = parent.prefix + prefix;
        this.sender = ProxyServer.getInstance().getConsole();
    }

    public void run(Level level, String method, Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception ex) {
            this.log(level, method.replace("${n}", plugin.getDescription().getName()).replace("${v}", plugin.getDescription().getVersion()), ex);
        }
    }

    @Override
    public void log(LogRecord logRecord) {
        LLevel level = LLevel.fromName(logRecord.getLevel().getName());
        if (level == null) {
            return;
        }

        String message = logRecord.getMessage();
        if (message.equals("Default system encoding may have misread config.yml from plugin jar")) {
            return;
        }
        StringBuilder result = new StringBuilder(this.prefix + message);
        if (logRecord.getThrown() != null) {
            result.append("\n").append(logRecord.getThrown().getLocalizedMessage());
            for (StackTraceElement ste : logRecord.getThrown().getStackTrace()) {
                result.append("\n").append(ste.toString());
            }
        }

        this.sender.sendMessage(TextComponent.fromLegacyText(level.format(result.toString())));
    }

    public ReachLogger getModule(String module) {
        return new ReachLogger(this, module + ": ");
    }

    private enum LLevel {
        INFO("§a"),
        WARNING("§e"),
        SEVERE("§c");

        private String color;

        LLevel(String color) {
            this.color = color;
        }

        public String format(String message) {
            return this.color + message;
        }

        public static LLevel fromName(String name) {
            for (LLevel level : values()) {
                if (level.name().equalsIgnoreCase(name)) {
                    return level;
                }
            }

            return null;
        }
    }
}
