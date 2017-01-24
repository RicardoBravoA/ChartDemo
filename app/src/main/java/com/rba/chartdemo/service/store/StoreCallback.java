package com.rba.chartdemo.service.store;

import com.rba.chartdemo.api.NetworkError;
import com.rba.chartdemo.model.response.ErrorResponse;
import com.rba.chartdemo.model.response.StoreResponse;

/**
 * Created by Ricardo Bravo on 24/01/17.
 */

public interface StoreCallback {

    void onStoreResponse(StoreResponse storeResponse);

    void onStoreError(ErrorResponse errorResponse);

    void onStoreFailure(NetworkError networkError);

}

