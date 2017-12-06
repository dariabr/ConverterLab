package com.joyfullkiwi.converterlab.Detail;


import com.arellomobile.mvp.MvpView;
import com.joyfullkiwi.converterlab.Models.Organization;
import com.joyfullkiwi.converterlab.Models.Price;

import io.realm.RealmList;

public interface DetailView extends MvpView {

    void showOrganizationInfo(Organization organization,String city, String regoin);

    void showCurrency(String currencyName, RealmList<Price> prices);

}
