package com.nextdots.marvelapp.API.Retrofit;

import com.nextdots.marvelapp.API.Models.ResponseComic;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yxzan on 17/02/2017.
 */

public interface ComicInterface {

    @GET("comics")
    Call<ResponseComic> GetAllComic(@Query("limit") int limit
            , @Query("ts") long timestamp
            , @Query("apikey") String apikey
            , @Query("hash") String hashSignature);
}