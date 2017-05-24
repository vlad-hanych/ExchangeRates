package com.foxy_corporation.exchangerates.presenter.exchange_rates_support;

import com.foxy_corporation.exchangerates.model.api.ApiModelImp;
import com.foxy_corporation.exchangerates.model.api.ExchangeRatesApiInterface;
import com.foxy_corporation.exchangerates.utils.Constants;
import com.foxy_corporation.exchangerates.view.RefreshableView;
import com.foxy_corporation.exchangerates.view.exchange_rates_support.ExchangeRatesSupportView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vlad on 16.05.2017.
 */

public class ExchangeRatesSupportPresenterImp implements ExchangeRatesSupportPresenter {

    private ExchangeRatesSupportView mExchangeRatesSupportView;

    private ExchangeRatesApiInterface mExchangeRatesApiInterface;

    private Call<ResponseBody> mExchangeRatesCall;

    private ArrayList<String> mExchangeRatesForCurrency = new ArrayList<>();

    @Override
    public void onAttachView(RefreshableView refreshableView) {
        mExchangeRatesSupportView = (ExchangeRatesSupportView) refreshableView;

        mExchangeRatesApiInterface = ApiModelImp.getInstance().createService(ExchangeRatesApiInterface.class);

        loadExchangeRatesForCurrency();
    }

    @Override
    public void loadExchangeRatesForCurrency() {
        final String baseCurrency = mExchangeRatesSupportView.getBaseCurrencyName();

        mExchangeRatesCall = mExchangeRatesApiInterface.getExchangeRates(baseCurrency);
        mExchangeRatesCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!call.isCanceled()) {
                    try {
                        mExchangeRatesForCurrency.clear();

                        JSONObject rootJson = new JSONObject(response.body().string());

                        JSONObject ratesJson = rootJson.optJSONObject(Constants.RATES_KEY);

                        Iterator<String> iterator = ratesJson.keys();
                        while (iterator.hasNext()) {
                            String currentCurrency = iterator.next();

                            mExchangeRatesForCurrency
                                    .add(baseCurrency +
                                            Constants.DIVIDER + currentCurrency +
                                            Constants.COLON +
                                            ratesJson.optString(currentCurrency));
                        }

                        sortList();

                        mExchangeRatesSupportView.dataLoadSuccess(mExchangeRatesForCurrency); /// TODO check here
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                /*Log.d("exchange rates error! ... ", String.valueOf(t));*/

                if (!call.isCanceled()) {
                    mExchangeRatesSupportView.dataLoadError(Constants.CONNECTION_ERROR_TEXT);
                }
            }
        });
    }

    private void sortList() {
        Collections.sort(mExchangeRatesForCurrency, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });
    }

    @Override
    public void onDetachView() {
        stopExchangeRatesCall();

        mExchangeRatesApiInterface = null;

        mExchangeRatesSupportView = null;
    }

    @Override
    public void onNeedToRefreshData() {
        loadExchangeRatesForCurrency();
    }

    private void stopExchangeRatesCall() {
        if (mExchangeRatesCall != null) {
            mExchangeRatesCall.cancel();

            mExchangeRatesCall = null;
        }
    }
}
