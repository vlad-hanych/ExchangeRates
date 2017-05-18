package com.foxy_corporation.exchangerates.view.exchange_rates_main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.foxy_corporation.exchangerates.R;
import com.foxy_corporation.exchangerates.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Vlad on 15.05.2017.
 */

public class ExchangeRatesActivity extends AppCompatActivity {
    private PagerAdapter mPagerAdapter;

    private FragmentManager mSupportFragmentManager;

    @BindView(R.id.tabLayTabs_AER)
    TabLayout mTabLayTabs;

    @BindView(R.id.viewPagPager_AER)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Log.d("ExchangeRatesActivity", "onCreate");*/

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_exchange_rates);

        mSupportFragmentManager = getSupportFragmentManager();

        ButterKnife.bind(this);

        mTabLayTabs.setupWithViewPager(mViewPager);

        Intent exchangeRatesActivityIntent = getIntent();

        ArrayList<String> currenciesList = exchangeRatesActivityIntent.
                getStringArrayListExtra(Constants.CURRENCIES_ARRAY_LIST);

        /*Log.d("ExchangeRatesActivity...currencies count", String.valueOf(currenciesList.size()));*/

        if (mPagerAdapter == null)
            mPagerAdapter = new PagerAdapter(mSupportFragmentManager, currenciesList);
        else
            mPagerAdapter.setTabsList(currenciesList);

        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.setCurrentItem
                (exchangeRatesActivityIntent.getIntExtra(Constants.NEEDED_POSITION_KEY, 0));
    }
}
