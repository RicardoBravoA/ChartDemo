package com.rba.chartdemo.di;

import com.rba.chartdemo.api.NetworkModule;
import com.rba.chartdemo.salestore.SaleStoreYearActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ricardobravo on 13/12/16.
 */

@Singleton
@Component(modules = NetworkModule.class)
public interface ChartComponent {

    void injectSaleStoreYear(SaleStoreYearActivity saleStoreYearActivity);

}
