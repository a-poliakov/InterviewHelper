package config;

import util.ConstantManager;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.nio.channels.FileLock;

public class SystemConfig {
    public static void setupAutoRun() {
        File file = new File(System.getProperty("user.dir"));
        String s;
        try {
            s = "reg add HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run /v " + AppConfig.INTERVIEW_HELPER_JAR + " /t REG_SZ /d " + file.getPath() + "\\" + AppConfig.INTERVIEW_HELPER_JAR + ".jar";
            Runtime.getRuntime().exec(s);
        } catch (Exception ex) {
        }
        try {
            s = "taskkill /IM reg.exe /F";
            Runtime.getRuntime().exec(s);
        } catch (Exception ex) {
        }
    }

    // TODO: 15.07.2016 Сделать!
    public static boolean hasAutoRunRegistryKey() {
        return false;
    }

    // TODO: 16.07.2016 Проверка на запуск осуществляется через порт. После закрытия приложения автоматически закрывается порт
    private static ServerSocket socket;
    public static boolean isRun () throws IOException {
        try {
            socket = new ServerSocket(ConstantManager.PORT,0, InetAddress.getByAddress(new byte[] {127,0,0,1}));
            socket.close();
            return false;
        }catch (BindException e)
        {
            return true;
        }

    }
    public static void run () throws IOException {

        try {
            socket = new ServerSocket(ConstantManager.PORT,0, InetAddress.getByAddress(new byte[] {127,0,0,1}));
        }
        catch (BindException e) {
            System.out.print("Что-то пошло не так");
        }
    }
}
