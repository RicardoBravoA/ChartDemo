package com.rba.chartdemo.component;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.rba.chartdemo.R;

/**
 * Created by Ricardo Bravo on 24/01/17.
 */

public class ToolTip extends MarkerView {

    private TextView lblMarker;

    public ToolTip(Context context, int layoutResource) {
        super(context, layoutResource);

        lblMarker = (TextView) findViewById(R.id.lblMarker);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

            lblMarker.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
        } else {

            lblMarker.setText("" + Utils.formatNumber(e.getY(), 0, true));
        }

        //lblMarker.setText(e.getData().toString());

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

}

