package com.osg.ex82retrofitboard;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitHelper {

    public static Retrofit getInstanceGson(){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://kamniang.dothome.co.kr");
        builder.addConverterFactory(GsonConverterFactory.create());

        return builder.build();
    }

    public static Retrofit getInstanceScalars(){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://kamniang.dothome.co.kr");
        builder.addConverterFactory(ScalarsConverterFactory.create());

        return builder.build();
    }

}

