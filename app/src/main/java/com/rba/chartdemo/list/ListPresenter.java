package com.rba.chartdemo.list;

import com.rba.chartdemo.model.entity.DataEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ricardobravo on 13/12/16.
 */

public class ListPresenter {

    private ListView listView;

    public void loadData(){

        List<DataEntity> dataEntityList = new ArrayList<>();
        dataEntityList.add(new DataEntity(1, "Ventas de Sucursal por año"));
        dataEntityList.add(new DataEntity(2, "Ventas totales de Sucursal por año"));

        listView.showData(dataEntityList);

    }

    public ListPresenter(ListView listView){
        this.listView = listView;
    }

    public void onClickCardView(DataEntity dataEntity){
        listView.onClickItem(dataEntity);
    }

}
