package com.joyfullkiwi.converterlab.Provider;

import com.joyfullkiwi.converterlab.Model.ObjectModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;


public interface API {

    @GET("/ru/public/currency-cash.json")

    void getObjectModel(Callback<ObjectModel> callback);

    // Callback<ObjectModel> getObjectModel;
//Call<List<ObjectModel>> getObjectModel;
}
