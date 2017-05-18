package com.foxy_corporation.exchangerates.presenter.currencies;

import com.foxy_corporation.exchangerates.presenter.BasePresenter;

import java.util.ArrayList;

/**
 * Created by Vlad on 14.05.2017.
 */

public interface CurrenciesPresenter extends BasePresenter {
    void loadCurrencies();

    void clickCurrency(String currencyName, ArrayList <String> queriedList, int clickedPosition);
}
