package com.rba.chartdemo.service.store;

import android.util.Log;

import com.google.gson.Gson;
import com.rba.chartdemo.api.ApiService;
import com.rba.chartdemo.api.ErrorUtil;
import com.rba.chartdemo.api.NetworkError;
import com.rba.chartdemo.model.response.ErrorResponse;
import com.rba.chartdemo.model.response.StoreResponse;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Ricardo Bravo on 24/01/17.
 */

public class StoreInteractor {

    private ApiService apiService;

    public StoreInteractor(ApiService apiService) {
        this.apiService = apiService;
    }

    public Subscription getStore(final StoreCallback storeCallback){

        return  apiService.getStore()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends StoreResponse>>() {
                    @Override
                    public Observable<? extends StoreResponse> call(Throwable throwable) {
                        Log.i("z- onErrorResumeNext", throwable.getMessage());
                        return Observable.error(throwable);
                    }
                }).subscribe(new Subscriber<StoreResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            HttpException exception = (HttpException) e;
                            Response response = exception.response();
                            ErrorResponse errorResponse = ErrorUtil.parseError(response);
                            storeCallback.onStoreError(errorResponse);
                        } else {
                            storeCallback.onStoreFailure(new NetworkError(e));
                        }
                    }

                    @Override
                    public void onNext(StoreResponse storeResponse) {
                        Log.i("z- onNext", new Gson().toJson(storeResponse));
                        storeCallback.onStoreResponse(storeResponse);
                    }
                });

    }

}
