package com.rba.chartdemo.salestore;

import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;

import com.github.mikephil.charting.charts.PieChart;
import com.rba.chartdemo.R;
import com.rba.chartdemo.base.BaseActivity;
import com.rba.chartdemo.model.response.YearResponse;
import com.rba.chartdemo.service.year.YearInteractor;
import com.rba.chartdemo.service.year.YearPresenter;

import java.util.List;

import javax.inject.Inject;

public class SaleStoreYearActivity extends BaseActivity implements SaleStoreYearView {

    private AppCompatSpinner spYear;
    private PieChart pchSaleStore;
    private SaleStoreYearPresenter saleStoreYearPresenter;
    private YearPresenter yearPresenter;
    @Inject
    SaleStoreYearInteractor saleStoreYearInteractor;
    @Inject
    YearInteractor yearInteractor;
    private List<YearResponse> yearResponseList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getChartComponent().injectSaleStoreYear(this);
        setContentView(R.layout.activity_sale_store_year);
        init();

    }

    @Override
    public void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spYear = (AppCompatSpinner) findViewById(R.id.spYear);
        pchSaleStore = (PieChart) findViewById(R.id.pchSaleStore);
        saleStoreYearPresenter = new SaleStoreYearPresenter(saleStoreYearInteractor, this);
        yearPresenter = new YearPresenter(yearInteractor, this);
        yearPresenter.loadYear(2014);


    }

    @Override
    public void showYear() {

    }

    @Override
    public void showErrorMessage(String message) {

    }
}
