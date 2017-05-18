package com.foxy_corporation.exchangerates.view.currencies;

import com.foxy_corporation.exchangerates.view.RefreshableView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 14.05.2017.
 */

public interface CurrenciesView extends RefreshableView {

    void startExchangeRatesView(ArrayList<String> curencies, String baseCurrencyName, int neededPosition);
}

