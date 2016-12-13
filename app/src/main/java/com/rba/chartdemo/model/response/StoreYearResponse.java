package com.rba.chartdemo.model.response;

import java.util.List;

/**
 * Created by ricardobravo on 13/12/16.
 */

public class StoreYearResponse {

    /**
     * data : [{"store_id":1,"store_description":"Tienda 1","year_sale":2014,"amount":"300600"}]
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class MetaBean {
        /**
         * status : success
         * code : 200
         */

        private String status;
        private String code;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    public static class DataBean {
        /**
         * store_id : 1
         * store_description : Tienda 1
         * year_sale : 2014
         * amount : 300600
         */

        private int store_id;
        private String store_description;
        private int year_sale;
        private String amount;

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

        public int getYear_sale() {
            return year_sale;
        }

        public void setYear_sale(int year_sale) {
            this.year_sale = year_sale;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }
}
