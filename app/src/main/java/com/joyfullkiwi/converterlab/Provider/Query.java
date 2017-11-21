package com.joyfullkiwi.converterlab.Provider;

import android.util.Log;

import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;
import com.joyfullkiwi.converterlab.BuildConfig;
import com.joyfullkiwi.converterlab.Utils.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Query {

    private static API instance;

    private Query() {
    }

    public static API getInstance() {
        if (instance == null) {

            OkHttpClient.Builder client = new OkHttpClient.Builder();
            client.addInterceptor(new LoggingInterceptor.Builder()
                    .loggable(BuildConfig.DEBUG)
                    .setLevel(Level.BASIC)
                    .log(Log.INFO)
                    .request("Request")
                    .response("Response")
                    .addHeader("version", BuildConfig.VERSION_NAME)
                    .build());
            OkHttpClient okHttpClient = client.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            instance = retrofit.create(API.class);

            return instance;
        } else {
            return instance;
        }
    }
}
