package com.joyfullkiwi.converterlab.Provider;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.joyfullkiwi.converterlab.GSON.CityAdapter;
import com.joyfullkiwi.converterlab.GSON.CurrenciesAdapter;
import com.joyfullkiwi.converterlab.GSON.CurrencyAdapter;
import com.joyfullkiwi.converterlab.GSON.DateAdapter;
import com.joyfullkiwi.converterlab.GSON.OrganizationAdapter;
import com.joyfullkiwi.converterlab.GSON.RegionAdapter;
import com.joyfullkiwi.converterlab.Models.City;
import com.joyfullkiwi.converterlab.Models.Currencies;
import com.joyfullkiwi.converterlab.Models.Currency;
import com.joyfullkiwi.converterlab.Models.DateRate;
import com.joyfullkiwi.converterlab.Models.Organization;
import com.joyfullkiwi.converterlab.Models.Region;
import com.joyfullkiwi.converterlab.Utils.Constants;

import java.util.List;

import io.realm.RealmList;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Query {

    private static Query instance;
    private final API api;

    private Query(){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(new TypeToken<List<City>>(){}.getType(), CityAdapter.INSTANCE)
                .registerTypeAdapter(new TypeToken<List<Region>>() {}.getType(), RegionAdapter.INSTANCE)
                .registerTypeAdapter(new TypeToken<List<Currencies>>() {}.getType(), CurrenciesAdapter.INSTANCE)
                .registerTypeAdapter(new TypeToken<List<Organization>>() {}.getType(), OrganizationAdapter.INSTANCE)
                .registerTypeAdapter(new TypeToken<RealmList<Currency>>() {}.getType(), CurrencyAdapter.INSTANCE)
                .registerTypeAdapter(new TypeToken<DateRate>(){}.getType(), DateAdapter.INSTANCE)
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        api = retrofit.create(API.class);
    }

    public static Query getInstance(){
        if(instance == null){
            instance = new Query();
            return instance;
        }
        return instance;
    }

    public API getApi(){ return api;}


}
