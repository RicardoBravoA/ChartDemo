package com.rba.chartdemo.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rba.chartdemo.api.NetworkModule;
import com.rba.chartdemo.di.ChartComponent;
import com.rba.chartdemo.di.DaggerChartComponent;

/**
 * Created by ricardobravo on 13/12/16.
 */

public class BaseActivity extends AppCompatActivity {

    private ChartComponent chartComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chartComponent = DaggerChartComponent.builder().networkModule(new NetworkModule()).build();
    }

    public ChartComponent getChartComponent() {
        return chartComponent;
    }


}
