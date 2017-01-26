package com.rba.chartdemo.salestorebybranchoffice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.gson.Gson;
import com.rba.chartdemo.R;
import com.rba.chartdemo.base.BaseFragment;
import com.rba.chartdemo.model.response.StoreResponse;
import com.rba.chartdemo.model.response.StoreYearResponse;
import com.rba.chartdemo.salestore.SaleStoreYearInteractor;
import com.rba.chartdemo.salestore.SaleStoreYearPresenter;
import com.rba.chartdemo.service.store.StoreInteractor;
import com.rba.chartdemo.service.store.StorePresenter;
import com.rba.chartdemo.util.Util;
import com.rba.chartdemo.util.control.spinner.CustomSpinner;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ricardo Bravo on 26/01/17.
 */

public class SaleStoreBranchOfficeBarFragment extends BaseFragment implements SaleStoreBranchView,
        AdapterView.OnItemSelectedListener {

    private SaleStoreYearPresenter saleStoreYearPresenter;
    private StorePresenter storePresenter;
    @Inject SaleStoreYearInteractor saleStoreYearInteractor;
    @Inject StoreInteractor storeInteractor;
    private StoreResponse storeResponse;
    private StoreYearResponse storeYearResponse;

    @BindView(R.id.linGeneral) LinearLayout linGeneral;
    @BindView(R.id.spStore) CustomSpinner spStore;
    @BindView(R.id.bchSale)
    BarChart bchSale;

    public SaleStoreBranchOfficeBarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_branch_office_bar, container, false);
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
        spStore.setOnItemSelectedListener(this);
        //saleStoreYearPresenter = new SaleStoreYearPresenter(saleStoreYearInteractor, this);
        storePresenter = new StorePresenter(storeInteractor, this);
        storePresenter.loadStoreBranch();


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
    public void showStore(StoreResponse storeResponse) {
        Log.i("z- showStore", new Gson().toJson(storeResponse));
        this.storeResponse = storeResponse;

        spStore.setDataSource(storeResponse.getData());

        /*
        saleStoreYearPresenter.load(
                yearResponse.getData().get(spYear.getSelectedIndex()).getYear_sale());
        */
    }

    @Override
    public void showErrorStore(String message) {
        Log.i("z- showErrorStore", message);
    }

    @Override
    public void showErrorMessage(String message) {
        Log.i("z- showErrorMessage", message);
        Snackbar.make(linGeneral, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showStoreYear(StoreYearResponse storeYearResponse) {

        /*
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
        */


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i("z- onItemSelected", String.valueOf(storeResponse.getData().get(i).getStore_id()));

        //saleStoreYearPresenter.load(yearResponse.getData().get(i).getYear_sale());

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.i("z- onNothingSelected", "true");
    }

}