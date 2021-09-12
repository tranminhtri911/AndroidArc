package com.example.pc.basemvp.repositories;

public interface TokenRepository {
    void saveToken(String token);

    String getToken();

    void clearToken();
}
