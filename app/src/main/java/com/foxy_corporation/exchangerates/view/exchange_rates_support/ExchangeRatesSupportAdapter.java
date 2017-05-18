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
        ((ExchangeRatesViewHolder) holder).text.setText(baseAdapterList.get(position));
    }


    private class ExchangeRatesViewHolder extends BaseViewHolder {
        private TextView text;

        private ExchangeRatesViewHolder(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
