package com.joyfullkiwi.converterlab.Models;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Currency extends RealmObject {

    @PrimaryKey
    private String currency;

    private double ask;

    private double bid;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

}
