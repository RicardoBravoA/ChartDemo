package com.rba.chartdemo.api;

import com.rba.chartdemo.BuildConfig;
import com.rba.chartdemo.salebystoreandyear.SaleByStoreAndYearInteractor;
import com.rba.chartdemo.salestore.SaleStoreYearInteractor;
import com.rba.chartdemo.salestorebybranchoffice.SaleStoreBranchOfficeInteractor;
import com.rba.chartdemo.service.store.StoreInteractor;
import com.rba.chartdemo.service.year.YearInteractor;
import com.rba.chartdemo.storesale.StoreSaleInteractor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by ricardobravo on 13/12/16.
 */

@Module
public class NetworkModule {

    public NetworkModule(){}

    @Provides
    @Singleton
    Retrofit retrofitInstance() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        logging.setLevel(HttpLoggingInterceptor.Level.NONE);

        if(BuildConfig.IS_DEBUG){
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public ApiService providesNetworkService(
            Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    public YearInteractor year(ApiService apiService) {
        return new YearInteractor(apiService);
    }

    @Provides
    @Singleton
    public StoreInteractor store(ApiService apiService) {
        return new StoreInteractor(apiService);
    }

    @Provides
    @Singleton
    public SaleStoreYearInteractor saleStoreYear(ApiService apiService) {
        return new SaleStoreYearInteractor(apiService);
    }

    @Provides
    @Singleton
    public StoreSaleInteractor storeSale(ApiService apiService) {
        return new StoreSaleInteractor(apiService);
    }

    @Provides
    @Singleton
    public SaleByStoreAndYearInteractor saleByStoreAndYear(ApiService apiService) {
        return new SaleByStoreAndYearInteractor(apiService);
    }

    @Provides
    @Singleton
    public SaleStoreBranchOfficeInteractor saleStoreBranchOfficeInteractor(ApiService apiService) {
        return new SaleStoreBranchOfficeInteractor(apiService);
    }

    /*
    @Provides
    @Singleton
    public OfferJobInteractor offerJobService(AptitusService aptitusService) {
        return new OfferJobInteractor(aptitusService);
    }
    */

    public static Retrofit getRetrofit(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.NONE);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(logging).build();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


}
