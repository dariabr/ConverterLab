package com.joyfullkiwi.converterlab.Models;


import java.util.Date;
import java.util.List;

import io.realm.RealmList;

public class Information {

    private DateRate date;

    private List<City> cities;
    private List<Region> regions;
    private List<Currencies> currencies;
    private RealmList<Organization> organizations;


    public void setDate(DateRate date) {
        this.date = date;
    }

    public DateRate getDate() {
        return date;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public List<Currencies> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currencies> currencies) {
        this.currencies = currencies;
    }

    public RealmList<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(
            RealmList<Organization> organizations) {
        this.organizations = organizations;
    }
}
