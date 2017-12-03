package com.joyfullkiwi.converterlab.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Region extends RealmObject {

    @PrimaryKey
    private String id;

    private String name;

    public Region() {
    }

    public Region(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
