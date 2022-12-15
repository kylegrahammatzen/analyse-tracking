package net.analyse.base.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ResourceUtils {
    public static InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = ResourceUtils.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new RuntimeException(String.format("File %s not found", fileName));
        } else {
            return inputStream;
        }
    }

    public static File getFile(String platform, String fileName) throws IOException {
        InputStream inputStream = ResourceUtils.getFileFromResourceAsStream(String.format("platform/%s/%s", platform, fileName));

        // Retrieve file name and extension
        String[] split = fileName.split("\\.");
        String name = split[0];
        String extension = split[1];

        // Convert the input stream to a File
        File tempFile = File.createTempFile(name, extension);

        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            // Read the input stream and write it to the output stream
            int read;

            byte[] buffer = new byte[8192];
            // Read the input stream using the buffer
            while ((read = inputStream.read(buffer)) != -1) {
                // Write the buffer to the output stream
                out.write(buffer, 0, read);
            }
        }

        return tempFile;
    }
}