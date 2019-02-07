package com.practice.tinyurl;

import com.practice.tinyurl.model.TinyUrl;

import java.util.HashMap;

public class ServiceDao {

    private LRUCache cache;
    private HashMap<String, TinyUrl> hashMap = new HashMap();

    public ServiceDao(LRUCache cache) {
        this.cache = cache;
    }

    public void insertTinyUrl(TinyUrl tinyUrl) {
        this.hashMap.put(tinyUrl.getTinyUrl(), tinyUrl);
        this.cache.put(tinyUrl.getTinyUrl(), tinyUrl);
    }

    public TinyUrl getLongUrl(String tinyUrl) {
        return this.cache.get(tinyUrl);
    }

    public void deleteTinyUrl(String tinyUrl) {
        this.cache.remove(tinyUrl);
        this.hashMap.remove(tinyUrl);
    }
}
