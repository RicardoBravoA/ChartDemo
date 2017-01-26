package com.rba.chartdemo.model.response;

import java.util.List;

/**
 * Created by Ricardo Bravo on 26/01/17.
 */

public class BranchStoreResponse {

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
         * branch : [{"store_id":1,"store_description":"Tienda 1","branch_id":1,"branch_description":"Sucursal 1","year_sale":2013,"amount":"120800"},{"store_id":1,"store_description":"Tienda 1","branch_id":2,"branch_description":"Sucursal 2","year_sale":2013,"amount":"139400"},{"store_id":1,"store_description":"Tienda 1","branch_id":3,"branch_description":"Sucursal 3","year_sale":2013,"amount":"112000"},{"store_id":1,"store_description":"Tienda 1","branch_id":4,"branch_description":"Sucursal 4","year_sale":2013,"amount":"130000"},{"store_id":1,"store_description":"Tienda 1","branch_id":5,"branch_description":"Sucursal 5","year_sale":2013,"amount":"52800"}]
         */

        private int year;
        private List<BranchBean> branch;

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public List<BranchBean> getBranch() {
            return branch;
        }

        public void setBranch(List<BranchBean> branch) {
            this.branch = branch;
        }

        public static class BranchBean {
            /**
             * store_id : 1
             * store_description : Tienda 1
             * branch_id : 1
             * branch_description : Sucursal 1
             * year_sale : 2013
             * amount : 120800
             */

            private int store_id;
            private String store_description;
            private int branch_id;
            private String branch_description;
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

            public int getBranch_id() {
                return branch_id;
            }

            public void setBranch_id(int branch_id) {
                this.branch_id = branch_id;
            }

            public String getBranch_description() {
                return branch_description;
            }

            public void setBranch_description(String branch_description) {
                this.branch_description = branch_description;
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
