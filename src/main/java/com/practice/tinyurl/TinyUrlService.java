package com.practice.tinyurl;

import com.practice.tinyurl.model.TinyUrl;

import java.nio.ByteBuffer;
import java.util.Base64;


//
public class TinyUrlService {
    private static Short counter = 0; // 0 - 65535
    private ServiceDao serviceDao;
    private int serverId; // 8 bits


    public TinyUrlService(int serverId, ServiceDao serviceDao) {
        this.serverId = serverId;
        this.serviceDao = serviceDao;
    }

    private int getCounter() {
        synchronized (counter) {
            return counter == 65535 ? 0 : ++counter;
        }
    }

    public String createTinyUrl(String longUrl, Long expire) {

        int counter = getCounter();
        ByteBuffer byteBuffer = ByteBuffer.allocate(Long.BYTES);
        long uniqueId = (System.currentTimeMillis() << 24);
        uniqueId |= (serverId << 16);
        uniqueId |= counter;
        byteBuffer.putLong(uniqueId);
        String key = Base64.getUrlEncoder().withoutPadding().encodeToString(byteBuffer.array());

        TinyUrl tinyUrl = new TinyUrl();
        tinyUrl.setTinyUrl(key);
        tinyUrl.setLongUrl(longUrl);
        tinyUrl.setExpireDate(expire);
        this.serviceDao.insertTinyUrl(tinyUrl);
        return key;
    }

    public String getLongUrl(String tinyUrl) {
        TinyUrl url = this.serviceDao.getLongUrl(tinyUrl);
        if (url == null) {
            return null;
        } else if (url.getExpireDate() < System.currentTimeMillis()) {
            this.serviceDao.deleteTinyUrl(url.getTinyUrl());
            return null;
        } else {
            return url.getLongUrl();
        }
    }
}
