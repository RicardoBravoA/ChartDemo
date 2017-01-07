package com.rba.chartdemo.util.control.spinner;

import android.content.Context;
import android.widget.ListAdapter;

/**
 * Created by Ricardo Bravo on 6/01/17.
 */

public class CustomSpinnerAdapterWrapper extends CustomSpinnerBaseAdapter {

    private final ListAdapter mBaseAdapter;

    public CustomSpinnerAdapterWrapper(Context context, ListAdapter toWrap, int textColor, int backgroundSelector) {
        super(context, textColor, backgroundSelector);
        mBaseAdapter = toWrap;
    }

    @Override
    public int getCount() {
        return mBaseAdapter.getCount() - 1;
    }

    @Override
    public Object getItem(int position) {

        if(position == mSelectedIndex){
            return mBaseAdapter.getItem(position);
        }else{
            if (position > mSelectedIndex) {
                return mBaseAdapter.getItem(position + 1);
            } else {
                return mBaseAdapter.getItem(position);
            }
        }

    }

    @Override
    public Object getItemInDataset(int position) {
        return mBaseAdapter.getItem(position);
    }
}
