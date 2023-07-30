package com.user.workspace.XUrl;

import java.security.SecureRandom;
import java.util.HashMap;


public class XUrlImpl implements XUrl{
    private static final String available = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    //To store mapping of Long URL -> Short URL
    HashMap<String,String> shortMap = new HashMap<String,String>();
    //To store mapping of Short URL -> Long URL
    HashMap<String,String> longMap = new HashMap<String,String>();
    //To store Hit Count of Long URL
    HashMap<String,Integer> hitMap = new HashMap<String,Integer>();

    @Override
    public String registerNewUrl(String longUrl) {
        if(shortMap.containsKey(longUrl)){
            hitMap.put(longUrl , hitMap.getOrDefault(longUrl , 0) + 1);
            return shortMap.get(longUrl);
        }

        StringBuilder shUrl = new StringBuilder();
        shUrl.append("http://short.url/");
        shUrl.append(randomString(9));
        String url = shUrl.toString();

        shortMap.put(longUrl , url);
        longMap.put(url , longUrl);
        hitMap.put(longUrl , hitMap.getOrDefault(longUrl , 0) + 1);
        //Returning Short URL
        return url;
    }

    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        if(shortMap.containsKey(longUrl)){
            String url = shortMap.get(longUrl);
            if(url.equals(shortUrl)){
                return shortUrl;
            } else {
                return null;
            }
        }
        if(longMap.containsKey(shortUrl)){
            String url = longMap.get(shortUrl);
            if(url.equals(longUrl)){
                return shortUrl;
            } else {
                return null;
            }
        }
        if(shortUrl.length() == 0){
            shortUrl = registerNewUrl(longUrl);
        } else {
            shortMap.put(longUrl , shortUrl);
            longMap.put(shortUrl , longUrl);
        }
        return shortUrl;
    }

    @Override
    public String getUrl(String shortUrl) {
        if(longMap.containsKey(shortUrl)){
            return longMap.get(shortUrl);
        }
        return null;
    }

    @Override
    public Integer getHitCount(String longUrl) {
        return hitMap.getOrDefault(longUrl , 0);
    }

    @Override
    public String delete(String longUrl) {
        if(shortMap.containsKey(longUrl)){
            String shortUrl = shortMap.get(longUrl);
            shortMap.remove(longUrl);
            longMap.remove(shortUrl);
        }
        return null;
    }

    private String randomString(int len){
        SecureRandom rand = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(available.charAt(rand.nextInt(available.length())));
        return sb.toString();
    }
}
