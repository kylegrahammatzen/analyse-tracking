package net.analyse.base.utils.player;

import java.util.UUID;

public class AnalysePlayer {

    private final String name;
    private final UUID uuid;
    private final String country;
    private final String domain;

    public AnalysePlayer(String name, UUID uuid, String country, String domain) {
        this.name = name;
        this.uuid = uuid;
        this.country = country;
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getCountry() {
        return country;
    }

    public String getDomain() {
        return domain;
    }
}