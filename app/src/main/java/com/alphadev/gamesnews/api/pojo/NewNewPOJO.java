package com.alphadev.gamesnews.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewNewPOJO {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("add")
    @Expose
    private NewPOJO add;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public NewPOJO getAdd() {
        return add;
    }

    public void setAdd(NewPOJO add) {
        this.add = add;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("success" + success).append("add" + add).toString();
    }
}
