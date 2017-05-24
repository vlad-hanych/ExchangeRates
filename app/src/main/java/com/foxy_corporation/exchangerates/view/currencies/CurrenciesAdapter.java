package com.foxy_corporation.exchangerates.view.currencies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foxy_corporation.exchangerates.R;
import com.foxy_corporation.exchangerates.presenter.currencies.CurrenciesPresenter;
import com.foxy_corporation.exchangerates.view.BaseAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vlad on 15.05.2017.
 */

class CurrenciesAdapter extends BaseAdapter<String> {

    private CurrenciesPresenter mCurrenciesPresenter;

    private ArrayList<String> mDownloadedList = new ArrayList<>();

    CurrenciesAdapter(ArrayList<String> arrayList, CurrenciesPresenter presenter) {
        super(arrayList);

        mDownloadedList.addAll(super.baseAdapterList);

        this.mCurrenciesPresenter = presenter;
    }

    @Override
    public CurrenciesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_currencies, parent, false);

        return new CurrenciesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CurrenciesViewHolder myCurrenciesViewHolder = (CurrenciesViewHolder) holder;

        myCurrenciesViewHolder.mText.setText(baseAdapterList.get(position));

    }

    void setDownloadedCurrenciesList(ArrayList<String> list) {
        mDownloadedList.clear();
        mDownloadedList.addAll(list);

        notifyDataSetChanged();
    }

    ArrayList<String> getDownloadedCurrenciesList() {
        return mDownloadedList;
    }

    void filter(String text) {
        baseAdapterList.clear();
        if (text.isEmpty()) {
            baseAdapterList.addAll(mDownloadedList);
        } else {
            text = text.toLowerCase();
            for (String item : mDownloadedList) {
                if (item.toLowerCase().contains(text) || item.toLowerCase().contains(text)) {
                    baseAdapterList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }


    class CurrenciesViewHolder extends BaseViewHolder implements View.OnClickListener {
        @BindView(R.id.textView)
        TextView mText;

        private CurrenciesViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            mCurrenciesPresenter.clickCurrency(baseAdapterList.get(position), baseAdapterList, position);
        }
    }
}
