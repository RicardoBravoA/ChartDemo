package com.rba.chartdemo.di;

import com.rba.chartdemo.api.NetworkModule;
import com.rba.chartdemo.salebystoreandyear.SaleByStoreAndYearActivity;
import com.rba.chartdemo.salestore.ChartFragment;
import com.rba.chartdemo.salestore.LineFragment;
import com.rba.chartdemo.salestore.SaleStoreYearActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ricardobravo on 13/12/16.
 */

@Singleton
@Component(modules = NetworkModule.class)
public interface ChartComponent {

    void injectSaleStoreYear(ChartFragment chartFragment);
    void injectSaleStoreYear(LineFragment lineFragment);

    void injectSaleByStoreAndYear(SaleByStoreAndYearActivity saleByStoreAndYearActivity);

}
