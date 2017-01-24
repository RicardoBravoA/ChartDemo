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

public class StoreSaleChartFragment extends BaseFragment implements SaleStoreYearView,
        AdapterView.OnItemSelectedListener {

    private SaleStoreYearPresenter saleStoreYearPresenter;
    private YearPresenter yearPresenter;
    @Inject
    SaleStoreYearInteractor saleStoreYearInteractor;
    @Inject
    YearInteractor yearInteractor;
    private YearResponse yearResponse;

    @BindView(R.id.linGeneral) LinearLayout linGeneral;
    @BindView(R.id.spYear) CustomSpinner spYear;
    @BindView(R.id.pchSaleStore) PieChart pchSaleStore;

    public StoreSaleChartFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        pchSaleStore.setEntryLabelColor(ContextCompat.getColor(getActivity(), R.color.plumb));
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