package com.rba.chartdemo.storesale;

import com.rba.chartdemo.api.NetworkError;
import com.rba.chartdemo.model.response.ErrorResponse;
import com.rba.chartdemo.model.response.StoreYearResponse;

/**
 * Created by Ricardo Bravo on 24/01/17.
 */

public interface StoreSaleCallback {

    void onResponse(StoreYearResponse storeYearResponse);

    void onError(ErrorResponse errorResponse);

    void onFailure(NetworkError networkError);

}
