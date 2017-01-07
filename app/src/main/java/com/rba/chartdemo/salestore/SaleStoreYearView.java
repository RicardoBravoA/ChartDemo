package com.rba.chartdemo.salestore;

import com.rba.chartdemo.model.response.StoreYearResponse;
import com.rba.chartdemo.model.response.YearResponse;

/**
 * Created by ricardobravo on 13/12/16.
 */

public interface SaleStoreYearView {

    void init();

    void showErrorMessage(String message);

    void showStoreYear(StoreYearResponse storeYearResponse);

    void showYear(YearResponse yearResponse);

    void showErrorYear(String message);

}
