package config;

import java.io.File;
import java.io.IOException;

import static config.AppConfig.Name_File;

public class SystemConfig {
    // TODO: 11.07.2016 Настройка приложения: пока только автозапуск
    public void run() {
        File file = new File(System.getProperty("user.dir"));
        String s;
        try {
            s = "reg add HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run /v Hello /t REG_SZ /d " + file.getPath() + "\\Hello.jar";
            System.out.println(file.getName());
            System.out.println(file.getPath());
            Runtime.getRuntime().exec(s);
        } catch (Exception ex) {
        }
    }
}
