package com.rba.chartdemo.storesale;

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
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.rba.chartdemo.R;
import com.rba.chartdemo.base.BaseFragment;
import com.rba.chartdemo.model.response.StoreYearResponse;
import com.rba.chartdemo.model.response.YearResponse;
import com.rba.chartdemo.salestore.SaleStoreYearInteractor;
import com.rba.chartdemo.salestore.SaleStoreYearPresenter;
import com.rba.chartdemo.salestore.SaleStoreYearView;
import com.rba.chartdemo.service.year.YearInteractor;
import com.rba.chartdemo.service.year.YearPresenter;
import com.rba.chartdemo.util.control.spinner.CustomSpinner;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ricardo Bravo on 24/01/17.
 */

public class StoreSaleLineFragment extends BaseFragment implements SaleStoreYearView,
        AdapterView.OnItemSelectedListener {

    private SaleStoreYearPresenter saleStoreYearPresenter;
    private YearPresenter yearPresenter;
    @Inject
    SaleStoreYearInteractor saleStoreYearInteractor;
    @Inject
    YearInteractor yearInteractor;
    private YearResponse yearResponse;

    @BindView(R.id.linGeneral)
    LinearLayout linGeneral;
    @BindView(R.id.spYear)
    CustomSpinner spYear;
    @BindView(R.id.lchSale)
    LineChart lchSale;

    public StoreSaleLineFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_store_sale_line, container, false);
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
        lchSale.setDrawGridBackground(false);

        XAxis xAxis = lchSale.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        YAxis leftAxis = lchSale.getAxisLeft();
        leftAxis.setLabelCount(5, false);
        leftAxis.setAxisMinimum(0f);

        YAxis rightAxis = lchSale.getAxisRight();
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

        Log.i("z- showStoreYear", new Gson().toJson(storeYearResponse));

        ArrayList<Entry> values = new ArrayList<Entry>();


        //StoreYearResponse.DataBean dataBean =

        for (int i = 0; i < 20; i++) {
            values.add(new Entry(i, (int) (Math.random() * 65) + 40));
        }

        LineDataSet lineDataSet = new LineDataSet(values, "New DataSet (2)");
        lineDataSet.setLineWidth(2.5f);
        lineDataSet.setCircleRadius(4.5f);
        lineDataSet.setHighLightColor(Color.rgb(244, 117, 117));
        lineDataSet.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        lineDataSet.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        lineDataSet.setDrawValues(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(lineDataSet); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(dataSets);

        // set data
        lchSale.setData(data);
        lchSale.animateX(750);

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
