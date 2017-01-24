package com.rba.chartdemo.model.response;

import java.util.List;

/**
 * Created by Ricardo Bravo on 24/01/17.
 */

public class StoreResponse {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * store_id : 1
         * store_description : Tienda 1
         */

        private int store_id;
        private String store_description;

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public String getStore_description() {
            return store_description;
        }

        public void setStore_description(String store_description) {
            this.store_description = store_description;
        }


        @Override
        public String toString() {
            return store_description;
        }
    }
}
