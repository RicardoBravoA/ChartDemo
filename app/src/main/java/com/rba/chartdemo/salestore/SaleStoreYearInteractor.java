package com.rba.chartdemo.salestore;

import android.util.Log;

import com.google.gson.Gson;
import com.rba.chartdemo.api.ApiService;
import com.rba.chartdemo.api.ErrorUtil;
import com.rba.chartdemo.api.NetworkError;
import com.rba.chartdemo.model.response.ErrorResponse;
import com.rba.chartdemo.model.response.StoreYearResponse;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ricardobravo on 13/12/16.
 */

public class SaleStoreYearInteractor {

    private ApiService apiService;

    public SaleStoreYearInteractor(ApiService apiService) {
        this.apiService = apiService;
    }

    public Subscription getSaleStoreYear(int id, final SaleStoreYearCallback saleStoreYearCallback){

        return  apiService.getStoreYear(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends StoreYearResponse>>() {
                    @Override
                    public Observable<? extends StoreYearResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                }).subscribe(new Subscriber<StoreYearResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            HttpException exception = (HttpException) e;
                            Response response = exception.response();
                            ErrorResponse errorResponse = ErrorUtil.parseError(response);
                            saleStoreYearCallback.onError(errorResponse);
                        } else {
                            saleStoreYearCallback.onFailure(new NetworkError(e));
                        }
                    }

                    @Override
                    public void onNext(StoreYearResponse storeYearResponse) {
                        Log.i("z- onNext", new Gson().toJson(storeYearResponse));
                        saleStoreYearCallback.onResponse(storeYearResponse);
                    }
                });

    }

}
