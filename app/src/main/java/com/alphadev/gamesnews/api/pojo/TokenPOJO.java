package com.alphadev.gamesnews.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenPOJO {

    @SerializedName("token")
    @Expose
    private String token;

    public String getToken() {
        return token;
    }

    public String getProcessedToken() {
        return "Bearer " + token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}