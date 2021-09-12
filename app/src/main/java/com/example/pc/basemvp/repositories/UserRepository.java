package com.example.pc.basemvp.repositories;

import com.example.pc.basemvp.data.model.User;
import com.example.pc.basemvp.data.source.remote.api.response.ApiResponse;
import com.example.pc.basemvp.data.source.remote.api.response.LoginResponse;
import io.reactivex.Single;

public interface UserRepository {

    Single<ApiResponse<LoginResponse>> doLogin(String email, String password, int type);

    Single<ApiResponse<User>> getUser();
}
