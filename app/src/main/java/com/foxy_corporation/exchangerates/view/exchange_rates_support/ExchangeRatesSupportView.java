package com.foxy_corporation.exchangerates.view.exchange_rates_support;

import com.foxy_corporation.exchangerates.view.RefreshableView;

/**
 * Created by Vlad on 16.05.2017.
 */

public interface ExchangeRatesSupportView extends RefreshableView {
    String getBaseCurrencyName();
}
