package com.joyfullkiwi.converterlab.Home;


import android.app.DownloadManager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.joyfullkiwi.converterlab.Models.City;
import com.joyfullkiwi.converterlab.Models.Currencies;
import com.joyfullkiwi.converterlab.Models.DateRate;
import com.joyfullkiwi.converterlab.Models.Information;
import com.joyfullkiwi.converterlab.Models.Organization;
import com.joyfullkiwi.converterlab.Models.Region;
import com.joyfullkiwi.converterlab.Provider.Query;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

@InjectViewState
public class HomePresenter extends MvpPresenter<HomeView> {

    private HomeInteractor interactor;

    public HomePresenter(){
        interactor = HomeInteractor.init();
        loadData();

    }

    public void loadData(){
        getViewState().setSwipeRefreshing(true);

        interactor.getAndWriteData()
                //перекл в главныйпоток
                .observeOn(AndroidSchedulers.mainThread())
                //читаем с бд
                .map(cerrencyCash -> readOrganizationsInDb())
                //если ошибка тоже читаем с бд
                .onErrorReturn(throwable -> readOrganizationsInDb())
                .subscribe(results ->{
                    getViewState().setSwipeRefreshing(false);
                    //передача даных в UI
                    getViewState().onSuccessLoaded(results);
                });
    }

    private RealmList<Organization> readOrganizationsInDb(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RealmResults<Organization> organizations = realm.where(Organization.class).findAll();

        RealmList<Organization> result = new RealmList<>();

        result.addAll(organizations.subList(0,organizations.size()));

        realm.commitTransaction();

        return result;
    }

    /*private void handleResult(RealmResults<Organization> results) {


        getViewState().onSuccessLoaded(results);

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for (Organization organization : results) {
            System.out.println("id = " + organization.getId());
            System.out.println("title = " + organization.getTitle());

            String cityName = realm
                    .where(City.class)
                    .equalTo("id", organization.getCityId())
                    .findFirst()
                    .getName();

            String regionName = realm
                    .where(Region.class)
                    .equalTo("id", organization.getRegionId())
                    .findFirst()
                    .getName();

            System.out.println("city = " + cityName);
            System.out.println("region = " + regionName);
            System.out.println("address = " + organization.getAddress());
            System.out.println("link = " + organization.getLink());
        }
        realm.commitTransaction();
    }


    private RealmResults<Organization> readRealm() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RealmResults<Organization> organizations = realm.where(Organization.class).findAll();

        realm.commitTransaction();

        return organizations;
    }*/
}
