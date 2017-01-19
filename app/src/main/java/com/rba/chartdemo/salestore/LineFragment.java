package com.rba.chartdemo.salestore;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.rba.chartdemo.R;
import com.rba.chartdemo.base.BaseFragment;
import com.rba.chartdemo.model.response.StoreYearResponse;
import com.rba.chartdemo.model.response.YearResponse;
import com.rba.chartdemo.service.year.YearInteractor;
import com.rba.chartdemo.service.year.YearPresenter;
import com.rba.chartdemo.util.control.spinner.CustomSpinner;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ricardo Bravo on 18/01/17.
 */

public class LineFragment extends BaseFragment implements SaleStoreYearView,
        AdapterView.OnItemSelectedListener {

    private SaleStoreYearPresenter saleStoreYearPresenter;
    private YearPresenter yearPresenter;
    @Inject SaleStoreYearInteractor saleStoreYearInteractor;
    @Inject YearInteractor yearInteractor;
    private YearResponse yearResponse;

    @BindView(R.id.linGeneral) LinearLayout linGeneral;
    @BindView(R.id.spYear) CustomSpinner spYear;
    @BindView(R.id.lchSale) LineChart lchSale;

    public LineFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getChartComponent().injectSaleStoreYear(this);
        View view =  inflater.inflate(R.layout.fragment_line, container, false);
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


        lchSale.getDescription().setEnabled(false);

        // enable touch gestures
        lchSale.setTouchEnabled(true);

        lchSale.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        lchSale.setDragEnabled(true);
        lchSale.setScaleEnabled(true);
        lchSale.setDrawGridBackground(false);
        lchSale.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        lchSale.setPinchZoom(true);

        // set an alternative background color
        lchSale.setBackgroundColor(Color.LTGRAY);

        lchSale.animateX(2500);

        // get the legend (only possible after setting data)
        Legend l = lchSale.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTextSize(11f);
        l.setTextColor(Color.WHITE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
//        l.setYOffset(11f);

        XAxis xAxis = lchSale.getXAxis();
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);

        YAxis leftAxis = lchSale.getAxisLeft();
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaximum(200f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = lchSale.getAxisRight();
        rightAxis.setTextColor(Color.RED);
        rightAxis.setAxisMaximum(900);
        rightAxis.setAxisMinimum(-200);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);

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

        Log.i("z- showStoreYear", new Gson().toJson(storeYearResponse));

        ArrayList<Entry> entries = new ArrayList<>();


        for(StoreYearResponse.DataBean dataBean: storeYearResponse.getData()){
            entries.add(new Entry(dataBean.getStore_id(), Float.parseFloat(dataBean.getAmount())));
        }


        LineDataSet set1 = new LineDataSet(entries, "DataSet 1");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setCircleColor(Color.WHITE);
        set1.setLineWidth(2f);
        set1.setCircleRadius(3f);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);

        LineData data = new LineData(set1);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        lchSale.setData(data);

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
