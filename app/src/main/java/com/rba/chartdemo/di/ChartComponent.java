package com.rba.chartdemo.di;

import com.rba.chartdemo.api.NetworkModule;
import com.rba.chartdemo.salebystoreandyear.SaleByStoreAndYearActivity;
import com.rba.chartdemo.salestore.ChartFragment;
import com.rba.chartdemo.salestore.LineFragment;
import com.rba.chartdemo.salestore.SaleStoreYearActivity;
import com.rba.chartdemo.storesale.StoreSaleChartFragment;
import com.rba.chartdemo.storesale.StoreSaleLineFragment;

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

    void injectStoreSale(StoreSaleChartFragment storeSaleChartFragment);
    void injectStoreSale(StoreSaleLineFragment storeSaleLineFragment);

    void injectSaleByStoreAndYear(SaleByStoreAndYearActivity saleByStoreAndYearActivity);

}
