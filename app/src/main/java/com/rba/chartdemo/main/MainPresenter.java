package com.rba.chartdemo.main;

import com.rba.chartdemo.model.entity.DataEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ricardobravo on 13/12/16.
 */

public class MainPresenter {

    private MainView mainView;

    public void loadData(){

        List<DataEntity> dataEntityList = new ArrayList<>();
        dataEntityList.add(new DataEntity(1, "Ventas de Tienda por año"));
        dataEntityList.add(new DataEntity(2, "Ventas totales de Tienda por año"));
        dataEntityList.add(new DataEntity(3, "Ventas anuales por Tienda"));
        dataEntityList.add(new DataEntity(4, "Ventas de Sucursales por Año y Tienda"));

        mainView.showData(dataEntityList);

    }

    public MainPresenter(MainView mainView){
        this.mainView = mainView;
    }

    public void onClickCardView(DataEntity dataEntity){
        mainView.onClickItem(dataEntity);
    }

}
