package com.foxy_corporation.exchangerates.presenter.exchange_rates_support;

import com.foxy_corporation.exchangerates.presenter.BasePresenter;

/**
 * Created by Vlad on 16.05.2017.
 */

interface ExchangeRatesSupportPresenter extends BasePresenter {
    void loadExchangeRatesForCurrency();
}
