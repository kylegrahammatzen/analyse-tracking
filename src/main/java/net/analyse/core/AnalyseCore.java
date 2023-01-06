package net.analyse.core;

import net.analyse.api.AnalyseAPI;
import net.analyse.sdk.AnalyseSDK;

import java.util.UUID;

public class AnalyseCore {
    private final Platform platform;
    private AnalyseSDK sdk;
    private final AnalyseAPI api;

    public AnalyseCore(Platform platform, AnalyseSDK sdk, AnalyseAPI api) {
        this.platform = platform;
        this.sdk = sdk;
        this.api = api;
    }

    /**
     * Get the platform that this core is running on.
     */
    public Platform getPlatform() {
        return platform;
    }

    /**
     * Get the SDK that this core is using.
     */
    public AnalyseSDK getSDK() {
        return sdk;
    }

    /**
     * Update the SDK that this core is using.
     * <p>
     * @param sdk The new SDK to use.
     * This will be used for all future requests.
     * This will affect all new requests, but not any that are already in progress.
     */
    public void setSDK(AnalyseSDK sdk) {
        this.sdk = sdk;
    }

    /**
     * Get the API that this core is using.
     */
    public AnalyseAPI getApi() {
        return api;
    }

    /**
     * Get the AnalysePlayer object of a player
     * <p>
     * @param uuid The UUID of the player
     * @return The AnalysePlayer object of the player
     */
    public AnalysePlayer getPlayer(UUID uuid) {
        return api.getPlayer(uuid);
    }

    // Other methods go here
}