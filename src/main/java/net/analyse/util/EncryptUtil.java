package net.analyse.util;

import java.util.UUID;

/**
 * A utility class to generate encrypted key.
 *
 * @author Analyse, <a href="http://www.analyse.net">...</a>
 * @version 1.0
 */
public class EncryptUtil {

    /**
     * Generates a server encryption key.
     * @param length the length of the key
     * @return the generated key
     */
    public static String generateEncryptionKey(int length){
        UUID uuid = UUID.randomUUID();
        String key = uuid.toString().replaceAll("-", "");
        return key.substring(0, length);
    }
}