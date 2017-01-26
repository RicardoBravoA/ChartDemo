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

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.rba.chartdemo.R;
import com.rba.chartdemo.base.BaseFragment;
import com.rba.chartdemo.model.response.BranchStoreResponse;
import com.rba.chartdemo.model.response.YearResponse;
import com.rba.chartdemo.service.year.YearInteractor;
import com.rba.chartdemo.service.year.YearPresenter;
import com.rba.chartdemo.util.Util;
import com.rba.chartdemo.util.control.spinner.CustomSpinner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ricardo Bravo on 26/01/17.
 */

public class SaleStoreBranchOfficeOfficeBarFragment extends BaseFragment implements SaleStoreBranchOfficeView,
        AdapterView.OnItemSelectedListener {

    private SaleStoreBranchOfficePresenter saleStoreBranchOfficePresenter;
    private YearPresenter yearPresenter;
    @Inject SaleStoreBranchOfficeInteractor saleStoreBranchOfficeInteractor;
    @Inject YearInteractor yearInteractor;
    private YearResponse yearResponse;
    private BranchStoreResponse branchStoreResponse;

    @BindView(R.id.linGeneral) LinearLayout linGeneral;
    @BindView(R.id.spYear) CustomSpinner spYear;
    @BindView(R.id.bchSale) BarChart bchSale;
    private List<String> storeList;

    public SaleStoreBranchOfficeOfficeBarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getChartComponent().injectSaleStoreBranchOffice(this);
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
        spYear.setOnItemSelectedListener(this);
        saleStoreBranchOfficePresenter = new SaleStoreBranchOfficePresenter(saleStoreBranchOfficeInteractor, this);
        yearPresenter = new YearPresenter(yearInteractor, this);
        yearPresenter.loadYearBranch();
        storeList = new ArrayList<>();

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
        xAxis.setLabelCount(5);
        xAxis.setDrawAxisLine(true);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                if(Util.format0DecimalsInt(value) < storeList.size()){
                    return String.valueOf(storeList.get(Util.format0DecimalsInt(value)));
                }else{
                    return "";
                }


                /*
                //return String.valueOf(storeList.get(Util.format0DecimalsInt(value-1)));
                Log.i("z- format "+value, String.valueOf(storeList.get(Util.format0DecimalsInt(value))));
                Log.i("z- value", ""+value);
                //return String.valueOf(branchStoreResponse.getData().get(Util.format0DecimalsInt(value)).getYear());
                return "hola";
                */
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

        saleStoreBranchOfficePresenter.load(
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
    public void showBranchStore(BranchStoreResponse branchStoreResponse) {

        this.branchStoreResponse = branchStoreResponse;

        float groupSpace = 0.08f;
        float barSpace = 0.02f;
        float barWidth = 0.1f;

        int groupCount = branchStoreResponse.getData().size()-1;
        int startYear = 0;

        Log.i("z- showStoreYear", new Gson().toJson(branchStoreResponse));

        List<ArrayList<BarEntry>> data = new ArrayList<>();

        Log.i("z- branchStoreResponse", ""+branchStoreResponse.getData().size());

        for(int i = 0; i < branchStoreResponse.getData().size(); i++){

            Log.i("z- year for "+i, String.valueOf(branchStoreResponse.getData().get(i).getYear()));
            storeList.add(String.valueOf(branchStoreResponse.getData().get(i).getBranch().get(i).getStore_description()));

            ArrayList<BarEntry> value = new ArrayList<>();


            for(BranchStoreResponse.DataBean.BranchBean branchBean : branchStoreResponse.getData().get(i).getBranch()){
                Log.i("z- data for", ""+i+" - "+new Gson().toJson(branchBean));
                value.add(new BarEntry(branchBean.getStore_id(), Float.parseFloat(branchBean.getAmount()), branchBean.getStore_description()));
            }
            data.add(value);

        }

        Log.i("z- yearList", ""+storeList.size());
        Log.i("z- yearList", new Gson().toJson(storeList));

        List<IBarDataSet> barDataSetList = new ArrayList<>();

        for(int i = 0; i < storeList.size(); i++){
            Log.i("z- year "+i, ""+storeList.get(i));
            Log.i("z- array "+i, new Gson().toJson(data.get(i))+" - "+storeList.get(i));
            BarDataSet barDataSet = new BarDataSet(data.get(i), storeList.get(i));
            barDataSet.setColor(ColorTemplate.MATERIAL_COLORS[i]);
            barDataSetList.add(barDataSet);
        }

        BarData barData = new BarData(barDataSetList);
        barData.setValueFormatter(new LargeValueFormatter());

        bchSale.setData(barData);


        bchSale.getBarData().setBarWidth(barWidth);

        bchSale.getXAxis().setAxisMinimum(startYear);

        bchSale.getXAxis().setAxisMaximum(startYear + bchSale.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        bchSale.groupBars(startYear, groupSpace, barSpace);


        bchSale.animateY(1000, Easing.EasingOption.EaseInOutQuad);

        bchSale.invalidate();


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i("z- onItemSelected", String.valueOf(yearResponse.getData().get(i).getYear_sale()));

        saleStoreBranchOfficePresenter.load(yearResponse.getData().get(i).getYear_sale());

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.i("z- onNothingSelected", "true");
    }


}