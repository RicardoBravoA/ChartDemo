package com.rba.chartdemo.storesale;

import com.rba.chartdemo.model.response.StoreResponse;
import com.rba.chartdemo.model.response.StoreYearResponse;

/**
 * Created by Ricardo Bravo on 24/01/17.
 */

public interface StoreSaleView {

    void init();

    void showErrorMessage(String message);

    void showStoreYear(StoreYearResponse storeYearResponse);

    void showStore(StoreResponse storeResponse);

    void showErrorStore(String message);


}
