package com.joyfullkiwi.converterlab.Models;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DateRate extends RealmObject {



   private long time;

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
