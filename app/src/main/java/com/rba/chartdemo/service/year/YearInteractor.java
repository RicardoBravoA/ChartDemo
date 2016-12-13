package com.rba.chartdemo.service.year;

import android.util.Log;

import com.google.gson.Gson;
import com.rba.chartdemo.api.ApiService;
import com.rba.chartdemo.api.ErrorUtil;
import com.rba.chartdemo.api.NetworkError;
import com.rba.chartdemo.model.response.ErrorResponse;
import com.rba.chartdemo.model.response.YearResponse;

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

public class YearInteractor {

    private ApiService apiService;

    public YearInteractor(ApiService apiService) {
        this.apiService = apiService;
    }

    public Subscription getYear(final YearCallback yearCallback){

        return  apiService.getYear()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends YearResponse>>() {
                    @Override
                    public Observable<? extends YearResponse> call(Throwable throwable) {
                        Log.i("z- onErrorResumeNext", throwable.getMessage());
                        return Observable.error(throwable);
                    }
                }).subscribe(new Subscriber<YearResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            HttpException exception = (HttpException) e;
                            Response response = exception.response();
                            ErrorResponse errorResponse = ErrorUtil.parseError(response);
                            yearCallback.onError(errorResponse);
                        } else {
                            yearCallback.onFailure(new NetworkError(e));
                        }
                    }

                    @Override
                    public void onNext(YearResponse yearResponse) {
                        Log.i("z- onNext", new Gson().toJson(yearResponse));
                        yearCallback.onResponse(yearResponse);
                    }
                });

    }

}
