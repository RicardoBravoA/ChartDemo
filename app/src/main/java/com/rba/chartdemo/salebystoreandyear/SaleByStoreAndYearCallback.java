package com.rba.chartdemo.salebystoreandyear;

import com.rba.chartdemo.api.NetworkError;
import com.rba.chartdemo.model.response.ErrorResponse;
import com.rba.chartdemo.model.response.SaleByStoreAndYearResponse;

/**
 * Created by Ricardo Bravo on 17/01/17.
 */

public interface SaleByStoreAndYearCallback {

    void onResponse(SaleByStoreAndYearResponse saleByStoreAndYearResponse);

    void onError(ErrorResponse errorResponse);

    void onFailure(NetworkError networkError);

}
