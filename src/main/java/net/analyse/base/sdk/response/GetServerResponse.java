package net.analyse.base.sdk.response;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;

public class GetServerResponse {
    private final String name;
    private final String uuid;
    private final Instant createdAt;

    public GetServerResponse(@NotNull String name, @NotNull String uuid, @NotNull Instant createdAt) {
        this.name = name;
        this.uuid = uuid;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}