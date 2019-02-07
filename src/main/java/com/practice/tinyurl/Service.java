package com.practice.tinyurl;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class Service extends Application<TinyUrlConfiguration> {

    public static void main(String[] args) throws Exception {
        new Service().run(args);
    }

    @Override
    public void run(TinyUrlConfiguration configuration, Environment environment) {
        LRUCache cache = new LRUCache(configuration.getCacheCapacity());
        ServiceDao serviceDao = new ServiceDao(cache);
        TinyUrlService tinyUrlService = new TinyUrlService(configuration.getServerId(), serviceDao);
        environment.jersey().register(new TinyUrlResource(tinyUrlService));
    }
}
