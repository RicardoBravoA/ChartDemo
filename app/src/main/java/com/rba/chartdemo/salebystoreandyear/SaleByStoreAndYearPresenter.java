package com.rba.chartdemo.salebystoreandyear;

import com.rba.chartdemo.api.NetworkError;
import com.rba.chartdemo.model.response.ErrorResponse;
import com.rba.chartdemo.model.response.SaleByStoreAndYearResponse;
import com.rba.chartdemo.model.response.StoreYearResponse;
import com.rba.chartdemo.salestore.SaleStoreYearCallback;
import com.rba.chartdemo.salestore.SaleStoreYearInteractor;
import com.rba.chartdemo.salestore.SaleStoreYearView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Ricardo Bravo on 17/01/17.
 */

public class SaleByStoreAndYearPresenter {

    private SaleByStoreAndYearInteractor saleByStoreAndYearInteractor;
    private SaleByStoreAndYearView saleByStoreAndYearView;
    private CompositeSubscription subscription;

    public SaleByStoreAndYearPresenter(SaleByStoreAndYearInteractor saleByStoreAndYearInteractor,
                                       SaleByStoreAndYearView saleByStoreAndYearView){
        this.saleByStoreAndYearInteractor = saleByStoreAndYearInteractor;
        this.saleByStoreAndYearView = saleByStoreAndYearView;
    }

    public void load(int id) {
        this.subscription = new CompositeSubscription();

        Subscription subscription = saleByStoreAndYearInteractor.getSaleStoreYear(new SaleByStoreAndYearCallback() {
            @Override
            public void onResponse(SaleByStoreAndYearResponse saleByStoreAndYearResponse) {
                saleByStoreAndYearView.showData(saleByStoreAndYearResponse);
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                saleByStoreAndYearView.showErrorMessage(errorResponse.get_meta().getStatus());
            }

            @Override
            public void onFailure(NetworkError networkError) {
                saleByStoreAndYearView.showErrorMessage(networkError.getMessage());
            }
        });

        this.subscription.add(subscription);

    }

    public void cancel() {
        subscription.unsubscribe();
    }

}
