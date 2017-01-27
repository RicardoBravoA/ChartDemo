package com.rba.chartdemo.salestorebybranchoffice;

import android.util.Log;

import com.google.gson.Gson;
import com.rba.chartdemo.api.ApiService;
import com.rba.chartdemo.api.ErrorUtil;
import com.rba.chartdemo.api.NetworkError;
import com.rba.chartdemo.model.response.BranchStoreResponse;
import com.rba.chartdemo.model.response.ErrorResponse;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Ricardo Bravo on 26/01/17.
 */

public class SaleStoreBranchOfficeInteractor {

    private ApiService apiService;

    public SaleStoreBranchOfficeInteractor(ApiService apiService) {
        this.apiService = apiService;
    }

    public Subscription getSaleStoreBranchOffice(final SaleStoreBranchOfficeCallback saleStoreBranchOfficeCallback){

        return  apiService.getBranchStoreYear()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends BranchStoreResponse>>() {
                    @Override
                    public Observable<? extends BranchStoreResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                }).subscribe(new Subscriber<BranchStoreResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            HttpException exception = (HttpException) e;
                            Response response = exception.response();
                            ErrorResponse errorResponse = ErrorUtil.parseError(response);
                            saleStoreBranchOfficeCallback.onError(errorResponse);
                        } else {
                            saleStoreBranchOfficeCallback.onFailure(new NetworkError(e));
                        }
                    }

                    @Override
                    public void onNext(BranchStoreResponse branchStoreResponse) {
                        Log.i("z- onNext", new Gson().toJson(branchStoreResponse));
                        saleStoreBranchOfficeCallback.onResponse(branchStoreResponse);
                    }
                });

    }

}
