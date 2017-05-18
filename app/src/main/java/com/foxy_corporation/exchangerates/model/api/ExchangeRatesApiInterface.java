package com.foxy_corporation.exchangerates.model.api;

import com.foxy_corporation.exchangerates.utils.Constants;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Vlad on 15.05.2017.
 */

public interface ExchangeRatesApiInterface {
    @GET(Constants.LATEST)
    Call<ResponseBody> getExchangeRates(@Query(Constants.BASE) String currencyName);
}
