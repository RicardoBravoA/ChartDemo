package com.rba.chartdemo.di;

import com.rba.chartdemo.api.NetworkModule;
import com.rba.chartdemo.salebystoreandyear.SaleByStoreAndYearActivity;
import com.rba.chartdemo.salestore.SaleStoreChartFragment;
import com.rba.chartdemo.salestore.SaleStoreBarFragment;
import com.rba.chartdemo.salestoreyear.SaleStoreYearBarFragment;
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

    void injectSaleStoreYear(SaleStoreChartFragment saleStoreChartFragment);
    void injectSaleStoreYear(SaleStoreBarFragment saleStoreBarFragment);

    void injectStoreSale(StoreSaleChartFragment storeSaleChartFragment);
    void injectStoreSale(StoreSaleLineFragment storeSaleLineFragment);

    void injectSaleByStoreAndYear(SaleByStoreAndYearActivity saleByStoreAndYearActivity);
    void injectSaleStoreBranchOffice(SaleStoreYearBarFragment saleStoreYearBarFragment);

}
