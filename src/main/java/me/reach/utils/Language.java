package me.reach.utils;

import dev.slickcollections.kiwizin.plugin.config.KConfig;
import dev.slickcollections.kiwizin.plugin.config.KWriter;
import dev.slickcollections.kiwizin.plugin.config.KWriter.YamlEntry;
import dev.slickcollections.kiwizin.plugin.config.KWriter.YamlEntryInfo;
import dev.slickcollections.kiwizin.plugin.logger.KLogger;
import dev.slickcollections.kiwizin.utils.StringUtils;
import org.bukkit.Bukkit;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

@SuppressWarnings("rawtypes")
public class Language {

    public static final KLogger LOGGER = ((KLogger) Main.getInstance().getLogger())
            .getModule("LANGUAGE");
    private static final KConfig CONFIG = Main.getInstance().getConfig("language");

    public static List<String> commands$forms$pages = Arrays.asList(
            "     §5§lFORMULÁRIOS\n \n§0Fique atento as regras do §6formulário§0 que você deseja fazer.\n \n /-/§0◾ §eAjudante : ttp>§7Aplicar-se para Ajudante! : url>https://formulario.redeseat.com",
            "      §5§lREQUISITOS\n \n§014 anos.\nMicrofone bom.\nUma boa maturidade.\nEntender de comandos.\nTer um bom tempo no servidor."
    );
    @YamlEntryInfo(annotation = "Só funciona no servidor atual!")
    public static Boolean options$notice_book$enabled = true;
    public static Integer options$notice_book$delay = 5;
    public static List<String> options$notice_book$pages = Arrays.asList(
            "        §6§lANÚNCIO\n \n§0Página #1",
            "        §6§lANÚNCIO\n \n§0Página #2"
    );

    public static void setupLanguage() {
        boolean save = false;
        KWriter writer =
                Main.getInstance().getWriter(CONFIG.getFile(),
                        "SeatUtils - Criado por reachFODA\nVersão da configuração: " + Main.getInstance()
                                .getDescription().getVersion());
        for (Field field : Language.class.getDeclaredFields()) {
            if (field.getName().contains("$") && !Modifier.isFinal(field.getModifiers())) {
                String nativeName = field.getName().replace("$", ".").replace("_", "-");

                try {
                    Object value;

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
                        writer.set(nativeName, new YamlEntry(new Object[]{"", CONFIG.get(nativeName)}));
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
                        writer.set(nativeName, new YamlEntry(new Object[]{"", value}));
                    }
                } catch (ReflectiveOperationException e) {
                    LOGGER.log(Level.WARNING, "Unexpected error on settings file: ", e);
                }
            }
        }

        if (save) {
            writer.write();
            CONFIG.reload();
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),
                    () -> LOGGER.info("A config §6language.yml §afoi modificada ou criada."));
        }
    }
}