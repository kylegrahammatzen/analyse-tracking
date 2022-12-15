package net.analyse.base.sdk;

import net.analyse.base.sdk.exception.ServerNotFoundException;
import net.analyse.base.sdk.response.GetPluginResponse;
import net.analyse.base.sdk.response.GetServerResponse;
import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

public class AnalyseSDK {

    private final String token;
    private final String encryptionKey;
    private final String baseUrl;

    /**
     * @param token The token of the server.
     * @param encryptionKey The encryption key of the server.
     * @param baseUrl The base url of the api endpoint.
     */
    public AnalyseSDK(String token, String encryptionKey, String baseUrl) {
        this.token = token;
        this.encryptionKey = encryptionKey;
        this.baseUrl = baseUrl;
    }

    /**
     * @param token The token of the server.
     * @param encryptionKey The encryption key of the server.
     */
    public AnalyseSDK(String token, String encryptionKey) {
        this(token, encryptionKey, "https://app.analyse.net/api/v1/");
    }

    /**
     * The server token.
     * @return The server token.
     */
    public String getToken() {
        return token;
    }

    /**
     * The API endpoint url.
     * @return The API endpoint url.
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * @return The server information.
     //* @throws ServerNotFoundException
     */
    public GetServerResponse getServer() {
        return new GetServerResponse("Test", "0000", Instant.now());
    }

    /**
     * @param ipAddress The ip address of the player.
     * @return The hashed ip address.
     */
    public String hashIp(@NotNull String ipAddress) {
        String generatedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(this.encryptionKey.getBytes());
            byte[] bytes = md.digest(ipAddress.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedPassword;
    }

    public GetPluginResponse getPluginVersion() {
        return new GetPluginResponse("Test", 0, "1.0.0", "1.0.0", "1.0.0");
    }

    /**
     * @param playerCount The amount of players on the server.
     * @throws ServerNotFoundException If the server is not found.
     */
    public void sendHeartbeat(int playerCount) throws ServerNotFoundException {
        System.out.println("[SDK] Sending heartbeat with " + playerCount + " players.");
    }
}