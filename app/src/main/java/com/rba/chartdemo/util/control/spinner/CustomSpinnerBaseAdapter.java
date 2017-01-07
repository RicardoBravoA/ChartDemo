package com.rba.chartdemo.util.control.spinner;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rba.chartdemo.R;

/**
 * Created by Ricardo Bravo on 6/01/17.
 */

abstract class CustomSpinnerBaseAdapter<T> extends BaseAdapter {
    private final Context mContext;
    int mSelectedIndex;
    private final int mTextColor;
    private final int mBackgroundSelector;

    CustomSpinnerBaseAdapter(Context context, int textColor, int backgroundSelector) {
        mContext = context;
        mTextColor = textColor;
        mBackgroundSelector = backgroundSelector;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.spinner_list_item, null);
            textView = (TextView) convertView.findViewById(R.id.tv_tinted_spinner);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                textView.setBackground(ContextCompat.getDrawable(mContext, mBackgroundSelector));
            }

            convertView.setTag(new ViewHolder(textView));
        } else {
            textView = ((ViewHolder) convertView.getTag()).textView;
        }

        textView.setText(getItem(position).toString());
        textView.setTextColor(mTextColor);

        return convertView;
    }

// --Commented out by Inspection START (4/08/16 3:55 PM):
//    public int getSelectedIndex() {
//        return mSelectedIndex;
//    }
// --Commented out by Inspection STOP (4/08/16 3:55 PM)

    public void notifyItemSelected(int index) {
        mSelectedIndex = index;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract T getItem(int position);

    @Override
    public abstract int getCount();

    public abstract T getItemInDataset(int position);

    static class ViewHolder {

        public final TextView textView;

        public ViewHolder(TextView textView) {
            this.textView = textView;
        }
    }
}