package com.rba.chartdemo.app;

import android.app.Application;

import com.rba.chartdemo.di.ChartComponent;
import com.rba.chartdemo.di.DaggerChartComponent;

/**
 * Created by ricardobravo on 13/12/16.
 */

public class ChartApplication extends Application {

    private ChartComponent chartComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        chartComponent = DaggerChartComponent.create();
    }

    public ChartComponent getChartComponent() {
        return chartComponent;
    }

}
