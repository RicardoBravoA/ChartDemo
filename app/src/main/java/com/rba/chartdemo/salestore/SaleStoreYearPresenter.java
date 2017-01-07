package com.rba.chartdemo.salestore;

import com.rba.chartdemo.api.NetworkError;
import com.rba.chartdemo.model.response.ErrorResponse;
import com.rba.chartdemo.model.response.StoreYearResponse;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ricardobravo on 13/12/16.
 */

public class SaleStoreYearPresenter {

    private SaleStoreYearInteractor saleStoreYearInteractor;
    private SaleStoreYearView saleStoreYearView;
    private CompositeSubscription subscription;

    public SaleStoreYearPresenter(SaleStoreYearInteractor saleStoreYearInteractor, SaleStoreYearView saleStoreYearView){
        this.saleStoreYearInteractor = saleStoreYearInteractor;
        this.saleStoreYearView = saleStoreYearView;
    }

    public void load(int id) {
        this.subscription = new CompositeSubscription();

        Subscription subscription = saleStoreYearInteractor.getSaleStoreYear(id, new SaleStoreYearCallback() {
            @Override
            public void onResponse(StoreYearResponse storeYearResponse) {
                saleStoreYearView.showStoreYear(storeYearResponse);
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                saleStoreYearView.showErrorMessage(errorResponse.get_meta().getStatus());
            }

            @Override
            public void onFailure(NetworkError networkError) {
                saleStoreYearView.showErrorMessage(networkError.getMessage());
            }
        });

        this.subscription.add(subscription);

    }

    public void cancel() {
        subscription.unsubscribe();
    }

}
