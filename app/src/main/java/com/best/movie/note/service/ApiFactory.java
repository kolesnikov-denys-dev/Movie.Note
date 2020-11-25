package com.best.movie.note.service;


import com.best.movie.note.utils.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiFactory {

    private static ApiFactory apiFactory;
    private static Retrofit retrofit;

    private ApiFactory() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static ApiFactory getInstance() {
        if (apiFactory == null) {
            apiFactory = new ApiFactory();
        }
        return apiFactory;
    }

    public ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }

}
