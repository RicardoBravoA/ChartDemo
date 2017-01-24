package com.rba.chartdemo.storesale;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.rba.chartdemo.R;
import com.rba.chartdemo.base.BaseFragment;
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

public class StoreSaleChartFragment extends BaseFragment implements StoreSaleView,
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
    @BindView(R.id.pchStoreSale) PieChart pchStoreSale;

    public StoreSaleChartFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getChartComponent().injectStoreSale(this);
        View view = inflater.inflate(R.layout.fragment_store_sale_chart, container, false);
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
        storePresenter.loadYear();


        pchStoreSale.setUsePercentValues(false);
        pchStoreSale.getDescription().setEnabled(false);
        pchStoreSale.setExtraOffsets(5, 10, 5, 5);

        pchStoreSale.setDragDecelerationFrictionCoef(0.95f);

        pchStoreSale.setDrawHoleEnabled(true);
        pchStoreSale.setHoleColor(Color.WHITE);

        pchStoreSale.setTransparentCircleColor(Color.WHITE);
        pchStoreSale.setTransparentCircleAlpha(110);

        pchStoreSale.setHoleRadius(40f);
        pchStoreSale.setTransparentCircleRadius(45f);

        pchStoreSale.setDrawCenterText(true);

        pchStoreSale.setRotationAngle(0);
        pchStoreSale.setRotationEnabled(true);
        pchStoreSale.setHighlightPerTapEnabled(true);

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
        Log.i("z- showErrorStore", message);
    }

    @Override
    public void showErrorMessage(String message) {
        Log.i("z- showErrorMessage", message);
        Snackbar.make(linGeneral, message, Snackbar.LENGTH_SHORT).show();
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
        pchStoreSale.setData(data);

        pchStoreSale.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        pchStoreSale.setEntryLabelColor(ContextCompat.getColor(getActivity(), R.color.plumb));
        pchStoreSale.setEntryLabelTextSize(12f);

        pchStoreSale.invalidate();

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