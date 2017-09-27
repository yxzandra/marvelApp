package com.nextdots.marvelapp.API.Retrofit;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nextdots.marvelapp.API.Models.ResponseComic;
import com.nextdots.marvelapp.Interface.OnResponseInterface;
import com.nextdots.marvelapp.R;

import java.util.Calendar;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yxzan on 17/02/2017.
 */

public class RetrofitMagnament implements Callback<ResponseComic> {

    private OnResponseInterface onResponseInterface = null;

    public RetrofitMagnament(OnResponseInterface ml) {
        this.onResponseInterface = ml;
    }

    public void start(Context context, int cantComic) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.URL))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ComicInterface gerritAPI = retrofit.create(ComicInterface.class);

        long timeStamp = Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis() / 1000L;
        String hashSignature = HashUtil.md5(String.valueOf(timeStamp) + context.getString(R.string.private_key) + context.getString(R.string.public_key));

        Call<ResponseComic> call = gerritAPI.GetAllComic(cantComic, timeStamp,context.getString(R.string.public_key),hashSignature);
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<ResponseComic> call, Response<ResponseComic> response) {
        if(response.isSuccessful()) {
            onResponseInterface.onResponseMethod(response.body().getData().getResults());
        } else {
            System.out.println(response.message());
        }
    }

    @Override
    public void onFailure(Call<ResponseComic> call, Throwable t)
    {
        t.printStackTrace();
    }
}


