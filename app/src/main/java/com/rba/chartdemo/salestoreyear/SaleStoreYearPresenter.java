package com.rba.chartdemo.salestoreyear;

import com.rba.chartdemo.api.NetworkError;
import com.rba.chartdemo.model.response.BranchStoreResponse;
import com.rba.chartdemo.model.response.ErrorResponse;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Ricardo Bravo on 26/01/17.
 */

public class SaleStoreYearPresenter {

    private SaleStoreYearInteractor saleStoreYearInteractor;
    private SaleStoreYearView saleStoreYearView;
    private CompositeSubscription subscription;

    public SaleStoreYearPresenter(SaleStoreYearInteractor saleStoreYearInteractor,
                                  SaleStoreYearView saleStoreYearView){
        this.saleStoreYearInteractor = saleStoreYearInteractor;
        this.saleStoreYearView = saleStoreYearView;
    }

    public void load() {
        this.subscription = new CompositeSubscription();

        Subscription subscription = saleStoreYearInteractor.getSaleStoreBranchOffice(new SaleStoreYearCallback() {
            @Override
            public void onResponse(BranchStoreResponse branchStoreResponse) {
                saleStoreYearView.showBranchStore(branchStoreResponse);
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
