package com.rba.chartdemo.util.control.spinner;

import android.content.Context;

import java.util.List;

/**
 * Created by Ricardo Bravo on 6/01/17.
 */


public class CustomSpinnerAdapter<T> extends CustomSpinnerBaseAdapter {

    private final List<T> mItems;

    public CustomSpinnerAdapter(Context context, List<T> items, int textColor, int backgroundSelector) {
        super(context, textColor, backgroundSelector);
        mItems = items;
    }

    @Override
    public int getCount() {
        if(mItems.size()==0){
            return  0;
        }else{
            return mItems.size() - 1;
        }
    }

    @Override
    public T getItem(int position) {

        /*
        if(position == mItems.size()){
            return mItems.get(position);
        }else{
            if (position > mSelectedIndex) {
                return mItems.get(position + 1);
            } else {
                return mItems.get(position);
            }
        }
        */
        if (position >= mSelectedIndex) {
            return mItems.get(position + 1);
        } else {
            return mItems.get(position);
        }

    }

    @Override
    public T getItemInDataset(int position) {

        if(mItems.size()>0){
            return mItems.get(position);
        }else{
            return null;
        }

    }
}
