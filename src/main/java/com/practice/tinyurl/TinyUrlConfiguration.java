package com.practice.tinyurl;

import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

public class TinyUrlConfiguration extends Configuration {

    @NotNull
    private int serverId;
    @NotNull
    private int cacheCapacity;

    public int getCacheCapacity() {
        return cacheCapacity;
    }

    public void setCacheCapacity(int cacheCapacity) {
        this.cacheCapacity = cacheCapacity;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }
}
