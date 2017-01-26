package com.rba.chartdemo.salestorebybranchoffice;

import com.rba.chartdemo.model.response.BranchStoreResponse;
import com.rba.chartdemo.model.response.YearResponse;

/**
 * Created by ricardobravo on 13/12/16.
 */

public interface SaleStoreBranchOfficeView {

    void init();

    void showErrorMessage(String message);

    void showBranchStore(BranchStoreResponse branchStoreResponse);

    void showYear(YearResponse yearResponse);

    void showErrorYear(String message);



}
