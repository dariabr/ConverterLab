package com.joyfullkiwi.converterlab.Models;


import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Currency extends RealmObject {

    @PrimaryKey
    private String currencyName;

   @Ignore
    private Price price;

    public String getCurrencyName() { return currencyName; }

    public void setCurrencyName(String currencyName) { this.currencyName = currencyName; }

    public Price getPrice() { return price; }

    public void setPrice(Price price) { this.price = price; }
}
