package com.rba.chartdemo.service.store;

import com.rba.chartdemo.api.NetworkError;
import com.rba.chartdemo.model.response.ErrorResponse;
import com.rba.chartdemo.model.response.StoreResponse;
import com.rba.chartdemo.storesale.StoreSaleView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Ricardo Bravo on 24/01/17.
 */

public class StorePresenter {

    private StoreInteractor storeInteractor;
    private StoreSaleView storeSaleView;
    private CompositeSubscription subscription;

    public StorePresenter(StoreInteractor storeInteractor, StoreSaleView storeSaleView){
        this.storeInteractor = storeInteractor;
        this.storeSaleView = storeSaleView;
    }

    public void loadYear() {
        this.subscription = new CompositeSubscription();

        Subscription subscription = storeInteractor.getStore(new StoreCallback() {
            @Override
            public void onStoreResponse(StoreResponse storeResponse) {
                storeSaleView.showStore(storeResponse);
            }

            @Override
            public void onStoreError(ErrorResponse errorResponse) {
                storeSaleView.showErrorStore(errorResponse.get_meta().getStatus());
            }

            @Override
            public void onStoreFailure(NetworkError networkError) {
                storeSaleView.showErrorStore(networkError.getMessage());
            }
        });

        this.subscription.add(subscription);

    }

    public void cancel() {
        subscription.unsubscribe();
    }

}
