package com.example.pc.basemvp.data.source.remote.api.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.example.pc.basemvp.data.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.features.login.models
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/30/18
 * ❖ Time: 17:04
 * -------------^_^-------------
 **/
public class LoginResponse implements Parcelable {

    public static final Creator<LoginResponse> CREATOR = new Creator<LoginResponse>() {
        @Override
        public LoginResponse createFromParcel(Parcel source) {
            return new LoginResponse(source);
        }

        @Override
        public LoginResponse[] newArray(int size) {
            return new LoginResponse[size];
        }
    };

    @Expose
    @SerializedName("user")
    private User user;
    @Expose
    @SerializedName("access_token")
    private String token;

    public LoginResponse() {
    }

    protected LoginResponse(Parcel in) {
        this.user = in.readParcelable(User.class.getClassLoader());
        this.token = in.readString();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.user, flags);
        dest.writeString(this.token);
    }
}
