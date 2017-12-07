package com.joyfullkiwi.converterlab.Detail;


import com.arellomobile.mvp.MvpView;
import com.joyfullkiwi.converterlab.Models.CurrencyModel;
import com.joyfullkiwi.converterlab.Models.Organization;
import com.joyfullkiwi.converterlab.Models.Price;

import java.util.List;

import io.realm.RealmList;

public interface DetailView extends MvpView {

    void showOrganizationInfo(Organization organization,String city, String regoin);

    void showCurrencies(List<CurrencyModel> modelList);

    void initShareButton(String title, String region, String city, List<CurrencyModel> modelList);
   // void showCurrency(String currencyName, RealmList<Price> prices);

}
