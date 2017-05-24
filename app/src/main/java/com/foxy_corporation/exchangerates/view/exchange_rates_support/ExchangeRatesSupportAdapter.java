package com.foxy_corporation.exchangerates.view.exchange_rates_support;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foxy_corporation.exchangerates.R;
import com.foxy_corporation.exchangerates.view.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vlad on 15.05.2017.
 */

class ExchangeRatesSupportAdapter extends BaseAdapter<String> {
    ExchangeRatesSupportAdapter(ArrayList<String> list) {
        super(list);
    }

    @Override
    public ExchangeRatesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exchange_rates, parent, false);

        return new ExchangeRatesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ExchangeRatesViewHolder) holder).mText.setText(baseAdapterList.get(position));
    }


    class ExchangeRatesViewHolder extends BaseViewHolder {
        @BindView(R.id.textView)
        TextView mText;

        private ExchangeRatesViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
