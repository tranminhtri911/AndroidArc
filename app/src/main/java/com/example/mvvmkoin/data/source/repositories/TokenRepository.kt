package com.example.mvvmkoin.data.source.repositories

import com.example.mvvmkoin.data.model.Token
import com.example.mvvmkoin.data.source.local.sharedprf.SharedPrefsApi
import com.example.mvvmkoin.data.source.local.sharedprf.SharedPrefsKey
import com.example.mvvmkoin.util.extension.notNull

class TokenRepository
constructor(private val sharedPrefsApi: SharedPrefsApi) {

    private var tokenCache: Token? = null

    fun getToken(): Token? {
        tokenCache.notNull {
            return it
        }

        val token = sharedPrefsApi.get(SharedPrefsKey.KEY_TOKEN, Token::class.java)
        token.notNull {
            tokenCache = it
            return it
        }

        return null
    }


    fun saveToken(token: Token) {
        tokenCache = token
        sharedPrefsApi.put(SharedPrefsKey.KEY_TOKEN, tokenCache)
    }

    fun clearToken() {
        tokenCache = null
        sharedPrefsApi.clear()
    }

}