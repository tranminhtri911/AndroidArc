package com.example.pc.basemvp.data.source.local.sharePrf;

public interface SharedPrefsApi {
    <T> T get(String key, Class<T> clazz);

    <T> void put(String key, T data);

    void clear();

    void clearKey(String key);
}
