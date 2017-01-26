package com.rba.chartdemo.salestorebybranchoffice;

import com.rba.chartdemo.model.response.StoreResponse;
import com.rba.chartdemo.model.response.StoreYearResponse;

/**
 * Created by ricardobravo on 13/12/16.
 */

public interface SaleStoreBranchView {

    void init();

    void showErrorMessage(String message);

    void showStoreYear(StoreYearResponse storeYearResponse);

    void showStore(StoreResponse storeResponse);

    void showErrorStore(String message);



}
