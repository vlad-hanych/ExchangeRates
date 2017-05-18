package com.foxy_corporation.exchangerates.presenter.currencies;

import com.foxy_corporation.exchangerates.model.api.ApiHelper;
import com.foxy_corporation.exchangerates.model.api.CurrenciesApiInterface;
import com.foxy_corporation.exchangerates.utils.Constants;
import com.foxy_corporation.exchangerates.view.RefreshableView;
import com.foxy_corporation.exchangerates.view.currencies.CurrenciesView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vlad on 14.05.2017.
 */

public class CurrenciesPresenterImp implements CurrenciesPresenter {

    private CurrenciesView mCurrenciesView;

    private Call<ResponseBody> mGetCurrenciesCB;

    private ArrayList<String> mDownloadedCurrencies = new ArrayList<>();

    @Override
    public void onAttachView(RefreshableView refreshableView) {
        mCurrenciesView = (CurrenciesView) refreshableView;

        mGetCurrenciesCB = ApiHelper.getInstance().createService(CurrenciesApiInterface.class).getCurrencies();

        loadCurrencies();
    }


    @Override
    public void loadCurrencies() {
        mGetCurrenciesCB.clone().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!call.isCanceled()) {
                    handleResponse(response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (!call.isCanceled())
                    mCurrenciesView.dataLoadError(t.getMessage());

            }
        });
    }

    private void handleResponse(Response<ResponseBody> response) {
        try {
            mDownloadedCurrencies.clear();

            JSONObject rootJson = new JSONObject(response.body().string());

            mDownloadedCurrencies.add(rootJson.optString(Constants.BASE));

            JSONObject ratesJson = rootJson.optJSONObject(Constants.RATES_KEY);

            Iterator<String> iterator = ratesJson.keys();
            while (iterator.hasNext()) {
                mDownloadedCurrencies.add(iterator.next());
            }
            mCurrenciesView.dataLoadSuccess(mDownloadedCurrencies); /// TODO check here
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clickCurrency(String clickedCurrency, ArrayList <String> queriedList, int clickedPosition) {
        /*Log.d("presenter...clickCurrency", clickedCurrency);*/

        mCurrenciesView.startExchangeRatesView(queriedList, clickedCurrency, clickedPosition); /// здесь можно передавать и весь список
    }

    @Override
    public void onDetachView() {
        stopCallback();

        mCurrenciesView = null;
    }

    @Override
    public void onNeedToRefreshData() {
        loadCurrencies();
    }

    private void stopCallback() {
        if (mGetCurrenciesCB != null) {
            mGetCurrenciesCB.cancel();

            mGetCurrenciesCB = null;
        }
    }
}
