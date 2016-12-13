package com.rba.chartdemo.api;

import com.rba.chartdemo.model.response.ErrorResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by ricardobravo on 13/12/16.
 */

public class ErrorUtil {

    public static ErrorResponse parseError(Response<?> response) {

        Converter<ResponseBody, ErrorResponse> converter = NetworkModule.getRetrofit()
                .responseBodyConverter(ErrorResponse.class,
                        new Annotation[0]);

        ErrorResponse error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ErrorResponse();
        }

        return error;
    }

}
