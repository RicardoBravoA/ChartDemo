package com.rba.chartdemo.main;

import com.rba.chartdemo.model.entity.DataEntity;

import java.util.List;

/**
 * Created by ricardobravo on 13/12/16.
 */

public interface MainView {

    void init();

    void onClickItem(DataEntity dataEntity);

    void showData(List<DataEntity> list);

}
