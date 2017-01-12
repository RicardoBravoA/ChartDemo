package com.rba.chartdemo.salestore;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.rba.chartdemo.R;
import com.rba.chartdemo.base.BaseActivity;
import com.rba.chartdemo.model.response.StoreYearResponse;
import com.rba.chartdemo.model.response.YearResponse;
import com.rba.chartdemo.service.year.YearInteractor;
import com.rba.chartdemo.service.year.YearPresenter;
import com.rba.chartdemo.util.control.spinner.CustomSpinner;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SaleStoreYearActivity extends BaseActivity implements SaleStoreYearView,
        AdapterView.OnItemSelectedListener {

    private SaleStoreYearPresenter saleStoreYearPresenter;
    private YearPresenter yearPresenter;
    @Inject
    SaleStoreYearInteractor saleStoreYearInteractor;
    @Inject
    YearInteractor yearInteractor;
    private YearResponse yearResponse;

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

        toolbar.setTitle(getString(R.string.sale_year));
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

        spYear.setOnItemSelectedListener(this);
        saleStoreYearPresenter = new SaleStoreYearPresenter(saleStoreYearInteractor, this);
        yearPresenter = new YearPresenter(yearInteractor, this);
        yearPresenter.loadYear();


        pchSaleStore.setUsePercentValues(false);
        pchSaleStore.getDescription().setEnabled(false);
        pchSaleStore.setExtraOffsets(5, 10, 5, 5);

        pchSaleStore.setDragDecelerationFrictionCoef(0.95f);

        pchSaleStore.setDrawHoleEnabled(true);
        pchSaleStore.setHoleColor(Color.WHITE);

        pchSaleStore.setTransparentCircleColor(Color.WHITE);
        pchSaleStore.setTransparentCircleAlpha(110);

        pchSaleStore.setHoleRadius(40f);
        pchSaleStore.setTransparentCircleRadius(45f);

        pchSaleStore.setDrawCenterText(true);

        pchSaleStore.setRotationAngle(0);
        pchSaleStore.setRotationEnabled(true);
        pchSaleStore.setHighlightPerTapEnabled(true);


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
        this.yearResponse = yearResponse;

        spYear.setDataSource(yearResponse.getData());

        saleStoreYearPresenter.load(
                yearResponse.getData().get(spYear.getSelectedIndex()).getYear_sale());
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

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        for(StoreYearResponse.DataBean dataBean : storeYearResponse.getData()){
            entries.add(new PieEntry(Float.parseFloat(dataBean.getAmount()),
                    dataBean.getStore_description()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        pchSaleStore.setData(data);

        pchSaleStore.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        pchSaleStore.setEntryLabelColor(ContextCompat.getColor(this, R.color.plumb));
        pchSaleStore.setEntryLabelTextSize(12f);

        pchSaleStore.invalidate();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i("z- onItemSelected", String.valueOf(yearResponse.getData().get(i).getYear_sale()));

        saleStoreYearPresenter.load(yearResponse.getData().get(i).getYear_sale());

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.i("z- onNothingSelected", "true");
    }

}
