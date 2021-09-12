package com.example.pc.basemvp.data.source.remote.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataResponse<Model> {
    @Expose
    @SerializedName("data")
    private Model data;
    @Expose
    @SerializedName("current_page")
    private int currentPage;
    @Expose
    @SerializedName("total")
    private int totalItem;
    @Expose
    @SerializedName("last_page")
    private int lastPage;

    public Model getData() {
        return data;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public int getLastPage() {
        return lastPage;
    }
}
