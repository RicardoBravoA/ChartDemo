package com.rba.chartdemo.component;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.rba.chartdemo.R;
import com.rba.chartdemo.model.response.BranchStoreResponse;
import com.rba.chartdemo.model.response.StoreYearResponse;
import com.rba.chartdemo.util.Util;

/**
 * Created by Ricardo Bravo on 25/01/17.
 */

public class ToolTipBarChart extends MarkerView {

    private TextView lblMarker;
    private StoreYearResponse storeYearResponse = null;
    private BranchStoreResponse branchStoreResponse = null;

    public ToolTipBarChart(Context context, StoreYearResponse storeYearResponse) {
        super(context, R.layout.tooltip_bar_chart);
        this.storeYearResponse = storeYearResponse;

        lblMarker = (TextView) findViewById(R.id.lblMarker);
    }

    public ToolTipBarChart(Context context, BranchStoreResponse branchStoreResponse) {
        super(context, R.layout.tooltip_bar_chart);
        this.branchStoreResponse = branchStoreResponse;

        lblMarker = (TextView) findViewById(R.id.lblMarker);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if(storeYearResponse != null){
            lblMarker.setText(storeYearResponse.getData().get(Util.format0DecimalsInt(e.getX()-1)).getStore_description()+" \n "+ Util.format0Decimals(e.getY()));
        }else if(branchStoreResponse != null){
            lblMarker.setText(branchStoreResponse.getData().get(Util.format0DecimalsInt(e.getX()-1)).getBranch().get(Util.format0DecimalsInt(e.getX()-1))+" \n "+ Util.format0Decimals(e.getY()));
        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}

