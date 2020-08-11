package com.github.julyss2019.maven.plugins.copyer;

import java.io.*;

public class Util {
    public static final String OS_NAME = System.getProperty("os.name");

    public static void copyFile(File source, File dest) throws IOException {
        try (InputStream is = new FileInputStream(source); OutputStream os = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int length;

            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
    }
}
