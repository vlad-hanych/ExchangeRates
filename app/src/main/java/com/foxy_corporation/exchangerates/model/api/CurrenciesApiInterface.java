package com.foxy_corporation.exchangerates.model.api;

import com.foxy_corporation.exchangerates.utils.Constants;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;


/**
 * Created by Vlad on 14.05.2017.
 */

public interface CurrenciesApiInterface {
    @GET(Constants.LATEST)
    Call<ResponseBody> getCurrencies();
}