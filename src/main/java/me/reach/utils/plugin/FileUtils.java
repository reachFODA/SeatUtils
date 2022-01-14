package me.reach.utils.plugin;

import me.reach.utils.bungee.Main;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

public class FileUtils {

    public static void deleteFile(File file) {
        if (!file.exists()) {
            return;
        }

        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                deleteFile(f);
            }
        }

        file.delete();
    }

    public static void copyFiles(File in, File out) {
        if (in.isDirectory()) {
            if (!out.exists()) {
                out.mkdirs();
            }

            for (File file : in.listFiles()) {
                if (file.getName().equals("uid.dat")) {
                    continue;
                }

                copyFiles(file, new File(out, file.getName()));
            }
        } else {
            try {
                copyFile(new FileInputStream(in), out);
            } catch (IOException ex) {
                Main.getInstance().getLogger().log(Level.WARNING, "Unexpected error ocurred copying file \"" + out.getName() + "\"!", ex);
            }
        }
    }

    public static void copyFile(InputStream input, File out) {
        FileOutputStream ou = null;
        try {
            ou = new FileOutputStream(out);
            byte[] buff = new byte[1024];
            int len;
            while ((len = input.read(buff)) > 0) {
                ou.write(buff, 0, len);
            }
        } catch (IOException ex) {
            Main.getInstance().getLogger().log(Level.WARNING, "Unexpected error ocurred copying file \"" + out.getName() + "\"!", ex);
        } finally {
            try {
                if (ou != null) {
                    ou.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (IOException ignored) {}
        }
    }
}
