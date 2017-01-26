package com.rba.chartdemo.salestorebybranchoffice;

import com.rba.chartdemo.api.NetworkError;
import com.rba.chartdemo.model.response.BranchStoreResponse;
import com.rba.chartdemo.model.response.ErrorResponse;

/**
 * Created by Ricardo Bravo on 26/01/17.
 */

public interface SaleStoreBranchOfficeCallback {

    void onResponse(BranchStoreResponse branchStoreResponse);

    void onError(ErrorResponse errorResponse);

    void onFailure(NetworkError networkError);

}
