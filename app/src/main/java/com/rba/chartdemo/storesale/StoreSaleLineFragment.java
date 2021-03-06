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
import com.rba.chartdemo.component.ToolTip;
import com.rba.chartdemo.model.response.StoreResponse;
import com.rba.chartdemo.model.response.StoreYearResponse;
import com.rba.chartdemo.service.store.StoreInteractor;
import com.rba.chartdemo.service.store.StorePresenter;
import com.rba.chartdemo.util.control.spinner.CustomSpinner;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ricardo Bravo on 24/01/17.
 */

public class StoreSaleLineFragment extends BaseFragment implements StoreSaleView,
        AdapterView.OnItemSelectedListener {

    private StoreSalePresenter storeSalePresenter;
    private StorePresenter storePresenter;
    @Inject
    StoreSaleInteractor storeSaleInteractor;
    @Inject
    StoreInteractor storeInteractor;
    private StoreResponse storeResponse;

    @BindView(R.id.linGeneral) LinearLayout linGeneral;
    @BindView(R.id.spStore) CustomSpinner spStore;
    @BindView(R.id.lchSale) LineChart lchSale;

    public StoreSaleLineFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getChartComponent().injectStoreSale(this);
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
        spStore.setOnItemSelectedListener(this);
        storeSalePresenter = new StoreSalePresenter(storeSaleInteractor, this);
        storePresenter = new StorePresenter(storeInteractor, this);
        storePresenter.loadStore();


        lchSale.getDescription().setEnabled(false);
        lchSale.setDrawGridBackground(false);

        ToolTip mv = new ToolTip(getContext(), R.layout.tooltip);
        mv.setChartView(lchSale);
        lchSale.setMarker(mv);

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
    public void showStore(StoreResponse storeResponse) {
        Log.i("z- showYear", new Gson().toJson(storeResponse));
        this.storeResponse = storeResponse;

        spStore.setDataSource(storeResponse.getData());

        storeSalePresenter.load(
                storeResponse.getData().get(spStore.getSelectedIndex()).getStore_id());
    }

    @Override
    public void showErrorStore(String message) {
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

        for(StoreYearResponse.DataBean dataBean : storeYearResponse.getData()){
            values.add(new Entry(dataBean.getYear_sale(), Float.parseFloat(dataBean.getAmount())));
        }

        LineDataSet lineDataSet = new LineDataSet(values, "Ventas "+storeResponse.getData().get(spStore.getSelectedIndex()).getStore_description());
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
        Log.i("z- onItemSelected", String.valueOf(storeResponse.getData().get(i).getStore_id()));

        storeSalePresenter.load(storeResponse.getData().get(i).getStore_id());

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.i("z- onNothingSelected", "true");
    }

}
