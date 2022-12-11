package com.example.hakatonfinaljava;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class News {
    @SerializedName("category")
    String category;
    @SerializedName("data")
    List<Data> data;
    @SerializedName("success")
    Boolean success;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
