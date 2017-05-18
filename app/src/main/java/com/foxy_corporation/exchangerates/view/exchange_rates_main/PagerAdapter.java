package com.foxy_corporation.exchangerates.view.exchange_rates_main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.foxy_corporation.exchangerates.utils.Constants;
import com.foxy_corporation.exchangerates.view.exchange_rates_support.ExchangeRatesFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 16.05.2017.
 */

class PagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<String> mTabsList = new ArrayList<String> ();

    PagerAdapter(FragmentManager fm, ArrayList<String> list) {
        super(fm);

        this.mTabsList.addAll(list);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabsList.get(position);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        /*Log.d("PagerAdapter...getItemgetItem", String.valueOf(position));*/

        Fragment myFragment = new ExchangeRatesFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Constants.BASE_CURRENCY_NAME_KEY, mTabsList.get(position));
        myFragment.setArguments(bundle);

        return myFragment;
    }

    @Override
    public int getCount() {
        return mTabsList.size();
    }

    void setTabsList(ArrayList<String> list) {
        /*Log.d("setTabsList", String.valueOf(list));*/

        mTabsList.clear();
        mTabsList.addAll(list);

        notifyDataSetChanged();
    }
}
