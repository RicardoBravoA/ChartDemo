package com.rba.chartdemo.api;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.rba.chartdemo.model.response.ErrorResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by ricardobravo on 13/12/16.
 */

public class NetworkError extends Throwable {
    private static final String DEFAULT_ERROR_MESSAGE = "Something went wrong! Please try again.";
    private static final String NETWORK_ERROR_MESSAGE = "No Internet Connection!";
    private static final String ERROR_MESSAGE_HEADER = "Error-Message";
    private final Throwable error;

    public NetworkError(Throwable e) {
        super(e);
        this.error = e;
    }

    public String getMessage() {
        return error.getMessage();
    }

    public boolean isAuthFailure() {
        return error instanceof HttpException &&
                ((HttpException) error).code() == HttpURLConnection.HTTP_UNAUTHORIZED;
    }

    public boolean isResponseNull() {
        return error instanceof HttpException && ((HttpException) error).response() == null;
    }

    public String getAppErrorMessage() {

        if (this.error instanceof IOException){
            return NETWORK_ERROR_MESSAGE;
        }

        if (!(this.error instanceof HttpException)){
            return DEFAULT_ERROR_MESSAGE;
        }

        retrofit2.Response<?> response = ((HttpException) this.error).response();
        if (response != null) {
            String status = getJsonStringFromResponse(response);
            if (!TextUtils.isEmpty(status)){
                return  status;
            }

            Map<String, List<String>> headers = response.headers().toMultimap();
            if (headers.containsKey(ERROR_MESSAGE_HEADER))
                return headers.get(ERROR_MESSAGE_HEADER).get(0);
        }

        return DEFAULT_ERROR_MESSAGE;
    }

    private String getJsonStringFromResponse(final retrofit2.Response<?> response) {
        try {
            String jsonString = response.errorBody().string();
            ErrorResponse errorResponse = new Gson().fromJson(jsonString, ErrorResponse.class);
            return errorResponse.get_meta().getStatus();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Throwable getError() {
        return error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (o == null || getClass() != o.getClass()){
            return false;
        }

        NetworkError that = (NetworkError) o;

        return error != null ? error.equals(that.error) : that.error == null;

    }

    @Override
    public int hashCode() {
        return error != null ? error.hashCode() : 0;
    }
}
