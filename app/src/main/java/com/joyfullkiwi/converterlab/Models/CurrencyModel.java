package com.joyfullkiwi.converterlab.Models;


import io.realm.RealmList;

public class CurrencyModel {

    private final String currencyId;

    private final RealmList<Price> prices;

    public CurrencyModel(String currencyId,
                         RealmList<Price> prices) {
        this.currencyId = currencyId;
        this.prices = prices;
    }

    public RealmList<Price> getPrices() {
        return prices;
    }

    public String getCurrencyId() {
        return currencyId;
    }
}
