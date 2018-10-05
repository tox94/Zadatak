package com.sofascore.tonib.firsttask.service.repo;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    public static final String TEAMS_API_URL = "https://mobile.sofascore.com/mobile/v4/mcc/234/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            /*retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(TEAMS_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();*/

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(TEAMS_API_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }


        return retrofit;
    }
}
