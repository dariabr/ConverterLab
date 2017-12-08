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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

import static android.content.ContentValues.TAG;

public class HomeInteractor {

    private static HomeInteractor instance;

    private HomeInteractor() {
    }

    public static HomeInteractor init() {
        if (instance == null) {
            instance = new HomeInteractor();
        }
        return instance;
    }


    public Observable<Information> getAndWriteData() {
        //инициализация сетевого взаимодействия
        return Query.getInstance()
                //Получаем API
                .getApi()
                //грузим данные
                .getCurrencyCash()
                //при подписке на сетевой запрос - выполнинть в новом потоке
                .subscribeOn(Schedulers.newThread())
                //при успешной загрузки - сравниваем время с сервера и в нашей бд,
                // записуем в базу данных если данные новые
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
                        }, error -> writeToDatabase(result)));
    }

    /*
    * Запись в базу данных
    * */
    private void writeToDatabase(Information currencyCash) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        //запись времени
        realm.delete(DateRate.class);
        DateRate dateRate = realm.createObject(DateRate.class);
        dateRate.setTime(currencyCash.getDate().getTime());

        //Запись городов
        for (City city : currencyCash.getCities()) {
            realm.copyToRealmOrUpdate(city);
        }

        //Запись регионов
        for (Region region : currencyCash.getRegions()) {
            realm.copyToRealmOrUpdate(region);
        }

        //Запись валют
        for (Currencies currencies : currencyCash.getCurrencies()) {
            realm.copyToRealmOrUpdate(currencies);
        }

        //Запись организаций
        for (Organization organization : currencyCash.getOrganizations()) {
            Organization org = realm.copyToRealmOrUpdate(organization);
            //перебираем список валют в организации
            Observable.fromIterable(organization.getCurrencies())
                    .subscribe(currency -> {
                        Price serverPrice = currency.getPrice();

                        Price dbPrice = realm.createObject(Price.class);
                        dbPrice.setAsk(serverPrice.getAsk());
                        dbPrice.setBid(serverPrice.getBid());
                        dbPrice.setCurrencyName(currency.getCurrencyId());
                        dbPrice.setOrganizationId(org.getId());
                    });

        }

        realm.commitTransaction();
        realm.close();
        Log.d(TAG, "write data to database");
    }

}
