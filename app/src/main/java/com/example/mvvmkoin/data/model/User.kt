package com.example.mvvmkoin.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
        @Expose
        @SerializedName("full_name")
        var fullName: String? = "",
        @Expose
        @SerializedName("description")
        var description: String? = "",
        @Expose
        @SerializedName("owner")
        var owner: Owner? = null,

        var isFavorite: Boolean = false
) : BaseModel<User>(), Parcelable {

    @Parcelize
    data class Owner(
            @Expose
            @field:SerializedName("login")
            var login: String? = "",
            @Expose
            @field:SerializedName("avatar_url")
            var avatarUrl: String? = ""
    ): Parcelable

    companion object {
        const val UNKNOWN_ID = -1
    }
}
