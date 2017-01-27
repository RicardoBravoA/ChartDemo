package com.rba.chartdemo.salestoreyear;

import com.rba.chartdemo.model.response.BranchStoreResponse;

/**
 * Created by ricardobravo on 13/12/16.
 */

public interface SaleStoreYearView {

    void init();

    void showErrorMessage(String message);

    void showBranchStore(BranchStoreResponse branchStoreResponse);

}
