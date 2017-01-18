package com.rba.chartdemo.salebystoreandyear;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.rba.chartdemo.R;
import com.rba.chartdemo.base.BaseActivity;
import com.rba.chartdemo.model.response.SaleByStoreAndYearResponse;
import com.rba.chartdemo.util.mpchart.MyValueFormatter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SaleByStoreAndYearActivity extends BaseActivity implements
        SaleByStoreAndYearView {

    @BindView(R.id.lchSale) LineChart lchSale;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.clGeneral) CoordinatorLayout clGeneral;

    private SaleByStoreAndYearPresenter saleByStoreAndYearPresenter;
    @Inject
    SaleByStoreAndYearInteractor saleByStoreAndYearInteractor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getChartComponent().injectSaleByStoreAndYear(this);
        setContentView(R.layout.activity_sale_by_store_and_year);

        init();
    }

    @Override
    public void init() {
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        toolbar.setTitle(getString(R.string.sale_store_year));
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


        lchSale.setDrawGridBackground(false);
        lchSale.setTouchEnabled(true);
        lchSale.getDescription().setEnabled(false);

        lchSale.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        lchSale.setDragEnabled(false);
        lchSale.setScaleEnabled(false);
        lchSale.setDrawGridBackground(false);
        lchSale.setHighlightPerDragEnabled(true);

        XAxis xAxis = lchSale.getXAxis();
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.RED);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new MyValueFormatter());
        xAxis.setDrawAxisLine(true);


        /*
        YAxis leftAxis = lchSale.getAxisLeft();
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaximum(200f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = lchSale.getAxisRight();
        rightAxis.setTextColor(Color.RED);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);
        */


        lchSale.animateX(750);

        saleByStoreAndYearPresenter = new SaleByStoreAndYearPresenter(saleByStoreAndYearInteractor, this);
        saleByStoreAndYearPresenter.load();

    }

    @Override
    public void showErrorMessage(String message) {
        Log.i("z- showErrorMessage", message);
        Snackbar.make(clGeneral, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showData(SaleByStoreAndYearResponse saleByStoreAndYearResponse) {

        Log.i("z- showData", new Gson().toJson(saleByStoreAndYearResponse));

        ArrayList<Entry> e1 = new ArrayList<Entry>();

        /*
        for (int i = 0; i < 12; i++) {
            e1.add(new Entry(i, (int) (Math.random() * 65) + 40));
        }
        */

        e1.add(new Entry(1, 100));
        e1.add(new Entry(2, 200));
        e1.add(new Entry(3, 300));
        e1.add(new Entry(4, 400));
        e1.add(new Entry(5, 500));


        ArrayList<String> labels = new ArrayList<>();
        labels.add("2013");
        labels.add("2014");
        labels.add("2015");
        labels.add("2016");

        LineDataSet d1 = new LineDataSet(e1, "New DataSet , (1)");
        d1.setLineWidth(2.5f);
        //d1.setValueFormatter(new MyValueFormatter());
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(true);


        ArrayList<Entry> e2 = new ArrayList<Entry>();

        /*
        for (int i = 0; i < 12; i++) {
            e2.add(new Entry(i, e1.get(i).getY() - 30));
        }
        */

        e2.add(new Entry(1, 300));
        e2.add(new Entry(2, 400));
        e2.add(new Entry(3, 500));
        e2.add(new Entry(4, 600));
        e2.add(new Entry(5, 700));

        LineDataSet d2 = new LineDataSet(e2, "New DataSet , (2)");
        d2.setLineWidth(2.5f);
        //d2.setValueFormatter(new MyValueFormatter());
        d2.setHighLightColor(Color.rgb(244, 117, 117));
        d2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setDrawValues(false);

        LineData lineData = new LineData(d1, d2);
        //lineData.setValueFormatter(new MyValueFormatter());
        lchSale.setData(lineData);
        lchSale.invalidate();

    }

    @Override
    public void showError(String message) {
        Snackbar.make(clGeneral, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if(menuItem.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

}
