package com.foxy_corporation.exchangerates.view.exchange_rates_support;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.foxy_corporation.exchangerates.R;
import com.foxy_corporation.exchangerates.presenter.exchange_rates_support.ExchangeRatesSupportPresenterImp;
import com.foxy_corporation.exchangerates.utils.Constants;
import com.foxy_corporation.exchangerates.view.EmptyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Vlad on 16.05.2017.
 */

public class ExchangeRatesFragment extends Fragment implements ExchangeRatesSupportView {

    private ExchangeRatesSupportPresenterImp mExchangeRatesSupportPresenter = new ExchangeRatesSupportPresenterImp();

    private ExchangeRatesSupportAdapter mExchangeRatesSupportAdapter;

    private Activity mParentActivity;

    @BindView(R.id.swipLSwiper_FER)
    SwipeRefreshLayout mSwipSwiper;

    @BindView(R.id.rvExchangeRates_FER)
    RecyclerView mRVExchangeRates;

    private Unbinder mUnbinder;

    private String mCurrentCurrency;

    @Override
    public void onAttach(Context context) {
        /*Log.d("ExchangeRatesFragment...onAttach", getArguments().getString(Constants.BASE_CURRENCY_NAME_KEY));*/

        super.onAttach(context);

        mParentActivity = getActivity();

        mCurrentCurrency = getArguments().getString(Constants.BASE_CURRENCY_NAME_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*Log.d("ExchangeRatesFragment...onCreateView", getArguments().getString(Constants.BASE_CURRENCY_NAME_KEY));*/

        View rootView = inflater.inflate(R.layout.fragment_exchange_rates, container, false);

        mUnbinder = ButterKnife.bind(this, rootView);

        setUpSwiper();

        if (savedInstanceState == null)
            setUpListFirstTime();
        else
            restoreListState(savedInstanceState);

        return rootView;
    }

    private void setUpSwiper() {
        mSwipSwiper.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        /*Log.d("onRefresh()", "onRefresh called from SwipeRefreshLayout");*/

                        needToRefreshData();
                    }
                }
        );
    }

    private void setUpListFirstTime() {
        mRVExchangeRates.setHasFixedSize(true);
        mRVExchangeRates.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRVExchangeRates.setAdapter(new EmptyAdapter());
    }

    @Override
    public void onStart() {
        /*Log.d("ExchangeRatesFragment...", getArguments().getString(Constants.BASE_CURRENCY_NAME_KEY));*/
        super.onStart();

        mExchangeRatesSupportPresenter.onAttachView(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        /*Log.d("ExchangeRatesFragment...onSaveInstanceState", String.valueOf(outState) + this);*/

        super.onSaveInstanceState(outState);

        if (mExchangeRatesSupportAdapter != null) {
            outState.putStringArrayList(Constants.LIST_DOWNLOADED_DATA_KEY, (ArrayList<String>) mExchangeRatesSupportAdapter.getBaseAdapterList());

            // Save baseAdapterList state
            Parcelable listState = mRVExchangeRates.getLayoutManager().onSaveInstanceState();
            outState.putParcelable(Constants.LIST_POSITION_KEY, listState);
        }
    }

    private void restoreListState(Bundle savedState) {
        ArrayList <String> restoredListDownloadedData = savedState.getStringArrayList(Constants.LIST_DOWNLOADED_DATA_KEY);

        if (restoredListDownloadedData != null) {
            mRVExchangeRates.setHasFixedSize(true);

            mRVExchangeRates.setLayoutManager(new LinearLayoutManager(mParentActivity));

            Parcelable restoredListState = savedState.getParcelable(Constants.LIST_POSITION_KEY);

            mExchangeRatesSupportAdapter = new ExchangeRatesSupportAdapter(restoredListDownloadedData);

            mRVExchangeRates.setAdapter(mExchangeRatesSupportAdapter);
            mRVExchangeRates.getLayoutManager().onRestoreInstanceState(restoredListState);
        } else {
            setUpListFirstTime();
        }
    }

    @Override
    public void dataLoadSuccess(ArrayList <String> list) {
        /*Log.d("ExchangeRatesFragment...dataLoadSuccess", String.valueOf(list));*/

        setUpAdapter(list);

        mSwipSwiper.setRefreshing(false);
    }

    private void setUpAdapter(ArrayList <String> list) {
        if (mExchangeRatesSupportAdapter == null) {
            mExchangeRatesSupportAdapter = new ExchangeRatesSupportAdapter(list);

            mRVExchangeRates.setAdapter(mExchangeRatesSupportAdapter);
        } else
            mExchangeRatesSupportAdapter.setBaseAdapterList(list);
    }

    @Override
    public void dataLoadError(String message) {
        Toast.makeText(mParentActivity, Constants.CONNECTION_ERROR_TEXT, Toast.LENGTH_SHORT).show();

        mSwipSwiper.setRefreshing(false);
    }

    @Override
    public void needToRefreshData() {
        mExchangeRatesSupportPresenter.loadExchangeRatesForCurrency();
    }

    @Override
    public void onStop() {
        /*Log.d("ExchangeRatesFragment...onStop", String.valueOf(this));*/

        super.onStop();

        mExchangeRatesSupportPresenter.onDetachView();
    }

    @Override
    public void onDestroyView() {
        /*Log.d("ExchangeRatesFragment...onDestroyView()", String.valueOf(this));*/

        super.onDestroyView();

        mUnbinder.unbind();
    }

    @Override
    public String getBaseCurrencyName() {
        return mCurrentCurrency;
    }
}
