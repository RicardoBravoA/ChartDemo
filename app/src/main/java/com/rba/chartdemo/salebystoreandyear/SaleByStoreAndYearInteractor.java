package com.rba.chartdemo.salebystoreandyear;

import android.util.Log;

import com.google.gson.Gson;
import com.rba.chartdemo.api.ApiService;
import com.rba.chartdemo.api.ErrorUtil;
import com.rba.chartdemo.api.NetworkError;
import com.rba.chartdemo.model.response.ErrorResponse;
import com.rba.chartdemo.model.response.SaleByStoreAndYearResponse;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Ricardo Bravo on 17/01/17.
 */

public class SaleByStoreAndYearInteractor {

    private ApiService apiService;

    public SaleByStoreAndYearInteractor(ApiService apiService) {
        this.apiService = apiService;
    }

    public Subscription getSaleStoreYear(final SaleByStoreAndYearCallback callback){

        return  apiService.getSaleByStoreYear()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends SaleByStoreAndYearResponse>>() {
                    @Override
                    public Observable<? extends SaleByStoreAndYearResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                }).subscribe(new Subscriber<SaleByStoreAndYearResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            HttpException exception = (HttpException) e;
                            Response response = exception.response();
                            ErrorResponse errorResponse = ErrorUtil.parseError(response);
                            callback.onError(errorResponse);
                        } else {
                            callback.onFailure(new NetworkError(e));
                        }
                    }

                    @Override
                    public void onNext(SaleByStoreAndYearResponse saleByStoreAndYearResponse) {
                        Log.i("z- onNext", new Gson().toJson(saleByStoreAndYearResponse));
                        callback.onResponse(saleByStoreAndYearResponse);
                    }
                });

    }

}
