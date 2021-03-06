package com.rba.chartdemo.salestoreyear;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.rba.chartdemo.util.Util;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ricardo Bravo on 26/01/17.
 */

public class SaleStoreYearBarFragment extends BaseFragment
        implements SaleStoreYearView {

    private SaleStoreYearPresenter saleStoreYearPresenter;
    @Inject
    SaleStoreYearInteractor saleStoreYearInteractor;

    @BindView(R.id.linGeneral) LinearLayout linGeneral;
    @BindView(R.id.bchSale) BarChart bchSale;
    private List<String> yearList;
    private List<String> storeList;

    public SaleStoreYearBarFragment() {
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
        saleStoreYearPresenter = new SaleStoreYearPresenter(saleStoreYearInteractor, this);
        yearList = new ArrayList<>();
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
        xAxis.setLabelCount(4);
        xAxis.setDrawAxisLine(true);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                /*
                Log.i("x- axis", "value: "+value
                        +" - min: "+axis.getAxisMaximum()+" - max: "+axis.getAxisMaximum()
                        +" - label: "+axis.getLabelCount()+" - limit"+axis.getLimitLines());
                */

                Log.i("x- size", ""+yearList.size());

                if(Util.format0DecimalsInt(value) < yearList.size()){
                    Log.i("x- value", ""+value);
                    Log.i("x- valueList", ""+yearList.get(Util.format0DecimalsInt(value)));
                    return String.valueOf(yearList.get(Util.format0DecimalsInt(value)));
                }else{
                    return "";
                }
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

        saleStoreYearPresenter.load();

    }

    @Override
    public void showErrorMessage(String message) {
        Log.i("z- showErrorMessage", message);
        Snackbar.make(linGeneral, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showBranchStore(BranchStoreResponse branchStoreResponse) {

        float groupSpace = 0.08f;
        float barSpace = 0.02f;
        float barWidth = 0.1f;

        int groupCount = branchStoreResponse.getData().size();

        Log.i("z- showStoreYear", new Gson().toJson(branchStoreResponse));

        Log.i("z- groupCount", ""+groupCount);

        List<ArrayList<BarEntry>> data = new ArrayList<>();

        Log.i("z- branchStoreResponse", ""+branchStoreResponse.getData().size());

        for(int i = 0; i < branchStoreResponse.getData().size(); i++){

            Log.i("z- year for "+i, String.valueOf(branchStoreResponse.getData().get(i).getYear()));
            yearList.add(String.valueOf(branchStoreResponse.getData().get(i).getYear()));
            storeList.add(String.valueOf(branchStoreResponse.getData().get(i).getBranch().get(i).getStore_description()));

            ArrayList<BarEntry> value = new ArrayList<>();


            for(BranchStoreResponse.DataBean.BranchBean branchBean : branchStoreResponse.getData().get(i).getBranch()){
                Log.i("z- data for", ""+i+" - "+new Gson().toJson(branchBean));
                value.add(new BarEntry(branchBean.getStore_id(), Float.parseFloat(branchBean.getAmount()), branchBean.getStore_description()));
            }
            data.add(value);

        }

        Log.i("z- yearList", ""+yearList.size());
        Log.i("z- yearList", new Gson().toJson(yearList));

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

        bchSale.getXAxis().setAxisMinimum(0);

        bchSale.getXAxis().setAxisMaximum(bchSale.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);

        bchSale.groupBars(0, groupSpace, barSpace);

        bchSale.animateY(1000, Easing.EasingOption.EaseInOutQuad);

        bchSale.invalidate();


    }


}