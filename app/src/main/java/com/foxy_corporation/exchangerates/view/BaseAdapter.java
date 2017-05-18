package com.foxy_corporation.exchangerates.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 14.05.2017.
 */

public abstract class BaseAdapter<String> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected ArrayList<String> baseAdapterList = new ArrayList<>();

    public BaseAdapter(ArrayList<String> list) {
        this.baseAdapterList.addAll(list);
    }

    public ArrayList<String> getBaseAdapterList() {
        return baseAdapterList;
    }

    @Override
    public int getItemCount() {
        return baseAdapterList.size();
    }

    public void setBaseAdapterList(ArrayList<String> list) {
        /*Log.d("BaseAdapter...", "setBaseAdapterList");*/

        this.baseAdapterList.clear();
        this.baseAdapterList.addAll(list);

        notifyDataSetChanged();
    }

    protected class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(View v) {
            super(v);
        }
    }
}
