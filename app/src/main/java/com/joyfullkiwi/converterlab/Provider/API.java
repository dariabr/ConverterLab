package com.joyfullkiwi.converterlab.Provider;


import com.joyfullkiwi.converterlab.Models.Information;


import io.reactivex.Observable;
import retrofit2.http.GET;

public interface API {

    @GET("/ru/public/currency-cash.json")
    Observable<Information> getCurrencyCash();

}
