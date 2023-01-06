package net.analyse.core;

import com.google.gson.Gson;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class CustomEvent {
    private static final Gson GSON = new Gson();
    private final String id;
    private final Instant time;
    private final Map<String, Object> metadata;

    public CustomEvent(String id, Instant time) {
        this.id = id;
        this.time = time;
        this.metadata = new HashMap<>();
    }

    public CustomEvent withMetadata(String key, Object value) {
        metadata.put(key, value);
        return this;
    }

    public String getId() {
        return id;
    }

    public Instant getTime() {
        return time;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public String toJson() {
        return GSON.toJson(this);
    }
}