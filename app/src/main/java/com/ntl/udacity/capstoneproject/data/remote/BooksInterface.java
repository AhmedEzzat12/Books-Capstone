package com.ntl.udacity.capstoneproject.data.remote;

import com.ntl.udacity.capstoneproject.data.model.AccessToken;
import com.ntl.udacity.capstoneproject.data.model.BookShelfResponse;
import com.ntl.udacity.capstoneproject.data.model.KindBooksVolume;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface BooksInterface
{
    @Headers("No-Authentication: true")
    @FormUrlEncoded
    @POST("oauth2/v4/token")
    Call<AccessToken> requestAccessToken(
            @Field("code") String code,
            @Field("client_id") String client_id,
            @Field("redirect_uri") String redirect_uri,
            @Field("grant_type") String grant_type);

    @Headers("No-Authentication: true")
    @FormUrlEncoded
    @POST("oauth2/v4/token")
    Call<AccessToken> refreshAccessToken(
            @Field("refresh_token")String refresh_token,
            @Field("client_id")String client_id,
            @Field("grant_type")String grant_type);


    @GET("books/v1/volumes")
    Call<KindBooksVolume> searchVolumes(
            @Query("q") String searchQuery);

    @GET("books/v1/volumes")
    Call<AccessToken> getSpecificVolume(
            @Path("volumeId") String id);

    @GET("books/v1/mylibrary/bookshelves")
    Call<BookShelfResponse> getUserBookshelves();

    @GET("books/v1/mylibrary/bookshelves/{shelf}/volumes")
    Call<KindBooksVolume> getlistOfVolumesFromBookshelf(@Path("shelf") String shelfId);

    @POST("books/v1/mylibrary/bookshelves/{shelf}/addVolume")
    Call<EmptyResponse> addVolumeToBookshelf(@Path("shelf") String shelfId, @Query("volumeId") String volumeId);

    @POST("books/v1/mylibrary/bookshelves/{shelf}/removeVolume")
    Call<EmptyResponse> removeVolumeFromBookshelf(@Path("shelf") String shelfId, @Query("volumeId") String volumeId);

    @POST("books/v1/mylibrary/bookshelves/{shelf}/clearVolumes")
    Call<EmptyResponse> clearVolumesFromBookshelf(@Path("shelf") String shelfId);


}
