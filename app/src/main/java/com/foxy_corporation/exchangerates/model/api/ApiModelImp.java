package com.foxy_corporation.exchangerates.model.api;

import com.foxy_corporation.exchangerates.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Vlad on 14.05.2017.
 */

public class ApiModelImp implements ApiModel {

    private static ApiModelImp instance;

    private Retrofit mRetrofit;

    /// singleton, use Dagger 2
    public static ApiModelImp getInstance() {
        if (instance == null) {
            instance = new ApiModelImp();
        }

        return instance;
    }

    private ApiModelImp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Constants.API_BASE_URL);
        builder.client(okHttpClient);


        mRetrofit = builder.client(okHttpClient).build();
    }

    @Override
    public <S> S createService(Class<S> serviceClass) {
        return mRetrofit.create(serviceClass);
    }
}
