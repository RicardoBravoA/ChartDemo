package com.rba.chartdemo.salestore;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.google.gson.Gson;
import com.rba.chartdemo.R;
import com.rba.chartdemo.base.BaseActivity;
import com.rba.chartdemo.model.response.StoreYearResponse;
import com.rba.chartdemo.model.response.YearResponse;
import com.rba.chartdemo.service.year.YearInteractor;
import com.rba.chartdemo.service.year.YearPresenter;
import com.rba.chartdemo.util.control.spinner.CustomSpinner;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

public class SaleStoreYearActivity extends BaseActivity implements SaleStoreYearView {

    private SaleStoreYearPresenter saleStoreYearPresenter;
    private YearPresenter yearPresenter;
    @Inject
    SaleStoreYearInteractor saleStoreYearInteractor;
    @Inject
    YearInteractor yearInteractor;
    private List<YearResponse> yearResponseList;


    @BindView(R.id.clGeneral) CoordinatorLayout clGeneral;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.spYear) CustomSpinner spYear;
    @BindView(R.id.pchSaleStore) PieChart pchSaleStore;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getChartComponent().injectSaleStoreYear(this);
        setContentView(R.layout.activity_sale_store_year);

        init();

    }

    @Override
    public void init() {

        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        toolbar.setTitle(getString(R.string.title_activity_sale_store_year));
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        saleStoreYearPresenter = new SaleStoreYearPresenter(saleStoreYearInteractor, this);
        yearPresenter = new YearPresenter(yearInteractor, this);
        yearPresenter.loadYear();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if(menuItem.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void showYear(YearResponse yearResponse) {
        Log.i("z- showYear", new Gson().toJson(yearResponse));


        spYear.setDataSource(yearResponse.getData());
    }

    @Override
    public void showErrorYear(String message) {
        Log.i("z- showErrorYear", message);
    }

    @Override
    public void showErrorMessage(String message) {
        Log.i("z- showErrorMessage", message);
        Snackbar.make(clGeneral, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showStoreYear(StoreYearResponse storeYearResponse) {
        Log.i("z- showStoreYear", new Gson().toJson(storeYearResponse));
    }


}
