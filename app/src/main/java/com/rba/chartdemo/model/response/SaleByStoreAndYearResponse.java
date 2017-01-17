package com.rba.chartdemo.model.response;

import java.util.List;

/**
 * Created by Ricardo Bravo on 16/01/17.
 */

public class SaleByStoreAndYearResponse {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * year : 2013
         * store : [{"store_id":1,"store_description":"Tienda 1","year_sale":2013,"amount":"555000"},{"store_id":2,"store_description":"Tienda 2","year_sale":2013,"amount":"218500"},{"store_id":3,"store_description":"Tienda 3","year_sale":2013,"amount":"278200"},{"store_id":4,"store_description":"Tienda 4","year_sale":2013,"amount":"259200"}]
         */

        private int year;
        private List<StoreBean> store;

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public List<StoreBean> getStore() {
            return store;
        }

        public void setStore(List<StoreBean> store) {
            this.store = store;
        }

        public static class StoreBean {
            /**
             * store_id : 1
             * store_description : Tienda 1
             * year_sale : 2013
             * amount : 555000
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
}
