package com.foxy_corporation.exchangerates.view.currencies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.foxy_corporation.exchangerates.R;
import com.foxy_corporation.exchangerates.presenter.currencies.CurrenciesPresenter;
import com.foxy_corporation.exchangerates.presenter.currencies.CurrenciesPresenterImp;
import com.foxy_corporation.exchangerates.utils.Constants;
import com.foxy_corporation.exchangerates.utils.Utils;
import com.foxy_corporation.exchangerates.view.EmptyAdapter;
import com.foxy_corporation.exchangerates.view.exchange_rates_main.ExchangeRatesActivity;
import com.lapism.searchview.SearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CurrenciesActivity extends AppCompatActivity implements CurrenciesView {

    private CurrenciesPresenter mPresenter = new CurrenciesPresenterImp();

    private CurrenciesAdapter mCurrenciesAdapter;

    @BindView(R.id.lapisSearSearch_TC)
    SearchView mLapisSearSearch;

    @BindView(R.id.swipLSwiper_AC)
    SwipeRefreshLayout mSwipSwiper;

    @BindView(R.id.rvCurrencies_AC)
    RecyclerView mRVCurrencies;

    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Log.d("CurrenciesActivity...", String.valueOf(savedInstanceState));*/

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_currencies);

        ButterKnife.bind(this);

        setUpSearch();

        setUpSwiper();

        if (savedInstanceState == null)
            setUpListFirstTime();
        else
            restoreListState(savedInstanceState);

    }

    private void setUpSearch() {
        /*Log.d("CurrenciesActivity...setUpSearch()", "!");*/
        mLapisSearSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                /*Log.d("onQueryTextSubmit", query);*/

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                /*Log.d("onQueryTextChange", newText);*/

                tryToFilterList(newText);

                return false;
            }
        });
    }

    private void tryToFilterList(String newText) {
        if (mCurrenciesAdapter != null) {
            mCurrenciesAdapter.filter(newText);
        }
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
        mRVCurrencies.setHasFixedSize(true);

        mRVCurrencies.setLayoutManager(new LinearLayoutManager(this));

        mRVCurrencies.setAdapter(new EmptyAdapter());
    }

    private void restoreListState(Bundle savedState) {
        ArrayList <String> restoredListCurrentData = savedState.getStringArrayList(Constants.LIST_CURRENT_DATA_KEY);

        if (restoredListCurrentData != null) {
            mRVCurrencies.setHasFixedSize(true);

            mRVCurrencies.setLayoutManager(new LinearLayoutManager(this));

            ArrayList <String> restoredListDownloadedData = savedState.getStringArrayList(Constants.LIST_DOWNLOADED_DATA_KEY);

            Parcelable restoredListState = savedState.getParcelable(Constants.LIST_POSITION_KEY);

            mCurrenciesAdapter = new CurrenciesAdapter(restoredListCurrentData, mPresenter);
            mCurrenciesAdapter.setDownloadedCurrenciesList(restoredListDownloadedData);

            mRVCurrencies.setAdapter(mCurrenciesAdapter);
            mRVCurrencies.getLayoutManager().onRestoreInstanceState(restoredListState);
        } else {
            setUpListFirstTime();
        }
    }

    @Override
    public void onStart() {
        /*Log.d ("CurrenciesActivity...onStart ()", String.valueOf(this));*/

        super.onStart();

        mPresenter.onAttachView(CurrenciesActivity.this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        /*Log.d("CurrenciesActivity...", String.valueOf(outState) + this);*/

        if (mCurrenciesAdapter != null) {
            // Save current items
            outState.putStringArrayList(Constants.LIST_CURRENT_DATA_KEY, (ArrayList<String>) mCurrenciesAdapter.getBaseAdapterList());

            // Save all downloaded items
            outState.putStringArrayList(Constants.LIST_DOWNLOADED_DATA_KEY, (ArrayList<String>) mCurrenciesAdapter.getDownloadedCurrenciesList());

            // Save baseAdapterList state
            Parcelable listState = mRVCurrencies.getLayoutManager().onSaveInstanceState();
            outState.putParcelable(Constants.LIST_POSITION_KEY, listState);

            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void dataLoadSuccess(ArrayList<String> list) {
        if (mCurrenciesAdapter == null) {
            mCurrenciesAdapter = new CurrenciesAdapter(list, mPresenter);

            mRVCurrencies.setAdapter(mCurrenciesAdapter);
        } else
            mCurrenciesAdapter.setDownloadedCurrenciesList(list);

        String searchText = String.valueOf(mLapisSearSearch.getTextOnly());
        if (!searchText.equals(Constants.EMPTY_STRING))
            tryToFilterList(searchText);

        mSwipSwiper.setRefreshing(false);
    }

    @Override
    public void dataLoadError(String message) {
        Toast.makeText(this, Constants.CONNECTION_ERROR_TEXT, Toast.LENGTH_SHORT).show();

        mSwipSwiper.setRefreshing(false);
    }

    @Override
    public void needToRefreshData() {
        mPresenter.onNeedToRefreshData();
    }

    @Override
    public void startExchangeRatesView(ArrayList<String> currenciesList, String baseCurrencyName,
                                       int neededPosition) {
        /*Log.d("CurrenciesActivity...startExchangeRatesView : ", baseCurrencyName);*/

        if (Utils.isInternetAvailable(this)) {
            startExchangeRatesActivity((ArrayList<String>) currenciesList, baseCurrencyName, neededPosition);
        } else
            Toast.makeText(this, Constants.NO_CONNECTION, Toast.LENGTH_SHORT).show();
    }

    private void startExchangeRatesActivity(ArrayList<String> currenciesList,
                                            String baseCurrencyName,
                                            int neededPosition) {
        if (mIntent == null)
            mIntent = new Intent(this, ExchangeRatesActivity.class);

        mIntent.putStringArrayListExtra(Constants.CURRENCIES_ARRAY_LIST, currenciesList);
        mIntent.putExtra(Constants.BASE_CURRENCY_NAME_KEY, baseCurrencyName);
        mIntent.putExtra(Constants.NEEDED_POSITION_KEY, neededPosition);

        startActivity(mIntent);
    }

    @Override
    public void onStop() {
        super.onStop();

        mPresenter.onDetachView();
    }

}
