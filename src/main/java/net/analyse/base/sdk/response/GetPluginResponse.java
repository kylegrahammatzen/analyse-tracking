package net.analyse.base.sdk.response;

import org.jetbrains.annotations.NotNull;

public class GetPluginResponse {
    private final String versionName;
    private final Integer versionNumber;
    private final String bukkitDownload;
    private final String bungeeDownload;
    private final String velocityDownload;

    public GetPluginResponse(@NotNull String versionName, @NotNull Integer versionNumber, @NotNull String bukkitDownload, @NotNull String bungeeDownload, @NotNull String velocityDownload) {
        this.versionName = versionName;
        this.versionNumber = versionNumber;
        this.bukkitDownload = bukkitDownload;
        this.bungeeDownload = bungeeDownload;
        this.velocityDownload = velocityDownload;
    }

    public String getVersionName() {
        return versionName;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public String getBukkitDownload() {
        return bukkitDownload;
    }

    public String getBungeeDownload() {
        return bungeeDownload;
    }

    public String getVelocityDownload() {
        return velocityDownload;
    }

    @Override
    public String toString() {
        return String.format("GetPluginResponse{versionName='%s', versionNumber=%d, bukkitDownload='%s', bungeeDownload='%s', velocityDownload='%s'}", versionName, versionNumber, bukkitDownload, bungeeDownload, velocityDownload);
    }
}