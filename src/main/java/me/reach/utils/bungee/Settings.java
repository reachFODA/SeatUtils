package me.reach.utils.bungee;

import dev.slickcollections.kiwizin.utils.StringUtils;
import me.reach.utils.plugin.config.ReachConfig;
import me.reach.utils.plugin.config.ReachLogger;
import me.reach.utils.plugin.config.ReachWriter;
import net.md_5.bungee.api.ProxyServer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

@SuppressWarnings({"rawtypes"})
public class Settings {

    public static List<String> options$online_servers$blacklist = Collections.singletonList("login");

    public static final ReachLogger LOGGER = ((ReachLogger) Main.getInstance().getLogger()).getModule("LANGUAGE");
    private static final ReachConfig CONFIG = ReachConfig.getConfig("language");

    public static void setupSettings() {
        boolean save = false;
        ReachWriter writer = new ReachWriter(CONFIG.getFile(), "SeatUtils - Criado por Reach\nVersão da configuração: " + Main.getInstance().getDescription()
                .getVersion());
        for (Field field : Settings.class.getDeclaredFields()) {
            if (field.getName().contains("$") && !Modifier.isFinal(field.getModifiers())) {
                String nativeName = field.getName().replace("$", ".").replace("_", "-");

                try {
                    Object value;
                    ReachWriter.YamlEntryInfo entryInfo = field.getAnnotation(ReachWriter.YamlEntryInfo.class);

                    if (CONFIG.contains(nativeName)) {
                        value = CONFIG.get(nativeName);
                        if (value instanceof String) {
                            value = StringUtils.formatColors((String) value).replace("\\n", "\n");
                        } else if (value instanceof List) {
                            List l = (List) value;
                            List<Object> list = new ArrayList<>(l.size());
                            for (Object v : l) {
                                if (v instanceof String) {
                                    list.add(StringUtils.formatColors((String) v).replace("\\n", "\n"));
                                } else {
                                    list.add(v);
                                }
                            }

                            value = list;
                        }

                        field.set(null, value);
                        writer.set(nativeName, new ReachWriter.YamlEntry(
                                new Object[]{entryInfo == null ? "" : entryInfo.annotation(),
                                        CONFIG.get(nativeName)}));
                    } else {
                        value = field.get(null);
                        if (value instanceof String) {
                            value = StringUtils.deformatColors((String) value).replace("\n", "\\n");
                        } else if (value instanceof List) {
                            List l = (List) value;
                            List<Object> list = new ArrayList<>(l.size());
                            for (Object v : l) {
                                if (v instanceof String) {
                                    list.add(StringUtils.deformatColors((String) v).replace("\n", "\\n"));
                                } else {
                                    list.add(v);
                                }
                            }

                            value = list;
                        }

                        save = true;
                        writer.set(nativeName, new ReachWriter.YamlEntry(
                                new Object[]{entryInfo == null ? "" : entryInfo.annotation(), value}));
                    }
                } catch (ReflectiveOperationException e) {
                    LOGGER.log(Level.WARNING, "Unexpected error on settings file: ", e);
                }
            }
        }

        if (save) {
            writer.write();
            CONFIG.reload();
            ProxyServer.getInstance().getScheduler().runAsync(Main.getInstance(), () ->
                    LOGGER.info("A config §6language.yml §afoi modificada ou criada."));
        }
    }
}
