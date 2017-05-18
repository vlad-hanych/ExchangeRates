package com.foxy_corporation.exchangerates.view;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Vlad on 17.05.2017.
 */

public class EmptyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }


    @Override
    public int getItemCount() {
        return 0;
    }
}
