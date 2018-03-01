package com.ntl.udacity.capstoneproject.data.model;


import com.google.gson.annotations.SerializedName;

public class AccessToken
{
    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private long expiresIn;

    private long expiredAfterMilli = 0;

    @SerializedName("refresh_token")
    private String refreshToken;

    public String getAccessToken()
    {
        return accessToken;
    }

    public String getTokenType()
    {
        return tokenType;
    }

    public long getExpiresIn()
    {
        return expiresIn;
    }

    public long getExpiredAfterMilli()
    {
        return expiredAfterMilli;
    }

    public String getRefreshToken()
    {
        return refreshToken;
    }
}
