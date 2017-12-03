package com.joyfullkiwi.converterlab.Models;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DateRate extends RealmObject {

    @PrimaryKey
    private String date;

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
