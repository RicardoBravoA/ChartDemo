package com.rba.chartdemo.api;

import com.rba.chartdemo.BuildConfig;
import com.rba.chartdemo.model.response.SaleByStoreAndYearResponse;
import com.rba.chartdemo.model.response.StoreResponse;
import com.rba.chartdemo.model.response.StoreYearResponse;
import com.rba.chartdemo.model.response.YearResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by ricardobravo on 13/12/16.
 */

public interface ApiService {

    @GET(BuildConfig.URL_YEAR)
    Observable<YearResponse> getYear();

    @GET(BuildConfig.URL_STORE)
    Observable<StoreResponse> getStore();

    @GET(BuildConfig.URL_STORE_YEAR)
    Observable<StoreYearResponse> getStoreYear(@Path("id") int id);

    @GET(BuildConfig.URL_SALE_STORE_YEAR)
    Observable<SaleByStoreAndYearResponse> getSaleByStoreYear();

}
