package com.example.pc.basemvp.data.source.remote.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.common.models.api_response
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/30/18
 * ❖ Time: 17:02
 * -------------^_^-------------
 **/
public class ApiResponse<Model> {
    @Expose
    @SerializedName("status")
    private Boolean status;
    @Expose
    @SerializedName("data")
    private Model data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Model getData() {
        return data;
    }

    public void setData(Model data) {
        this.data = data;
    }
}
