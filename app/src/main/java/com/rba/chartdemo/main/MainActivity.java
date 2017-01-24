package com.rba.chartdemo.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.gson.Gson;
import com.rba.chartdemo.R;
import com.rba.chartdemo.model.entity.DataEntity;
import com.rba.chartdemo.salebystoreandyear.SaleByStoreAndYearActivity;
import com.rba.chartdemo.salestore.SaleStoreYearActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {

    private RecyclerView rcvList;
    private MainAdapter mainAdapter;
    private List<DataEntity> dataEntityList;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        init();
    }

    @Override
    public void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rcvList = (RecyclerView) findViewById(R.id.rcvList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rcvList.setLayoutManager(layoutManager);
        rcvList.setItemAnimator(new DefaultItemAnimator());

        dataEntityList = new ArrayList<>();
        mainPresenter = new MainPresenter(this);

        mainAdapter = new MainAdapter(this, mainPresenter);
        rcvList.setAdapter(mainAdapter);

        mainPresenter.loadData();

    }

    @Override
    public void onClickItem(DataEntity dataEntity) {
        Log.i("z- onClickItem", new Gson().toJson(dataEntity));
        switch (dataEntity.getId()){
            case 1:
                startActivity(new Intent(this, SaleStoreYearActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, SaleByStoreAndYearActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void showData(List<DataEntity> list) {
        dataEntityList.addAll(list);
        mainAdapter.load(dataEntityList);
        mainAdapter.notifyDataSetChanged();
    }
}
