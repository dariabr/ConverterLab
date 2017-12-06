package com.joyfullkiwi.converterlab.Detail;


import android.view.View;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.joyfullkiwi.converterlab.Models.City;
import com.joyfullkiwi.converterlab.Models.Currency;
import com.joyfullkiwi.converterlab.Models.Organization;
import com.joyfullkiwi.converterlab.Models.Price;
import com.joyfullkiwi.converterlab.Models.Region;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailView> {

    private Realm realm;

    public DetailPresenter(){ realm = Realm.getDefaultInstance();}

    public void loadDetailOrganization(String orgId){
        realm.beginTransaction();

        Organization organization = realm.where(Organization.class).equalTo("id",orgId).findFirst();

        for (Currency currency : organization.getCurrencies()){
            RealmResults<Price> realmResults = realm.where(Price.class)
                    .equalTo("organizationId",orgId)
                    .equalTo("currencyName", currency.getCurrencyName())
                    .findAll();

            RealmList<Price> prices = new RealmList<>();
            prices.addAll(realmResults.subList(0,realmResults.size()));
            if(prices.size() != 0){
                getViewState().showCurrency(currency.getCurrencyName(),prices);
                if(prices.size()!= 0){
                    Price first = prices.get(0);
                    Price second = prices.get(0);
                } else {
                    Price first = prices.get(0);

                }
            }
        }

        String region = realm.where(Region.class).equalTo("id",organization.getRegionId()).findFirst().getName();

        String city = realm.where(City.class).equalTo("id",organization.getCityId()).findFirst().getName();

        getViewState().showOrganizationInfo(organization,city,region);

        realm.commitTransaction();

    }
}
