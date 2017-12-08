package com.joyfullkiwi.converterlab.Models;


import io.realm.RealmObject;

public class Price extends RealmObject {

    private String organizationId;

    private String currencyName;

    private double ask;

    private double bid;

    public Price() {
    }

    public Price(double ask, double bid) {
        this.ask = ask;
        this.bid = bid;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
}
