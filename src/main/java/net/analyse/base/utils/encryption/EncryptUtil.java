package net.analyse.base.utils.encryption;

import java.security.SecureRandom;
import java.util.Random;

public final class EncryptUtil {
    private EncryptUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // TODO: Could this be replaced with a standardized key generator?
    public static String generateEncryptionKey(int length){
        String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        int n = alphabet.length();

        StringBuilder result = new StringBuilder();
        Random r = new SecureRandom();

        for (int i = 0; i < length; i++)
            result.append(alphabet.charAt(r.nextInt(n)));

        return result.toString();
    }
}