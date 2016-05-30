package com.ui.demo.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class IoUtil {

    private static final int READ_CACHE_SIZE = 8192;

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] readToBytes(InputStream inputStream) {

        DataInputStream dis = null;
        try {
            byte[] cache = new byte[READ_CACHE_SIZE];
            int length = inputStream.read(cache);
            if (length < READ_CACHE_SIZE) {
                byte[] result = new byte[length];
                System.arraycopy(cache, 0, result, 0, length);
                return result;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(dis);
        }

        return null;
    }

    public static String readToString(InputStream inputStream) throws IOException {

        if (null == inputStream) {
            return null;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer buffer = new StringBuffer();
        String line = null;
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }

        return buffer.toString();
    }

    public static void writeToFile(InputStream inputStream, String path) {
        writeToFile(inputStream, new File(path));
    }

    public static void writeToFile(InputStream inputStream, File targetFile) {

        if (inputStream == null || targetFile == null) {
            return;
        }

        OutputStream outputStream = null;
        try {
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }

            outputStream = new FileOutputStream(targetFile);

            byte[] cache = new byte[READ_CACHE_SIZE];
            int count = 0;
            while ((count = inputStream.read(cache)) != -1) {
                outputStream.write(cache, 0, count);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(outputStream);
        }
    }

}
