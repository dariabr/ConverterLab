package com.joyfullkiwi.converterlab.Provider;

import com.joyfullkiwi.converterlab.Model.ObjectModel;

import retrofit2.Callback;
import retrofit2.http.GET;


public interface API {
    ///ru/public/currency-cash.json
    @GET("/ru/public/currency-cash.json")
    void getObjectModel(Callback<ObjectModel> callback);

}
