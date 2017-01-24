package com.rba.chartdemo.storesale;

import com.rba.chartdemo.api.NetworkError;
import com.rba.chartdemo.model.response.ErrorResponse;
import com.rba.chartdemo.model.response.SaleByStoreAndYearResponse;

/**
 * Created by Ricardo Bravo on 24/01/17.
 */

public interface StoreSaleCallback {

    void onResponse(SaleByStoreAndYearResponse storeYearResponse);

    void onError(ErrorResponse errorResponse);

    void onFailure(NetworkError networkError);

}
