package com.rba.chartdemo.storesale;

import com.rba.chartdemo.api.NetworkError;
import com.rba.chartdemo.model.response.ErrorResponse;
import com.rba.chartdemo.model.response.SaleByStoreAndYearResponse;
import com.rba.chartdemo.model.response.StoreYearResponse;
import com.rba.chartdemo.salestore.SaleStoreYearCallback;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Ricardo Bravo on 24/01/17.
 */

public class StoreSalePresenter {

    private StoreSaleInteractor storeSaleInteractor;
    private StoreSaleView storeSaleView;
    private CompositeSubscription subscription;

    public StoreSalePresenter(StoreSaleInteractor storeSaleInteractor, StoreSaleView storeSaleView){
        this.storeSaleInteractor = storeSaleInteractor;
        this.storeSaleView = storeSaleView;
    }

    public void load(int id) {
        this.subscription = new CompositeSubscription();

        Subscription subscription = storeSaleInteractor.getSaleStoreYear(id, new StoreSaleCallback() {
            @Override
            public void onResponse(StoreYearResponse storeYearResponse) {
                storeSaleView.showStoreYear(storeYearResponse);
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                storeSaleView.showErrorMessage(errorResponse.get_meta().getStatus());
            }

            @Override
            public void onFailure(NetworkError networkError) {
                storeSaleView.showErrorMessage(networkError.getMessage());
            }
        });

        this.subscription.add(subscription);

    }

    public void cancel() {
        subscription.unsubscribe();
    }

}