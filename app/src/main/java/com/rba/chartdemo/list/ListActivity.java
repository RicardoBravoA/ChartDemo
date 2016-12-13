package com.rba.chartdemo.list;

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
import com.rba.chartdemo.salestore.SaleStoreYearActivity;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements ListView {

    private RecyclerView rcvList;
    private ListAdapter listAdapter;
    private List<DataEntity> dataEntityList;
    private ListPresenter listPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getMy().injectOfferJob(this);
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
        listPresenter = new ListPresenter(this);

        listAdapter = new ListAdapter(this, listPresenter);
        rcvList.setAdapter(listAdapter);

        listPresenter.loadData();

    }

    @Override
    public void onClickItem(DataEntity dataEntity) {
        Log.i("z- onClickItem", new Gson().toJson(dataEntity));
        switch (dataEntity.getId()){
            case 1:
                startActivity(new Intent(this, SaleStoreYearActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void showData(List<DataEntity> list) {
        dataEntityList.addAll(list);
        listAdapter.load(dataEntityList);
        listAdapter.notifyDataSetChanged();
    }
}
