package com.joyfullkiwi.converterlab.Home;



import android.util.Log;

import com.joyfullkiwi.converterlab.Models.City;
import com.joyfullkiwi.converterlab.Models.Currencies;
import com.joyfullkiwi.converterlab.Models.DateRate;
import com.joyfullkiwi.converterlab.Models.Information;
import com.joyfullkiwi.converterlab.Models.Organization;
import com.joyfullkiwi.converterlab.Models.Price;
import com.joyfullkiwi.converterlab.Models.Region;
import com.joyfullkiwi.converterlab.Provider.Query;
import com.joyfullkiwi.converterlab.Utils.RxAndroid;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class HomeInteractor {

    private static HomeInteractor instance;

    private HomeInteractor(){}

    public static HomeInteractor init(){
        if(instance == null){
            instance = new HomeInteractor();
        }
        return instance;
    }

    //получение и запись данных ,если дата в базе меньше даты на сервере
    public Observable<Information> getAndWriteData(){

        return Query.getInstance()
                .getApi()
                .getCurrencyCash()
                .subscribeOn(Schedulers.newThread())
                .doOnNext(result -> RxAndroid.getRealmObject(DateRate.class)
                        .subscribe(dateRate -> {
                            Date serverDate = new Date(result.getDate().getTime());
                            Date localDate = new Date(dateRate.getTime());
                            int compare = serverDate.compareTo(localDate);
                            if (compare == 1) {
                                DateFormat resultFormat = new SimpleDateFormat("dd MMMM 'в' kk:mm", Locale.getDefault());
                                Log.d(TAG, "update data in : " + resultFormat.format(serverDate));
                                writeToDatabase(result);
                            }
                        },error -> writeToDatabase(result)));
    }


    private void writeToDatabase(Information currencyCash) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();


        realm.delete(DateRate.class);
        DateRate dateRate = realm.createObject(DateRate.class);
        dateRate.setTime(currencyCash.getDate().getTime());
      //  DateRate dateRate = new DateRate();

        //dateRate.setDate(currencyCash.getDate());

       // realm.copyToRealmOrUpdate(dateRate);

        for (City city : currencyCash.getCities()) {
            realm.copyToRealmOrUpdate(city);
        }

        for (Region region : currencyCash.getRegions()) {
            realm.copyToRealmOrUpdate(region);
        }

        //валюта в бд
        for (Currencies currencies : currencyCash.getCurrencies()) {
            realm.copyToRealmOrUpdate(currencies);
        }

        for (Organization organization : currencyCash.getOrganizations()) {
            Organization org = realm.copyToRealmOrUpdate(organization);
            //перебор список валют ворганизации
            Observable.fromIterable(organization.getCurrencies())
                    .subscribe(currency -> {
                        Price serverPrice = currency.getPrice();
                        Price dbPrice = realm.createObject(Price.class);
                        dbPrice.setAsk(serverPrice.getAsk());
                        dbPrice.setBid(serverPrice.getBid());
                        dbPrice.setCurrencyName(currency.getCurrencyName());
                        dbPrice.setOrganizationId(org.getId());
                    });
            realm.copyToRealmOrUpdate(organization);
        }

        realm.commitTransaction();
    }


}
