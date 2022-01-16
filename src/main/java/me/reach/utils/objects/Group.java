package me.reach.utils.objects;

import dev.slickcollections.kiwizin.utils.StringUtils;
import me.reach.utils.plugin.config.ReachConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Group {

    private final String name;
    private final boolean bungee;
    private final String command;
    private final String permission;

    private final String title;
    private final String subtitle;

    public Group(String name, boolean bungee, String command, String permission, String title, String subtitle) {
        this.name = name;
        this.bungee = bungee;
        this.command = command;
        this.permission = permission;
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getName() {
        return name;
    }

    public boolean isBungee() {
        return bungee;
    }

    public String getCommand() {
        return command;
    }

    public String getPermission() {
        return permission;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    private static final List<Group> groups = new ArrayList<>();

    public static void setupGroups() {
        ReachConfig config = ReachConfig.getConfig("groups");

        for(String key : config.getKeys()) {
            if (!config.contains(key + ".name")) {
                continue;
            }

            String name = config.getString(key + ".name");
            boolean bungee = config.getBoolean(key + ".bungee", false);
            String execute = config.getString(key + ".execute", "");
            String permission = config.getString(key + ".permission", "");
            String title = config.getString(key + ".title.up", "");
            String subtitle = config.getString(key + ".title.bottom", "");

            groups.add(new Group(name, bungee, execute, permission, StringUtils.formatColors(title), StringUtils.formatColors(subtitle)));
        }
    }

    public static Group fromName(String name){
        Optional<Group> optional = groups.stream().filter(
                group -> group.getName().equalsIgnoreCase(name)
        ).findFirst();

        return optional.orElse(null);
    }

    public static List<Group> listGroups() {
        return groups;
    }
}
