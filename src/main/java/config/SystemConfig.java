package config;

import java.io.File;

public class SystemConfig {
    public static void setupAutoRun() {
        File file = new File(System.getProperty("user.dir"));
        String s;
        try {
            s = "reg add HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run /v Hello /t REG_SZ /d " + file.getPath() + "\\Hello.jar";
            Runtime.getRuntime().exec(s);
        } catch (Exception ex) {
        }
    }

    // TODO: 15.07.2016 Сделать!
    public static boolean hasAutoRunRegistryKey() {
        return false;
    }
}
