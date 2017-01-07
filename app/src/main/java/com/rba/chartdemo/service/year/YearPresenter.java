package com.rba.chartdemo.service.year;

import com.rba.chartdemo.api.NetworkError;
import com.rba.chartdemo.model.response.ErrorResponse;
import com.rba.chartdemo.model.response.YearResponse;
import com.rba.chartdemo.salestore.SaleStoreYearView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ricardobravo on 13/12/16.
 */

public class YearPresenter {

    private YearInteractor yearInteractor;
    private SaleStoreYearView saleStoreYearView;
    private CompositeSubscription subscription;

    public YearPresenter(YearInteractor yearInteractor, SaleStoreYearView saleStoreYearView){
        this.yearInteractor = yearInteractor;
        this.saleStoreYearView = saleStoreYearView;
    }

    public void loadYear() {
        this.subscription = new CompositeSubscription();

        Subscription subscription = yearInteractor.getYear(new YearCallback() {
            @Override
            public void onResponse(YearResponse yearResponse) {
                saleStoreYearView.showYear(yearResponse);
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                saleStoreYearView.showErrorYear(errorResponse.get_meta().getStatus());
            }

            @Override
            public void onFailure(NetworkError networkError) {
                saleStoreYearView.showErrorYear(networkError.getMessage());
            }
        });

        this.subscription.add(subscription);

    }

    public void cancel() {
        subscription.unsubscribe();
    }

}
