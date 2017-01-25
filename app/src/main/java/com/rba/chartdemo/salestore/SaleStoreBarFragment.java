package com.rba.chartdemo.salestore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.rba.chartdemo.R;
import com.rba.chartdemo.base.BaseFragment;
import com.rba.chartdemo.component.ToolTipBarChart;
import com.rba.chartdemo.model.response.StoreYearResponse;
import com.rba.chartdemo.model.response.YearResponse;
import com.rba.chartdemo.service.year.YearInteractor;
import com.rba.chartdemo.service.year.YearPresenter;
import com.rba.chartdemo.util.Util;
import com.rba.chartdemo.util.control.spinner.CustomSpinner;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ricardo Bravo on 18/01/17.
 */

public class SaleStoreBarFragment extends BaseFragment implements SaleStoreYearView,
        AdapterView.OnItemSelectedListener {

    private SaleStoreYearPresenter saleStoreYearPresenter;
    private YearPresenter yearPresenter;
    @Inject SaleStoreYearInteractor saleStoreYearInteractor;
    @Inject YearInteractor yearInteractor;
    private YearResponse yearResponse;
    private StoreYearResponse storeYearResponse;

    @BindView(R.id.linGeneral) LinearLayout linGeneral;
    @BindView(R.id.spYear) CustomSpinner spYear;
    @BindView(R.id.bchSale) BarChart bchSale;

    public SaleStoreBarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getChartComponent().injectSaleStoreYear(this);
        View view =  inflater.inflate(R.layout.fragment_sale_store_bar_chart, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void init() {
        spYear.setOnItemSelectedListener(this);
        saleStoreYearPresenter = new SaleStoreYearPresenter(saleStoreYearInteractor, this);
        yearPresenter = new YearPresenter(yearInteractor, this);
        yearPresenter.loadYear();


        bchSale.setDrawBarShadow(false);
        bchSale.setDrawValueAboveBar(true);
        bchSale.getDescription().setEnabled(false);
        bchSale.setMaxVisibleValueCount(60);
        bchSale.setPinchZoom(false);

        bchSale.setDrawGridBackground(false);


        XAxis xAxis = bchSale.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(4);
        xAxis.setDrawAxisLine(true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return storeYearResponse.getData().get(Integer.parseInt(Util.format0Decimals(value-1))).getStore_description();
            }
        });


        bchSale.getDescription().setEnabled(false);
        bchSale.setDrawGridBackground(false);

        YAxis leftAxis = bchSale.getAxisLeft();
        leftAxis.setLabelCount(5, false);
        leftAxis.setAxisMinimum(0f);

        YAxis rightAxis = bchSale.getAxisRight();
        rightAxis.setLabelCount(5, false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);


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
        Snackbar.make(linGeneral, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showStoreYear(StoreYearResponse storeYearResponse) {

        this.storeYearResponse = storeYearResponse;
        Log.i("z- showStoreYear", new Gson().toJson(storeYearResponse));


        ArrayList<BarEntry> yVals1 = new ArrayList<>();

        for(StoreYearResponse.DataBean dataBean : storeYearResponse.getData()){
            yVals1.add(new BarEntry(dataBean.getStore_id(), Float.parseFloat(dataBean.getAmount()), dataBean.getStore_description()));
        }

        BarDataSet set1 = new BarDataSet(yVals1, getString(R.string.sale_year_variable,
                String.valueOf(yearResponse.getData().get(spYear.getSelectedIndex()).getYear_sale())));

        set1.setColors(ColorTemplate.MATERIAL_COLORS);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);

        bchSale.setData(data);

        ToolTipBarChart mv = new ToolTipBarChart(getContext(), storeYearResponse);
        mv.setChartView(bchSale);
        bchSale.setMarker(mv);
        bchSale.animateY(1000, Easing.EasingOption.EaseInOutQuad);

        bchSale.invalidate();


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
