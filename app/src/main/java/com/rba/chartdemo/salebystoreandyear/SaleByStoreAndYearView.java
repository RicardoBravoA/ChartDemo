package com.rba.chartdemo.salebystoreandyear;

import com.rba.chartdemo.model.response.SaleByStoreAndYearResponse;

/**
 * Created by Ricardo Bravo on 16/01/17.
 */

public interface SaleByStoreAndYearView {

    void init();

    void showErrorMessage(String message);

    void showData(SaleByStoreAndYearResponse saleByStoreAndYearResponse);

    void showError(String message);

}
