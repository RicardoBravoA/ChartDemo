package com.rba.chartdemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.rba.chartdemo.api.NetworkModule;
import com.rba.chartdemo.di.ChartComponent;
import com.rba.chartdemo.di.DaggerChartComponent;

/**
 * Created by Ricardo Bravo on 18/01/17.
 */

public class BaseFragment extends Fragment {

    private ChartComponent chartComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chartComponent = DaggerChartComponent.builder().networkModule(new NetworkModule()).build();
    }

    public ChartComponent getChartComponent() {
        return chartComponent;
    }

}
