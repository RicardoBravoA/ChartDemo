package com.rba.chartdemo.util.mpchart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.rba.chartdemo.util.Util;

/**
 * Created by Ricardo Bravo on 17/01/17.
 */

public class MyValueFormatter implements IAxisValueFormatter {


    public MyValueFormatter() {
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return Util.format2Decimals(value);
    }
}