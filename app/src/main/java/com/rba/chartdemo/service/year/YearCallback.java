package com.rba.chartdemo.service.year;

import com.rba.chartdemo.api.NetworkError;
import com.rba.chartdemo.model.response.ErrorResponse;
import com.rba.chartdemo.model.response.YearResponse;

/**
 * Created by ricardobravo on 13/12/16.
 */

public interface YearCallback {

    void onResponse(YearResponse yearResponse);

    void onError(ErrorResponse errorResponse);

    void onFailure(NetworkError networkError);

}
