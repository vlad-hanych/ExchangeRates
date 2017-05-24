package com.foxy_corporation.exchangerates.model.api;

/**
 * Created by Vlad on 24.05.2017.
 */


public interface ApiModel {
    <S> S createService(Class<S> serviceClass);
}
