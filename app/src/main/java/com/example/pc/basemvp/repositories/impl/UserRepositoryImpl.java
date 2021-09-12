package com.example.pc.basemvp.repositories.impl;

import com.example.pc.basemvp.data.model.User;
import com.example.pc.basemvp.data.source.remote.api.response.ApiResponse;
import com.example.pc.basemvp.data.source.remote.api.response.LoginResponse;
import com.example.pc.basemvp.data.source.remote.service.RetrofitProvider;
import com.example.pc.basemvp.repositories.UserRepository;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class UserRepositoryImpl implements UserRepository {
    private UserApi mUserApi;

    public UserRepositoryImpl() {
        this.mUserApi = RetrofitProvider.getInstance().makeApi(UserApi.class);
    }

    @Override
    public Single<ApiResponse<LoginResponse>> doLogin(String email, String password, int type) {
        return this.mUserApi.login(email, password, type);
    }

    @Override
    public Single<ApiResponse<User>> getUser() {
        return this.mUserApi.getUser();
    }

    interface UserApi {
        @FormUrlEncoded
        @POST("api/v1/auth/login")
        Single<ApiResponse<LoginResponse>> login(@Field("email") String email,
                @Field("password") String password, @Field("type") int type);

        @GET("api/v1/auth/me")
        Single<ApiResponse<User>> getUser();
    }
}
