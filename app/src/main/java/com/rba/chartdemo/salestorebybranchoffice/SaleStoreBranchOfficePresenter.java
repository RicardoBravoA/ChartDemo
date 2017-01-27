package com.rba.chartdemo.salestorebybranchoffice;

import com.rba.chartdemo.api.NetworkError;
import com.rba.chartdemo.model.response.BranchStoreResponse;
import com.rba.chartdemo.model.response.ErrorResponse;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Ricardo Bravo on 26/01/17.
 */

public class SaleStoreBranchOfficePresenter {

    private SaleStoreBranchOfficeInteractor saleStoreBranchOfficeInteractor;
    private SaleStoreBranchOfficeView saleStoreBranchOfficeView;
    private CompositeSubscription subscription;

    public SaleStoreBranchOfficePresenter(SaleStoreBranchOfficeInteractor saleStoreBranchOfficeInteractor,
                                          SaleStoreBranchOfficeView saleStoreBranchOfficeView){
        this.saleStoreBranchOfficeInteractor = saleStoreBranchOfficeInteractor;
        this.saleStoreBranchOfficeView = saleStoreBranchOfficeView;
    }

    public void load() {
        this.subscription = new CompositeSubscription();

        Subscription subscription = saleStoreBranchOfficeInteractor.getSaleStoreBranchOffice(new SaleStoreBranchOfficeCallback() {
            @Override
            public void onResponse(BranchStoreResponse branchStoreResponse) {
                saleStoreBranchOfficeView.showBranchStore(branchStoreResponse);
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                saleStoreBranchOfficeView.showErrorMessage(errorResponse.get_meta().getStatus());
            }

            @Override
            public void onFailure(NetworkError networkError) {
                saleStoreBranchOfficeView.showErrorMessage(networkError.getMessage());
            }
        });

        this.subscription.add(subscription);

    }

    public void cancel() {
        subscription.unsubscribe();
    }

}
