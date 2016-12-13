package com.rba.chartdemo.model.response;

import java.util.List;

/**
 * Created by ricardobravo on 13/12/16.
 */

public class YearResponse {

    /**
     * data : [{"year_sale":2013},{"year_sale":2014},{"year_sale":2015},{"year_sale":2016}]
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
         * year_sale : 2013
         */

        private int year_sale;

        public int getYear_sale() {
            return year_sale;
        }

        public void setYear_sale(int year_sale) {
            this.year_sale = year_sale;
        }
    }
}
