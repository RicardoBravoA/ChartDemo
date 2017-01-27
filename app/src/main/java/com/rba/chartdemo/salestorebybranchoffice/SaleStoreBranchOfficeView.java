package com.rba.chartdemo.salestorebybranchoffice;

import com.rba.chartdemo.model.response.BranchStoreResponse;

/**
 * Created by ricardobravo on 13/12/16.
 */

public interface SaleStoreBranchOfficeView {

    void init();

    void showErrorMessage(String message);

    void showBranchStore(BranchStoreResponse branchStoreResponse);

}
