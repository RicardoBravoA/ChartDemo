package com.rba.chartdemo.salestore;

import com.rba.chartdemo.api.NetworkError;
import com.rba.chartdemo.model.response.ErrorResponse;
import com.rba.chartdemo.model.response.StoreYearResponse;

/**
 * Created by ricardobravo on 13/12/16.
 */

public interface SaleStoreYearCallback {

    void onResponse(StoreYearResponse storeYearResponse);

    void onError(ErrorResponse errorResponse);

    void onFailure(NetworkError networkError);

}
