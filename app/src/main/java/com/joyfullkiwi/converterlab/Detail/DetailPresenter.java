package com.joyfullkiwi.converterlab.Detail;


import android.view.View;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.joyfullkiwi.converterlab.Models.City;
import com.joyfullkiwi.converterlab.Models.Currency;
import com.joyfullkiwi.converterlab.Models.Organization;
import com.joyfullkiwi.converterlab.Models.Price;
import com.joyfullkiwi.converterlab.Models.Region;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailView> {

    private DetailInteractor detailInteractor;

    public  DetailPresenter() { detailInteractor = DetailInteractor.init();}

    public void loadDetailOrganization(String orgId) {

        detailInteractor.loadDetailOrganization(orgId);

        getViewState().showCurrencies(detailInteractor.getModelList());

        getViewState().showOrganizationInfo(detailInteractor.getOrganization(),detailInteractor.getCity(),detailInteractor.getRegion());
    }

}
