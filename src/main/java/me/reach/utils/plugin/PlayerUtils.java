package me.reach.utils.plugin;

import dev.slickcollections.kiwizin.cash.CashException;
import dev.slickcollections.kiwizin.cash.CashManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import dev.slickcollections.kiwizin.player.role.Role;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class PlayerUtils {

    public static void removeCash(Player player, int x){
        try {
            CashManager.removeCash(player.getName(), x);
        } catch (CashException ignored) {}
    }

    public static String getFormattedCash(Player player){
        return new DecimalFormat("#,###").format(CashManager.getCash(player));
    }

    public static String getReplaced(String message, String player) {
        return message
                .replace("{colored}", Role.getColored(player))
                .replace("{display}", Role.getPrefixed(player));
    }

    public static TextComponent toTextComponent(String string){
        TextComponent component = new TextComponent("");
        if(!string.contains("/-/")){
            if(string.contains(": ttp>")){
                String[] rawtext = string.split(" : ");
                TextComponent action = new TextComponent(rawtext[0]);
                if(string.contains(": exe>")){
                    action.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
                    action.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, rawtext[2].replace("exe>", "")));
                    component.addExtra(action);
                } else if(string.contains(": sgt>")){
                    action.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
                    action.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, rawtext[2].replace("sgt>", "")));
                    component.addExtra(action);
                } else if(string.contains(": url>")){
                    action.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
                    action.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, rawtext[2].replace("url>", "")));
                    component.addExtra(action);
                } else {
                    action.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
                    component.addExtra(action);
                }
            } else if(string.contains(": exe>")){
                String[] split = string.split(" : ");
                TextComponent action = new TextComponent(split[0]);
                action.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, split[1].replace("exe>", "")));
                component.addExtra(action);
            } else if(string.contains(": sgt>")){
                String[] split = string.split(" : ");
                TextComponent action = new TextComponent(split[0]);
                action.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, split[1].replace("sgt>", "")));
                component.addExtra(action);
            } else if(string.contains(": url>")){
                String[] split = string.split(" : ");
                TextComponent action = new TextComponent(split[0]);
                action.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, split[1].replace("url>", "")));
                component.addExtra(action);
            } else {
                for (BaseComponent components : TextComponent.fromLegacyText(string)) {
                    component.addExtra(components);
                }
            }
        } else {
            for(String cluster : string.split("/-/")){
                if(cluster.contains(": ttp>")){
                    String[] split = cluster.split(" : ");
                    TextComponent action = new TextComponent(split[0]);
                    if(cluster.contains(": exe>")){
                        action.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(split[1].replace("ttp>", ""))));
                        action.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, split[2].replace("exe>", "")));
                        component.addExtra(action);
                    } else if(cluster.contains(": sgt>")){
                        action.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(split[1].replace("ttp>", ""))));
                        action.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, split[2].replace("sgt>", "")));
                        component.addExtra(action);
                    } else if(cluster.contains(": url>")){
                        action.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(split[1].replace("ttp>", ""))));
                        action.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, split[2].replace("url>", "")));
                        component.addExtra(action);
                    } else {
                        action.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(split[1].replace("ttp>", ""))));
                        component.addExtra(action);
                    }
                } else if(cluster.contains(": exe>")){
                    String[] split = cluster.split(" : ");
                    TextComponent action = new TextComponent(split[0]);
                    action.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, split[1].replace("exe>", "")));
                    component.addExtra(action);
                } else if(cluster.contains(": sgt>")){
                    String[] split = cluster.split(" : ");
                    TextComponent action = new TextComponent(split[0]);
                    action.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, split[1].replace("sgt>", "")));
                    component.addExtra(action);
                } else if(cluster.contains(": url>")){
                    String[] split = cluster.split(" : ");
                    TextComponent action = new TextComponent(split[0]);
                    action.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, split[1].replace("url>", "")));
                    component.addExtra(action);
                } else {
                    for (BaseComponent components : TextComponent.fromLegacyText(cluster)) {
                        component.addExtra(components);
                    }
                }
            }
        }

        return component;
    }
}
