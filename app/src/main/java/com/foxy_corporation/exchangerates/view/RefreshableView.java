package com.foxy_corporation.exchangerates.view;

import java.util.ArrayList;

/**
 * Created by Vlad on 14.05.2017.
 */

public interface RefreshableView {
    void dataLoadSuccess(ArrayList<String> list);

    void dataLoadError(String message);

    void needToRefreshData();
}

