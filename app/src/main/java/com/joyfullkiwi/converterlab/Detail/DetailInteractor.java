package com.joyfullkiwi.converterlab.Detail;


import com.joyfullkiwi.converterlab.Models.City;
import com.joyfullkiwi.converterlab.Models.Currency;
import com.joyfullkiwi.converterlab.Models.CurrencyModel;
import com.joyfullkiwi.converterlab.Models.Organization;
import com.joyfullkiwi.converterlab.Models.Price;
import com.joyfullkiwi.converterlab.Models.Region;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class DetailInteractor {


    private String orgId;
    private Realm realm;
    private Organization organization;
    private String region;
    private String city;
    private List<CurrencyModel> modelList;

    private DetailInteractor() {
        realm = Realm.getDefaultInstance();
    }

    private static DetailInteractor instance;

    public static DetailInteractor init() {
        return new DetailInteractor();
    }

    public void loadDetailOrganization(String orgId) {
        this.orgId = orgId;
        realm.beginTransaction();
        organization = realm.where(Organization.class).equalTo("id", orgId).findFirst();
        region = realm.where(Region.class).equalTo("id", organization.getRegionId()).findFirst()
                .getName();
        city = realm.where(City.class).equalTo("id", organization.getCityId()).findFirst().getName();

        modelList = new ArrayList<>();

        for (Currency currency : organization.getCurrencies()) {
            RealmResults<Price> realmResults = realm.where(Price.class)
                    .equalTo("organizationId", orgId)
                    .equalTo("currencyName", currency.getCurrencyId())
                    .findAll();

            RealmList<Price> prices = new RealmList<>();
            prices.addAll(realmResults.subList(0, realmResults.size()));
            if (prices.size() != 0) {
                modelList.add(new CurrencyModel(currency.getCurrencyId(), prices));
            }
        }
        realm.commitTransaction();
        realm.close();
    }

    public Organization getOrganization() {
        return organization;
    }

    public String getRegion() {
        return region;
    }

    public String getCity() {
        return city;
    }

    public List<CurrencyModel> getModelList() {
        return modelList;
    }
}
