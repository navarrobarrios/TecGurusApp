package net.tecgurus.app.tecgurusapp.web;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCore {

    //region Variables
    private static final  String BASE_URL = "http://services.groupkt.com/state/get/";
    private static Retrofit mRetrofit;
    //endregion

    public static RetrofitCore getInstance(){
        return new RetrofitCore();
    }

    private RetrofitCore(){
        mRetrofit = new Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL).build();
    }

    public <S> S start(Class<S> classDef){
        return mRetrofit.create(classDef);
    }
}

