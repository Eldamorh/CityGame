package com.eldus;

import java.util.UUID;

public final class City {
    private final String name;
    private final String id;

    public City(String name) {
        this.name = name;
        id = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
