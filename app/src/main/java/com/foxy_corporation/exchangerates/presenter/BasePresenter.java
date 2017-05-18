package com.foxy_corporation.exchangerates.presenter;

import com.foxy_corporation.exchangerates.view.RefreshableView;

/**
 * Created by Vlad on 14.05.2017.
 */

public interface BasePresenter {
    void onAttachView(RefreshableView refreshableView);

    void onDetachView();

    void onNeedToRefreshData();
}
