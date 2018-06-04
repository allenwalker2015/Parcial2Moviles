package com.alphadev.gamesnews.api.pojo;

import com.alphadev.gamesnews.api.pojo.New;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewNew {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("add")
    @Expose
    private New add;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public New getAdd() {
        return add;
    }

    public void setAdd(New add) {
        this.add = add;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("success" + success).append("add" + add).toString();
    }
}
