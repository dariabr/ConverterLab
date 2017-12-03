package com.joyfullkiwi.converterlab.Home;


import android.app.DownloadManager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.joyfullkiwi.converterlab.Models.City;
import com.joyfullkiwi.converterlab.Models.Currencies;
import com.joyfullkiwi.converterlab.Models.DateRate;
import com.joyfullkiwi.converterlab.Models.Information;
import com.joyfullkiwi.converterlab.Models.Organization;
import com.joyfullkiwi.converterlab.Models.Region;
import com.joyfullkiwi.converterlab.Provider.Query;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

@InjectViewState
public class HomePresenter extends MvpPresenter<HomeView> {

    public HomePresenter(){

        Query.getInstance()
                .getApi()
                .getCurrencyCash()
                .subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .doOnNext(currencyCash -> writeToDatabase(currencyCash))
                .observeOn(AndroidSchedulers.mainThread())
                .map(currencyCash -> readRealm())
                .onErrorReturn(throwable -> readRealm())
                .subscribe(results -> {
                    handleResult(results);
                });
    }

    private void handleResult(RealmResults<Organization> results) {


        getViewState().onSuccessLoaded(results);

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for (Organization organization : results) {
            System.out.println("id = " + organization.getId());
            System.out.println("title = " + organization.getTitle());

            String cityName = realm
                    .where(City.class)
                    .equalTo("id", organization.getCityId())
                    .findFirst()
                    .getName();

            String regionName = realm
                    .where(Region.class)
                    .equalTo("id", organization.getRegionId())
                    .findFirst()
                    .getName();

            System.out.println("city = " + cityName);
            System.out.println("region = " + regionName);
            System.out.println("address = " + organization.getAddress());
            System.out.println("link = " + organization.getLink());
        }
        realm.commitTransaction();
    }

    private void writeToDatabase(Information currencyCash) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        DateRate dateRate = new DateRate();

        dateRate.setDate(currencyCash.getDate());

        realm.copyToRealmOrUpdate(dateRate);

        for (City city : currencyCash.getCities()) {
            realm.copyToRealmOrUpdate(city);
        }

        for (Region region : currencyCash.getRegions()) {
            realm.copyToRealmOrUpdate(region);
        }

        for (Currencies currencies : currencyCash.getCurrencies()) {
            realm.copyToRealmOrUpdate(currencies);
        }

        for (Organization organization : currencyCash.getOrganizations()) {
            realm.copyToRealmOrUpdate(organization);
        }

        realm.commitTransaction();
    }

    private RealmResults<Organization> readRealm() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RealmResults<Organization> organizations = realm.where(Organization.class).findAll();

        realm.commitTransaction();

        return organizations;
    }
}
